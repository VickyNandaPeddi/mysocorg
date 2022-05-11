package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.good.platform.client.project.ProjectClientApi;
import com.good.platform.entity.User;
import com.good.platform.entity.UserAddressDetails;
import com.good.platform.entity.UserRoles;
import com.good.platform.enums.InviteStatus;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.UserDataMapper;
import com.good.platform.model.AddressDetailsModel;
import com.good.platform.model.ProjectIdNameModel;
import com.good.platform.model.UserProjectMapModel;
import com.good.platform.repository.UserAddressDetailsRepository;
import com.good.platform.repository.UserProjectMappingRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.repository.UserRoleMappingRepository;
import com.good.platform.repository.UserRoleRepository;
import com.good.platform.request.dto.EmailContentRequestDto;
import com.good.platform.request.dto.UserOtherDetailsRequestDto;
import com.good.platform.response.InvitedUsersResponse;
import com.good.platform.response.PaginatedResponse;
import com.good.platform.response.dto.InviteResendResponse;
import com.good.platform.response.dto.UserDetailsResponseDto;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.response.dto.UserStatusResponse;
import com.good.platform.service.AdminService;
import com.good.platform.service.EmailService;
import com.good.platform.service.UserService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Value("${keycloak.admin.realm}")
	private String keycloakAdminRealm;

	private final ProjectClientApi projectClientApi;
	private final UserProjectMappingRepository userProjectMappingRepo;
	private final UserRoleRepository userRoleRepository;
	private final UserRepository userRepository;
	private final UserDataMapper userDataMapper;
	private final SecurityUtil securityUtil;
	private final UserService userService;
	private final UserRoleMappingRepository userRoleMappingRepo;
	private final Keycloak keycloak;
	private final UserAddressDetailsRepository userAddressDetailsRepository;
	private final UserProjectMappingRepository userProjectMappingRepository;
	private final KeycloakService keycloakService;
	private final EmailService emailService;

	/**
	 * Get all invite-user to Admin
	 * 
	 * @version 1.23.3
	 * @author Arya C Achari
	 * @since 08-Feb-2022
	 * 
	 * @implNote JPA Query changed from OrganisationRespo to UserRepo. Change in
	 *           query and search provision added by first name or last name
	 */
	public PaginatedResponse<InvitedUsersResponse> getInvitedUsers(String organisationId, String projectId,
			String roleId, Integer pageNumber, Integer pageSize, InviteStatus inviteStatus, String name) {
		Pageable page = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
		try {
			List<InvitedUsersResponse> response = new ArrayList<>();
			Page<InvitedUsersResponse> invitedUsers;
			// invitedUsers = orgRepo.getInvitedUsersForAdmin(organisationId, roleId,
			// projectId, inviteStatus, page);
			String userIdpId = securityUtil.getCurrentUser();

			if (StringUtils.isEmpty(projectId)) {
				invitedUsers = userRepository.getInvitedUsersForAdmin(organisationId, roleId, inviteStatus, name,
						userIdpId, page);
			} else {
				invitedUsers = userRepository.getInvitedUsersForAdminWithProjectId(organisationId, roleId, projectId,
						inviteStatus, name, userIdpId, page);
			}

			for (InvitedUsersResponse user : invitedUsers.getContent()) {
				List<String> projectIds = userProjectMappingRepo.getProjectIdOfUser(user.getUserId());

				List<ProjectIdNameModel> userProjectList = projectClientApi.getProjectsIdsNames(projectIds).getData();
				if (userProjectList != null) {
					user.setProjects(userProjectList);
				}
				response.add(user);
			}

			Long totalElements = invitedUsers.getTotalElements();
			Integer totalPages = invitedUsers.getTotalPages();

			return new PaginatedResponse(totalElements, totalPages, pageSize, pageNumber + 1, response.size(),
					response);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.INVITED_USERS_FETCH_FAIL);
		}
	}

	@Override
	public UserResponse inviteUsers(UserOtherDetailsRequestDto request) {
		log.debug("Adding user  starts");

		if (request.getId() != null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.SOMETHING_WENT_WRONG);
		}
		if (request.getOrganisationId() != null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.SOMETHING_WENT_WRONG);
		}

		if (request.getUserRoleId() == null || request.getUserRoleId().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_ROLE_ID_NOT_FOUND);
		}
		if (request.getEmailId() == null || request.getEmailId().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_EMAIL_NOT_FOUND);
		}
		if (request.getPassword() == null || request.getPassword().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.PASSWORD_NOT_FOUND);
		}
		if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USERNAME_NOT_FOUND);
		}

		if (!userRoleRepository.existsById(request.getUserRoleId())) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ROLES_NOT_FOUND);
		}

		Boolean userExist = userRepository.existsByEmailIdIgnoreCase(request.getEmailId());
		if (userExist) {
			throw new SOException(ErrorCode.CONFLICT, Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS);
		}

		try {

			User entity = userDataMapper.mapAdminDetails(request);
			entity.setCreatedBy(securityUtil.getCurrentUser());
			entity.setEmailVerified(true);
			entity.setInviteStatus(InviteStatus.PENDING);
			User savedEntity = userRepository.save(entity);
			UserResponse userResponse = userService.setPassword(savedEntity.getId(),
					userDataMapper.mapAgentOtherDetailsToUserRequest(savedEntity.getId(), request));
			EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
			emailContentRequestDto.setEmailId(request.getEmailId());
			emailContentRequestDto.setSubject(Constants.INVITE_USER_EMAIL_SUBJECT);
			emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(request.getFirstName()));
			emailContentRequestDto.setPassword(request.getPassword());
			emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");

			emailService.sendUserInvitation(emailContentRequestDto);

			log.debug("Adding user by the admin ends");
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.USER_EMAIL_EXIST.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_EMAIL_EXIST);
			} else if (Constants.EMAIL_SEND_FAILURE.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_SEND_FAILURE);
			} else if (Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_INVITE_FAIL);
			}
		}
	}

	@Override
	public UserResponse getVolunteer(String userId) {
		Optional<User> savedEntity = userRepository.findById(userId);
		if (savedEntity.isPresent()) {
			List<String> projectIds = userProjectMappingRepo.getProjectIdOfUser(savedEntity.get().getId());
			UserRoles roleData = userRoleMappingRepo.getUserRoleId(savedEntity.get().getId());
			UserResponse response = userDataMapper.userBodyEntityToResponseMapper(savedEntity.get());
			response.setProjectsId(projectIds);
			response.setRoleId(roleData.getId());
			response.setRole(roleData.getRole());
			return response;
		} else {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
		}

	}

	@Override
	public boolean enableDisableUser(String userIdpId, String status) {
		log.debug("Enabling/Disabling user starts");
		if (userIdpId == null || userIdpId.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_IDP_ID_INVALID);
		}
		boolean response = false;
		try {
			RealmResource realmResource = keycloak.realm(keycloakAdminRealm);
			UserResource userRessource = realmResource.users().get(userIdpId);
			UserRepresentation user = userRessource.toRepresentation();
			if ("enable".equals(status)) {
				user.setEnabled(true);
			} else {
				user.setEnabled(false);
			}
			userRessource.update(user);
			if ("enable".equals(status)) {
				updateUserStatus(userIdpId, true);
			} else {
				updateUserStatus(userIdpId, false);
			}
			response = true;
			log.debug("Enabling/Disabling user ends");
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (("enable").equals(status)) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_ENABLE_FAIL);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_DISABLE_FAIL);
			}
		}
		return response;
	}

	/**
	 * updateAgentByAdmin is to update the users by admin only VALIDATORS,
	 * SOCIAL-ADMINS, ADMINS
	 * 
	 * the above mentioned roles doesnt have UserOtherDetails
	 */
	@Override
	public UserDetailsResponseDto updateAgentByAdmin(UserOtherDetailsRequestDto request, String userId) {
		log.debug("Update agent data by admin starts");
		UserDetailsResponseDto userResponse = null;
		try {
			String userIdpId = securityUtil.getCurrentUser();
			Optional<User> existingUser = userRepository.findById(userId);
			if (existingUser.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			}

			User entity = userDataMapper.mapAgentDetails(request);
			entity.setId(existingUser.get().getId());
			entity.setEmailId(existingUser.get().getEmailId());
			entity.setPhone(existingUser.get().getPhone());
			entity.setCreatedBy(existingUser.get().getCreatedBy());
			entity.setCreatedAt(existingUser.get().getCreatedAt());
			entity.setModifiedAt(new Date().getTime());
			entity.setLastModifiedBy(userIdpId);
			entity.setDetailsUpdated(request.getDetailsUpdated());
			entity.setUserIdpId(existingUser.get().getUserIdpId());
			entity.setActive(existingUser.get().isActive());
			entity.setDeleted(existingUser.get().isDeleted());
			entity.setEmailVerified(existingUser.get().getEmailVerified());
			entity.setInviteStatus(existingUser.get().getInviteStatus());
			User savedUserEntity = userRepository.save(entity);

			UserAddressDetails existingUserAddressDetails = userAddressDetailsRepository
					.getUserAddressEntity(savedUserEntity.getId());
			UserAddressDetails userAddressDetails = userDataMapper.mapAgentAddressDetails(savedUserEntity.getId(),
					request);
			if (Objects.isNull(existingUserAddressDetails)) {
				userAddressDetails.setCreatedBy(userIdpId);
				userAddressDetails.setCreatedAt(new Date().getTime());
			} else {
				userAddressDetails.setId(existingUserAddressDetails.getId());
				userAddressDetails.setCreatedAt(existingUserAddressDetails.getCreatedAt());
				userAddressDetails.setCreatedBy(existingUserAddressDetails.getCreatedBy());
				userAddressDetails.setLastModifiedBy(userIdpId);
				userAddressDetails.setModifiedAt(new Date().getTime());
			}
			UserAddressDetails savedAddressDetails = userAddressDetailsRepository.save(userAddressDetails);
			AddressDetailsModel addressMapModel = userDataMapper.mapAgentAddressDetails(savedAddressDetails);

			List<UserProjectMapModel> userProjectMapModel = userProjectMappingRepository
					.getProjectUserMapModel(savedUserEntity.getId());
			userResponse = userDataMapper.mapAgentDetailsToResponse(request.getOrganisationId(), savedUserEntity);
			userResponse.setAddress(addressMapModel);
			userResponse.setUserProjectMapModel(userProjectMapModel);
			log.debug("Updating the user details ends");
			return userResponse;
		} catch (Exception exception) {
			if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_BY_ADMIN_FAIL);
			}
		}

	}

	@Override
	public UserStatusResponse updateUserStatus(String id) {
		User user = findUserDetails(id);
		if (user != null) {
			user.setDetailsUpdated(true);
			user.setInviteStatus(InviteStatus.ACCEPTED);
			user.setModifiedAt(new Date().getTime());
			user.setLastModifiedBy(securityUtil.getCurrentUser());
			user = userRepository.save(user);
		}
		UserStatusResponse response = userDataMapper.userEntityMappingToUserStatusResponse(user);
		return response;
	}

	@Override
	public User findUserDetails(String id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND));
	}

	public boolean updateUserStatus(String userIdpId, boolean status) {
		log.debug("Fetch project Ids by userIdpId");
		boolean success = false;
		if (userIdpId == null || userIdpId.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_IDP_ID_INVALID);
		}
		try {
			String updateUser = securityUtil.getCurrentUser();
			User existingEntity = userRepository.findByUserIdpId(userIdpId);
			if (Objects.isNull(existingEntity)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			}
			existingEntity.setActive(status);
			existingEntity.setLastModifiedBy(updateUser);
			existingEntity.setModifiedAt(new Date().getTime());
			userRepository.save(existingEntity);
			success = true;
		} catch (Exception exception) {
			if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.UPDATE_USER_STATUS_FAILED);
			}
		}
		return success;
	}

	@Override
	public InviteResendResponse resendUserInvitation(UserOtherDetailsRequestDto request) {

		User existingEntity = userRepository.findByEmailIdIgnoreCase(request.getEmailId());
		if (Objects.isNull(existingEntity)) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EMAIL);
		}
		if (!existingEntity.getInviteStatus().equals(InviteStatus.PENDING)) {
			throw new SOException(ErrorCode.INVALID_OPERATION, Constants.INVITE_STATUS_MISMATCH);
		}
		keycloakService.updateCredentials(existingEntity.getUserIdpId(), "reinvite@1234");
		EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
		emailContentRequestDto.setEmailId(request.getEmailId());
		emailContentRequestDto.setSubject(Constants.INVITE_USER_EMAIL_SUBJECT);
		emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(existingEntity.getFirstName()));
		emailContentRequestDto.setPassword("reinvite@1234");
		emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
		emailService.sendUserInvitation(emailContentRequestDto);
		return new InviteResendResponse(existingEntity.getEmailId(), "Success");

	}
}