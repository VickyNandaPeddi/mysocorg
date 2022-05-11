package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.good.platform.client.beneficiary.BeneficiaryClient;
import com.good.platform.client.project.ProjectClientApi;
import com.good.platform.client.sap.model.BeneficiaryRegisterResponseDto;
import com.good.platform.client.sap.model.UserEntityIdRequest;
import com.good.platform.entity.User;
import com.good.platform.entity.UserOrganisationMapping;
import com.good.platform.entity.UserProjectMapping;
import com.good.platform.entity.UserRoleMapping;
import com.good.platform.entity.UserRoles;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.UserDataMapper;
import com.good.platform.model.ProjectIdNameModel;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.UserOrganisationMappingRespository;
import com.good.platform.repository.UserProjectMappingRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.repository.UserRoleMappingRepository;
import com.good.platform.repository.UserRoleRepository;
import com.good.platform.request.RegisterDTO;
import com.good.platform.request.dto.BeneficiaryRegisterDTO;
import com.good.platform.request.dto.EmailContentRequestDto;
import com.good.platform.request.dto.UserRequest;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.service.BeneficiaryService;
import com.good.platform.service.EmailService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeneficiaryServiceImpl implements BeneficiaryService {
	private final UserRepository userRepo;
	private final UserRoleRepository userRoleRepo;
	private final UserRoleMappingRepository userRoleMappingRepo;
	private final OrganisationRepository orgRepo;
	private final UserDataMapper userMapper;
	private final KeycloakService keycloakService;
	private final SecurityUtil securityUtil;
	private final UserOrganisationMappingRespository userOrgMappingRepo;
	private final UserProjectMappingRepository userProjMappingRepo;
	private final BeneficiaryClient sapClient;
	private final ProjectClientApi projectClientApi;
	private final EmailService emailService;

	@Transactional
	@Override
	public UserResponse register(RegisterDTO request) {
		UserResponse userResponse = null;
		try {
			orgRepo.findById(request.getOrganisationId())
					.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_DATA_NOT_FOUND));
			UserRoles role = userRoleRepo.findByRole("BENEFICIARY");
			if (userRepo.existsByEmailIdIgnoreCase(request.getEmailId())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_EMAIL_EXIST);
			}
			if (userRepo.existsByPhone(request.getPhone())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_PHONE_EXIST);
			}
			User entity = userMapper.mapBeneficiaryDetails(request);
			entity.setEmailVerified(true);
			entity.setPhoneVerified(true);
			User savedEntity = userRepo.save(entity);

			sapClient.updateUserEntityId(
					new UserEntityIdRequest(request.getBeneficiaryId(), savedEntity.getEmailId(), savedEntity.getId()));

			UserRequest keycloakUserRequest = userMapper.mapUserEntity(request);
			String userIdpId = keycloakService.create(keycloakUserRequest);
			keycloakService.addRole(role.getRole(), userIdpId);
			savedEntity.setUserIdpId(userIdpId);
			savedEntity.setLastModifiedBy(userIdpId);
			savedEntity.setModifiedAt(new Date().getTime());
			User savedUser = userRepo.save(entity);

			UserRoleMapping userRoleMapping = new UserRoleMapping();
			userRoleMapping.setUser(savedUser);
			userRoleMapping.setUserRoles(role);
			userRoleMapping.setCreatedBy(securityUtil.getCurrentUser());
			userRoleMappingRepo.save(userRoleMapping);

			UserOrganisationMapping userOrgMapEntity = userMapper.mapOrganization(savedUser.getId(), request);
			userOrgMapEntity.setCreatedBy(securityUtil.getCurrentUser());
			userOrgMappingRepo.save(userOrgMapEntity);

			UserProjectMapping userProjectMapping = userMapper.userProjectMapping(savedEntity.getId(),
					request.getProjectId());
			userProjectMapping.setCreatedBy(securityUtil.getCurrentUser());
			userProjMappingRepo.save(userProjectMapping);

			userResponse = userMapper.userBodyEntityToResponseMapper(savedEntity);
			log.debug("Adding user ends");
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if(Constants.ORG_DATA_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_DATA_NOT_FOUND);
			}else if(Constants.USER_EMAIL_EXIST.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_EMAIL_EXIST);
			}else if(Constants.USER_PHONE_EXIST.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_PHONE_EXIST);
			}else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_ADD_FAIL);
			}
		}
	}

	/**
	 * To onboard the beneficiary via cron-job
	 * @author Arya C Achari
	 * @since 25-Feb-2022
	 * 
	 * @return
	 */
	@Override
	public List<UserResponse> sapBeneficiaryOnBoarding() {
		try {
			List<UserResponse> userOnBoardList = new ArrayList<UserResponse>();
			List<BeneficiaryRegisterDTO> registerDTOList = new ArrayList<BeneficiaryRegisterDTO>();

			log.info("Get the role id of role=BENEFICIARY");
			UserRoles role = userRoleRepo.findByRole("BENEFICIARY");

			log.info("Fiegn call to get all the imported beneficiary");
			List<BeneficiaryRegisterResponseDto> importedBeneficiaryList = sapClient.getBeneficiaryListForRegister()
					.getData();
			
			List<ProjectIdNameModel> projOrgMappingList = new ArrayList<ProjectIdNameModel>();
			log.info("Fiegn call to get all the distinct project list from imported beneficiary");
			List<String> projectList = sapClient.getDistinctProjectList().getData();

			if (projectList != null) {
				log.info("Fiegn call to get all the project which mapped with the organisation");
				projOrgMappingList = projectClientApi.getProjectsIdsNames(projectList).getData();
				if (projOrgMappingList != null && importedBeneficiaryList != null) {
					log.info("Compare the imported beneficiary list vs ProjectOrganisation mapping list, ");
					log.info("If found the same projectId from both list mapped into new List<BeneficiaryRegisterResponseDto>");

					for (BeneficiaryRegisterResponseDto importedDto : importedBeneficiaryList) {
						for (ProjectIdNameModel projOrgMapping : projOrgMappingList) {
							if (importedDto.getProjectId().equals(projOrgMapping.getProjectId())) {
								BeneficiaryRegisterDTO registerDTO = new BeneficiaryRegisterDTO();
								registerDTO.setFirstName(importedDto.getFirstName());
								registerDTO.setMiddleName(importedDto.getMiddleName());
								registerDTO.setLastName(importedDto.getLastName());
								registerDTO.setEmailId(importedDto.getEmail());
								registerDTO.setPhone(importedDto.getMobile());
								registerDTO.setPassword("bene@1234"); // Will change once it confirm from client
								registerDTO.setRoleId(role.getId());
								registerDTO.setOrganisationId(projOrgMapping.getOrganisationId());
								registerDTO.setProjectId(importedDto.getProjectId());
								registerDTO.setBeneficiaryId(importedDto.getId());
								registerDTO.setCreatedBy(importedDto.getCreatedBy());
								registerDTOList.add(registerDTO);
							}
						}
					}
				}

				log.info("beneficiary onboarding section");
				if (registerDTOList != null && registerDTOList.size() > 0) {
					registerDTOList.forEach(item -> {
						userOnBoardList.add(beneficiaryOnboarding(item, role));
					});
				}
			}
			log.info("All imported benefiicaries onboarded!");
			return userOnBoardList;
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.BENEFICIARY_ON_BOARDING_FAILED);
		}
	}

	// beneficiary on boarding
	@Transactional
	public UserResponse beneficiaryOnboarding(BeneficiaryRegisterDTO request, UserRoles role) {
		log.debug("Beneficiary onboarding started");
		UserResponse userResponse = null;
		try {
			if (userRepo.existsByEmailIdIgnoreCase(request.getEmailId())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_EMAIL_EXIST);
			}
			if (userRepo.existsByPhone(request.getPhone())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_PHONE_EXIST);
			}
			User entity = userMapper.mapToUserFromBeneficiaryRegisterDTO(request);
			entity.setEmailVerified(true);
			entity.setPhoneVerified(true);
			User savedEntity = userRepo.save(entity);

			sapClient.updateUserEntityId(
					new UserEntityIdRequest(request.getBeneficiaryId(), savedEntity.getEmailId(), savedEntity.getId()));

			UserRequest keycloakUserRequest = userMapper.mapUserEntityFromBeneficiaryRegisterDTO(request);
			String userIdpId = keycloakService.create(keycloakUserRequest);
			keycloakService.addRole(role.getRole(), userIdpId);
			savedEntity.setUserIdpId(userIdpId);
			savedEntity.setLastModifiedBy(request.getCreatedBy());
			savedEntity.setModifiedAt(new Date().getTime());
			userRepo.save(entity);

			UserRoleMapping userRoleMapping = new UserRoleMapping();
			userRoleMapping.setUser(savedEntity);
			userRoleMapping.setUserRoles(role);
			userRoleMapping.setCreatedBy(request.getCreatedBy());
			userRoleMappingRepo.save(userRoleMapping);

			UserOrganisationMapping userOrgMapEntity = userMapper.mapOrganizationFromBeneficiaryRegisterDTO(savedEntity.getId(), request);
			userOrgMapEntity.setCreatedBy(request.getCreatedBy());
			userOrgMappingRepo.save(userOrgMapEntity);

			UserProjectMapping userProjectMapping = userMapper.userProjectMapping(savedEntity.getId(),
					request.getProjectId());
			userProjectMapping.setCreatedBy(request.getCreatedBy());
			userProjMappingRepo.save(userProjectMapping);

			log.info("Email is senting to beneficary");
			if (!StringUtils.isEmpty(request.getEmailId())) {
				EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
				emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(
						createFullName(request.getFirstName(), request.getMiddleName(), request.getLastName())));
				emailContentRequestDto.setEmailId(request.getEmailId());
				emailContentRequestDto.setSubject(Constants.BENEFICIARY_ON_BOARDING_EMAIL_SUB);
				emailContentRequestDto.setPassword(request.getPassword());
				emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
				emailService.sendUserInvitation(emailContentRequestDto);
			}

			userResponse = userMapper.userBodyEntityToResponseMapper(savedEntity);
			log.debug("Beneficiary onboarding completed");
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if(Constants.USER_EMAIL_EXIST.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_EMAIL_EXIST);
			}else if(Constants.USER_PHONE_EXIST.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_PHONE_EXIST);
			}else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_ADD_FAIL);
			}
		}
	}

	/**
	 * @author Arya C Achari
	 * @since 25-Feb-2022
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @return
	 */
	private String createFullName(String firstName, String middleName, String lastName) {
		String fullName = null;
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(middleName) && !StringUtils.isEmpty(lastName)) {
			fullName = firstName + " " + middleName + " " + lastName;
		} else if (StringUtils.isEmpty(middleName) && StringUtils.isEmpty(lastName)) {
			fullName = firstName;
		} else if (!StringUtils.isEmpty(middleName) && StringUtils.isEmpty(lastName)) {
			fullName = firstName + " " + middleName;
		} else {
			fullName = firstName + " " + lastName;
		}

		return fullName.trim();
	}
}
