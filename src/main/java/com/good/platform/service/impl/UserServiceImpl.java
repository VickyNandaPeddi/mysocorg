package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.good.platform.client.beneficiary.BeneficiaryClient;
import com.good.platform.client.project.ProjectClientApi;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;
import com.good.platform.entity.UserAddressDetails;
import com.good.platform.entity.UserOrganisationMapping;
import com.good.platform.entity.UserOtherDetails;
import com.good.platform.entity.UserProjectMapping;
import com.good.platform.entity.UserRoleMapping;
import com.good.platform.entity.UserRoles;
import com.good.platform.enums.InviteStatus;
import com.good.platform.exception.EmailException;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.UserDataMapper;
import com.good.platform.model.AddressDetailsModel;
import com.good.platform.model.ProjectIdNameModel;
import com.good.platform.model.ProjectModel;
import com.good.platform.model.UserProjectMapModel;
import com.good.platform.model.UserProjectOrgIdModel;
import com.good.platform.projection.InvitedUsersProjection;
import com.good.platform.projection.SocialAdminDetailsProjection;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.UserAddressDetailsRepository;
import com.good.platform.repository.UserOrganisationMappingRespository;
import com.good.platform.repository.UserOtherDetailsRepository;
import com.good.platform.repository.UserProjectMappingRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.repository.UserRoleMappingRepository;
import com.good.platform.repository.UserRoleRepository;
import com.good.platform.request.NewUserRequest;
import com.good.platform.request.dto.ConfirmAccountPasswordRequest;
import com.good.platform.request.dto.ConfirmEmailAccountRequestDto;
import com.good.platform.request.dto.EmailContentRequestDto;
import com.good.platform.request.dto.UpdatePasswordRequest;
import com.good.platform.request.dto.UserOtherDetailsRequestDto;
import com.good.platform.request.dto.UserProjectMappingRequest;
import com.good.platform.request.dto.UserRequest;
import com.good.platform.response.AgentDetailsDTO;
import com.good.platform.response.CountResponse;
import com.good.platform.response.InvitedUsersResponse;
import com.good.platform.response.PaginatedResponse;
import com.good.platform.response.UserCommonResponse;
import com.good.platform.response.UserMinDetails;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.InviteResendResponse;
import com.good.platform.response.dto.OtpRegisterResponseDTO;
import com.good.platform.response.dto.PhoneOTPResponseDTO;
import com.good.platform.response.dto.ProjectResponseDto;
import com.good.platform.response.dto.SocialAdminDetails;
import com.good.platform.response.dto.UserDataExistenceResponse;
import com.good.platform.response.dto.UserDetailsResponseDto;
import com.good.platform.response.dto.UserProjectMappingResponse;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.response.dto.UserStatusResponse;
import com.good.platform.service.EmailService;
import com.good.platform.service.OTPService;
import com.good.platform.service.SmsGatewayService;
import com.good.platform.service.UserService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.EmailUtils;
import com.good.platform.utility.FileUtils;
import com.good.platform.utility.OtpUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Value("${keycloak.admin.realm}")
	private String keycloakAdminRealm;

	@Autowired
	Keycloak keycloak;

	@Autowired
	EmailService emailService;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleMappingRepository userRoleMappingRepository;

	@Autowired
	UserAddressDetailsRepository userAddressDetailsRepository;

	@Autowired
	UserOtherDetailsRepository userOtherDetailsRepository;

	@Autowired
	UserOrganisationMappingRespository userOrganisationMappingRespository;

	@Autowired
	UserProjectMappingRepository userProjectMappingRepository;

	@Autowired
	EmailUtils emailUtils;

	@Autowired
	KeycloakService keycloakService;

	@Autowired
	UserDataMapper userDataMapper;

	@Autowired
	UserRoleRepository userRoleRepo;

	@Autowired
	UserRoleMappingRepository userRoleMappingRepo;

	@Autowired
	SecurityUtil securityUtil;

	@Autowired
	OrganisationRepository orgRepo;

	@Autowired
	UserOrganisationMappingRespository userOrgRepo;
	@Autowired
	ProjectClientApi projectClientApi;

	@Autowired
	SmsGatewayService smsGatewayService;

	@Autowired
	OrganisationRepository organisationRepo;

	@Value("${sms.gateway.download.link.arpan}")
	private String arpanDownloadLink;

	@Value("${sms.gateway.download.link.ummeed}")
	private String ummeedDownloadLink;

	@Value("${sms.gateway.download.link.sap}")
	private String sapDownloadLink;

	@Autowired
	private OTPService otpService;

	@Autowired
	BeneficiaryClient beneficiaryClient;

	@Override
	public UserResponse confirmAccount(String userId, ConfirmEmailAccountRequestDto confirmAccountRequestDto) {
		log.debug("Account verification request starts");
		UserResponse userResponse = null;
		try {
			User existingEntity = userRepository.findByEmailIdIgnoreCase(confirmAccountRequestDto.getEmail());
			if (Objects.isNull(existingEntity)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EMAIL);
			}

			if (emailService.activateUserAccountByEmail(confirmAccountRequestDto)) {
				existingEntity.setEmailVerified(true);
			} else {
				// check whether errorcode is correct or not(refactor)
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL_OTP);
			}
			// for PHONE NUMBER VERIFICATION
			confirmAccountRequestDto.setPhone(existingEntity.getPhone());
			confirmAccountRequestDto.setPhoneOtp(confirmAccountRequestDto.getEmailOtp());
			if (otpService.verifyPhoneOTP(confirmAccountRequestDto)) {
				existingEntity.setPhoneVerified(true);

			} else {
				// check whether errorcode is correct or not(refactor)
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_PHONE_OTP);
			}

			existingEntity.setLastModifiedBy(securityUtil.getCurrentUser());
			existingEntity.setModifiedAt(new Date().getTime());
			User savedEntity = userRepository.save(existingEntity);
			userResponse = userDataMapper.userBodyEntityToResponseMapper(savedEntity);
			return userResponse;
		} catch (Exception exception) {
			if (Constants.INVALID_EMAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EMAIL);
			} else if (Constants.EMAIL_VERIFICATION_DATA_NOT_FOUND.equals(exception.getMessage())) {
				throw new EmailException(ErrorCode.NOT_FOUND, Constants.EMAIL_VERIFICATION_DATA_NOT_FOUND);
			} else if (Constants.PHONE_OTP_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.PHONE_OTP_NOT_FOUND);
			} else if (Constants.PHONE_OTP_EXPIRED.equals(exception.getMessage())) {
				throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.PHONE_OTP_EXPIRED);
			} else if (Constants.EMAIL_VERIFICATION_OTP_EXPIRED.equals(exception.getMessage())) {
				throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.EMAIL_VERIFICATION_OTP_EXPIRED);
			} else if (Constants.INVALID_EMAIL_OTP.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL_OTP);
			} else if (Constants.INVALID_PHONE_OTP.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_PHONE_OTP);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ACCOUNT_CONFIRMATION_FAIL);
			}
		}
	}

	@Override
	@Transactional
	public UserResponse otpVerifyAndSetPassword(String userId, ConfirmAccountPasswordRequest confirmAccountRequestDto) {
		log.debug("OTP verification and set password starts");
		if (userId == null || userId.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_ID_INVALID);
		}
		if (confirmAccountRequestDto.getEmail() == null || confirmAccountRequestDto.getEmail().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL);
		}
		if (confirmAccountRequestDto.getEmailOtp() == null || confirmAccountRequestDto.getEmailOtp().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL_OTP);
		}
		if (confirmAccountRequestDto.getPassword() == null || confirmAccountRequestDto.getPassword().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.PASSWORD_NOT_FOUND);
		}
		if (confirmAccountRequestDto.getRoleId() == null || confirmAccountRequestDto.getRoleId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_ROLE_ID_NOT_FOUND);
		}
		try {
			Optional<User> user = userRepository.findById(userId);
			if (!user.isPresent()) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_NOT_FOUND);
			}
			if (!user.get().getEmailId().equals(confirmAccountRequestDto.getEmail())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.EMAIL_INCORRECT);
			}
			ConfirmEmailAccountRequestDto confirmEmailAccountRequestDto = new ConfirmEmailAccountRequestDto();
			confirmEmailAccountRequestDto.setEmail(confirmAccountRequestDto.getEmail());
			confirmEmailAccountRequestDto.setEmailOtp(confirmAccountRequestDto.getEmailOtp());
			UserResponse userResponse = confirmAccount(userId, confirmEmailAccountRequestDto);

			UserRequest userRequest = new UserRequest();
			userRequest.setEmailId(confirmAccountRequestDto.getEmail());
			userRequest.setPassword(confirmAccountRequestDto.getPassword());
			userRequest.setRoleId(confirmAccountRequestDto.getRoleId());
			UserResponse passwordSet = setPassword(userId, userRequest);
			log.debug("OTP verification and set password ends");
			return passwordSet;
		} catch (Exception exception) {
			if (Constants.INVALID_EMAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EMAIL);
			} else if (Constants.INVALID_EMAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EMAIL);
			} else if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_NOT_FOUND);
			} else if (Constants.EMAIL_INCORRECT.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.EMAIL_INCORRECT);
			} else if (Constants.EMAIL_VERIFICATION_DATA_NOT_FOUND.equals(exception.getMessage())) {
				throw new EmailException(ErrorCode.NOT_FOUND, Constants.EMAIL_VERIFICATION_DATA_NOT_FOUND);
			} else if (Constants.PHONE_OTP_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.PHONE_OTP_NOT_FOUND);
			} else if (Constants.PHONE_OTP_EXPIRED.equals(exception.getMessage())) {
				throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.PHONE_OTP_EXPIRED);
			} else if (Constants.EMAIL_VERIFICATION_OTP_EXPIRED.equals(exception.getMessage())) {
				throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.EMAIL_VERIFICATION_OTP_EXPIRED);
			} else if (Constants.INVALID_EMAIL_OTP.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL_OTP);
			} else if (Constants.INVALID_PHONE_OTP.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_PHONE_OTP);
			} else if (Constants.ACCOUNT_CONFIRMATION_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ACCOUNT_CONFIRMATION_FAIL);
			} else if (exception.getMessage().equals(Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS)) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS);
			} else if (Constants.PASSWORD_ADD_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PASSWORD_ADD_FAIL);
			} else if (Constants.ACCOUNT_CONFIRMATION_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ACCOUNT_CONFIRMATION_FAIL);
			} else if (Constants.USER_UPDATE_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_FAIL);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.OTP_VERIFICATION_DONE_PASSWORD_FAIL);
			}
		}
	}

	@Override
	@Transactional
	public UserResponse addUser(UserRequest request) {
		UserResponse userResponse = null;

		if (emailExist(request.getEmailId())) {
			throw new SOException(ErrorCode.CONFLICT, Constants.USER_EMAIL_EXIST);
		}
		if (userRepository.existsByPhone(request.getPhone())) {
			throw new SOException(ErrorCode.CONFLICT, Constants.USER_PHONE_EXIST);
		}

		try {
			User entity = userDataMapper.userValuesMapper(request);
			entity.setEmailVerified(false);
			entity.setPhoneVerified(false);
			entity.setCreatedBy(securityUtil.getCurrentUser());
			User savedEntity = userRepository.save(entity);
			String otp = OtpUtility.generateOTP();
			Long expTime = OtpUtility.getExpirationTime(EmailUtils.EXPIRATION_MINUTES);
			emailService.generateEmailOtpToRegister(savedEntity.getEmailId(), expTime, otp);
			PhoneOTPResponseDTO otpDetails = otpService.generateOTP(request.getPhone(), expTime, otp);
			smsGatewayService.sendUserRegistrationOTP(otpDetails, savedEntity.getFirstName());
			userResponse = userDataMapper.userBodyEntityToResponseMapper(savedEntity);
			log.debug("Adding user ends");
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_ADD_FAIL);
		}
	}

	@Override
	@Transactional
	public UserDetailsResponseDto updateAgent(UserOtherDetailsRequestDto request) {
		log.debug("Updating the user details starts");
		UserDetailsResponseDto userResponse = null;
		try {
			String updateUser = securityUtil.getCurrentUser();
			User user = userRepository.findByUserIdpId(updateUser);
			if (Objects.isNull(user)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			}
			UserOtherDetails existingEntity = userOtherDetailsRepository.findByUserId(user.getId());
			if (existingEntity == null) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_OTHER_DETAILS_NOT_FOUND);
			}
			User existingUserEntity = userRepository.findById(user.getId()).get();

			if (!existingUserEntity.getEmailId().equals(request.getEmailId())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_EMAIL_INVALID);
			}

			User entity = userDataMapper.mapAgentDetails(request);
			entity.setId(existingUserEntity.getId());
			entity.setEmailId(existingUserEntity.getEmailId());
			entity.setCreatedBy(existingUserEntity.getCreatedBy());
			entity.setCreatedAt(existingUserEntity.getCreatedAt());
			entity.setModifiedAt(new Date().getTime());
			entity.setLastModifiedBy(updateUser);
			entity.setDetailsUpdated(request.getDetailsUpdated());
			entity.setUserIdpId(existingUserEntity.getUserIdpId());
			entity.setActive(existingUserEntity.isActive());
			entity.setDeleted(existingUserEntity.isDeleted());
			entity.setEmailVerified(existingUserEntity.getEmailVerified());
			entity.setInviteStatus(InviteStatus.ACCEPTED);
			User savedUserEntity = userRepository.save(entity);

			UserAddressDetails existingUserAddressDetails = userAddressDetailsRepository
					.getUserAddressEntity(savedUserEntity.getId());
			UserAddressDetails userAddressDetails = userDataMapper
					.mapAgentAddressDetails(existingEntity.getUser().getId(), request);
			if (Objects.isNull(existingUserAddressDetails)) {
				userAddressDetails.setCreatedBy(updateUser);
				userAddressDetails.setCreatedAt(new Date().getTime());
			} else {
				userAddressDetails.setId(existingUserAddressDetails.getId());
				userAddressDetails.setCreatedAt(existingUserAddressDetails.getCreatedAt());
				userAddressDetails.setCreatedBy(existingUserAddressDetails.getCreatedBy());
				userAddressDetails.setLastModifiedBy(updateUser);
				userAddressDetails.setModifiedAt(new Date().getTime());
			}
			UserAddressDetails savedAddressDetails = userAddressDetailsRepository.save(userAddressDetails);
			AddressDetailsModel addressMapModel = userDataMapper.mapAgentAddressDetails(savedAddressDetails);

			UserOtherDetails userOtherDetails = userDataMapper.mapAgentOtherDetails(existingEntity.getUser().getId(),
					request);
			userOtherDetails.setId(existingEntity.getId());
			userOtherDetails.setUser(existingEntity.getUser());
			userOtherDetails.setOrganisation(existingEntity.getOrganisation());
			userOtherDetails.setCreatedAt(existingEntity.getCreatedAt());
			userOtherDetails.setCreatedBy(existingEntity.getCreatedBy());
			userOtherDetails.setModifiedAt(new Date().getTime());
			userOtherDetails.setLastModifiedBy(updateUser);
			UserOtherDetails savedEntity = userOtherDetailsRepository.save(userOtherDetails);
			List<UserProjectMapModel> userProjectMapModel = userProjectMappingRepository
					.getProjectUserMapModel(userOtherDetails.getUser().getId());
			userResponse = userDataMapper.mapAgentDetailsToResponse(request.getOrganisationId(), savedEntity);
			userResponse.setAddress(addressMapModel);
			userResponse.setUserProjectMapModel(userProjectMapModel);
			log.debug("Updating the user details ends");
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.USER_EMAIL_INVALID.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_EMAIL_INVALID);
			} else if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			} else if (Constants.USER_OTHER_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_OTHER_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_ADD_FAIL);
			}
		}
	}

	/**
	 * updateAgentByAdmin is to update the agent details by admin
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional
	public UserDetailsResponseDto updateAgentByAdmin(UserOtherDetailsRequestDto request, String userId) {
		log.debug("Update agent data by admin starts");
		UserDetailsResponseDto userResponse = null;
		try {
			String updateUser = securityUtil.getCurrentUser();
			Optional<User> existingUser = userRepository.findById(userId);
			if (existingUser.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			}
			UserOtherDetails existingEntity = userOtherDetailsRepository.findByUserId(existingUser.get().getId());
			if (existingEntity == null) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_OTHER_DETAILS_NOT_FOUND);
			}

			User entity = userDataMapper.mapAgentDetails(request);
			entity.setId(existingUser.get().getId());
			entity.setEmailId(existingUser.get().getEmailId());
			entity.setPhone(existingUser.get().getPhone());
			entity.setCreatedBy(existingUser.get().getCreatedBy());
			entity.setCreatedAt(existingUser.get().getCreatedAt());
			entity.setModifiedAt(new Date().getTime());
			entity.setLastModifiedBy(updateUser);
			entity.setDetailsUpdated(request.getDetailsUpdated());
			entity.setUserIdpId(existingUser.get().getUserIdpId());
			entity.setActive(existingUser.get().isActive());
			entity.setDeleted(existingUser.get().isDeleted());
			entity.setEmailVerified(existingUser.get().getEmailVerified());
			entity.setInviteStatus(existingUser.get().getInviteStatus());
			User savedUserEntity = userRepository.save(entity);

			UserAddressDetails existingUserAddressDetails = userAddressDetailsRepository
					.getUserAddressEntity(savedUserEntity.getId());
			UserAddressDetails userAddressDetails = userDataMapper
					.mapAgentAddressDetails(existingEntity.getUser().getId(), request);
			if (Objects.isNull(existingUserAddressDetails)) {
				userAddressDetails.setCreatedBy(updateUser);
				userAddressDetails.setCreatedAt(new Date().getTime());
			} else {
				userAddressDetails.setId(existingUserAddressDetails.getId());
				userAddressDetails.setCreatedAt(existingUserAddressDetails.getCreatedAt());
				userAddressDetails.setCreatedBy(existingUserAddressDetails.getCreatedBy());
				userAddressDetails.setLastModifiedBy(updateUser);
				userAddressDetails.setModifiedAt(new Date().getTime());
			}
			UserAddressDetails savedAddressDetails = userAddressDetailsRepository.save(userAddressDetails);
			AddressDetailsModel addressMapModel = userDataMapper.mapAgentAddressDetails(savedAddressDetails);

			UserOtherDetails userOtherDetails = userDataMapper.mapAgentOtherDetails(existingEntity.getUser().getId(),
					request);
			userOtherDetails.setId(existingEntity.getId());
			userOtherDetails.setUser(existingEntity.getUser());
			userOtherDetails.setOrganisation(existingEntity.getOrganisation());

			userOtherDetails.setEmailId(existingEntity.getEmailId());
			userOtherDetails.setContactNumber(existingEntity.getContactNumber());

			userOtherDetails.setCreatedAt(existingEntity.getCreatedAt());
			userOtherDetails.setCreatedBy(existingEntity.getCreatedBy());
			userOtherDetails.setModifiedAt(new Date().getTime());
			userOtherDetails.setLastModifiedBy(updateUser);
			UserOtherDetails savedEntity = userOtherDetailsRepository.save(userOtherDetails);
			List<UserProjectMapModel> userProjectMapModel = userProjectMappingRepository
					.getProjectUserMapModel(userOtherDetails.getUser().getId());
			userResponse = userDataMapper.mapAgentDetailsToResponse(request.getOrganisationId(), savedEntity);
			userResponse.setAddress(addressMapModel);
			userResponse.setUserProjectMapModel(userProjectMapModel);
			log.debug("Updating the user details ends");
			return userResponse;
		} catch (Exception exception) {
			if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_NOT_FOUND);
			} else if (Constants.USER_OTHER_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_OTHER_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_BY_ADMIN_FAIL);
			}
		}
	}

	@Override
	@Transactional
	public UserDetailsResponseDto addAgentByAdmin(UserOtherDetailsRequestDto request) {
		log.debug("Adding user by the admin starts");
		UserDetailsResponseDto userResponse = null;
		if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_ID_NOT_FOUND);
		}
		if (request.getUserRoleId() == null || request.getUserRoleId().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_ROLE_ID_NOT_FOUND);
		}
		if (request.getProjectId() == null || request.getProjectId().length == 0) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_PROJECT_ID_NOT_FOUND);
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
		Optional<Organisation> organisationExisting = organisationRepo.findById(request.getOrganisationId());
		if (organisationExisting.isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_DATA_NOT_FOUND);
		}
		try {
			String updateUser = securityUtil.getCurrentUser();
			User user = userRepository.findByEmailIdIgnoreCase(request.getEmailId());
			User entity = userDataMapper.mapAgentDetails(request);
			if (Objects.isNull(user)) {
				entity.setCreatedBy(updateUser);
			} else {
				throw new Exception(Constants.USER_EMAIL_EXIST);
			}

			entity.setEmailVerified(true);
			entity.setInviteStatus(InviteStatus.PENDING);
			entity.setCreatedBy(updateUser);

			User savedEntity = userRepository.save(entity);
			setPassword(savedEntity.getId(),
					userDataMapper.mapAgentOtherDetailsToUserRequest(savedEntity.getId(), request));

			UserOrganisationMapping userOrgMapEntity = userDataMapper.mapAgentPartnerOrganization(savedEntity.getId(),
					request);
			userOrgMapEntity.setCreatedBy(updateUser);
			userOrgMapEntity.setCreatedAt(new Date().getTime());
			UserOrganisationMapping userOrgMapSavedEntity = userOrganisationMappingRespository.save(userOrgMapEntity);

			List<UserProjectMapModel> mapProjectModel = new ArrayList<>();
			for (String projectId : request.getProjectId()) {
				UserProjectMapping userProjectMapping = userDataMapper.userProjectMapping(savedEntity.getId(),
						projectId);
				userProjectMapping.setCreatedBy(updateUser);
				userProjectMapping.setCreatedAt(new Date().getTime());
				UserProjectMapping userProjectMappingSaved = userProjectMappingRepository.save(userProjectMapping);
				mapProjectModel.add(userDataMapper.userProjectMapping(userProjectMappingSaved));
			}

			UserOtherDetails userOtherDetails = userDataMapper.mapAgentOtherDetails(savedEntity.getId(), request);
			userOtherDetails.setCreatedBy(updateUser);
			UserOtherDetails savedUserOtherDetails = userOtherDetailsRepository.save(userOtherDetails);

			List<String> list = Arrays.asList(request.getProjectId());
			List<ProjectResponseDto> nameList = projectClientApi.getProjectsByIds(list).getData();
			UserRoles role = userRoleRepository.findById(request.getUserRoleId()).get();
			for (ProjectResponseDto projectResponse : nameList) {
				if (projectResponse.getName().equalsIgnoreCase("arpan")) {
					EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
					emailContentRequestDto.setEmailId(request.getEmailId());
					emailContentRequestDto.setSubject(Constants.ARPAN_EMAIL_SUBJECT);
					emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(request.getFirstName()));
					emailContentRequestDto.setPassword(request.getPassword());
					emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
					emailContentRequestDto.setDownloadLink(arpanDownloadLink);
					emailContentRequestDto.setProjectName(projectResponse.getName());
					emailContentRequestDto.setOrgName(organisationExisting.get().getName());
					emailContentRequestDto.setSign("Team- Arpan");
					if (role.getRole().equalsIgnoreCase("FIELD-AGENT")
							|| role.getRole().equalsIgnoreCase("DISTRIBUTION-AGENT")) {
						emailContentRequestDto.setSign("Team- " + organisationExisting.get().getName() + " Arpan");
						emailContentRequestDto.setSubject(
								projectResponse.getName() + " under " + organisationExisting.get().getName());

						if (role.getRole().equalsIgnoreCase("DISTRIBUTION-AGENT")) {
							emailContentRequestDto.setRole("distribution agent");
						} else {
							emailContentRequestDto.setRole("volunteer");
						}

						emailService.inviteFieldAgent(emailContentRequestDto);
					} else {
						emailService.inviteEmailSender(emailContentRequestDto);
					}

					if (savedEntity.getPhone() != null && !savedEntity.getPhone().isEmpty()) {
						smsGatewayService.sendArpanInviteSms(savedEntity.getPhone(),
								FileUtils.convertFirstLetterCapital(request.getFirstName()), request.getEmailId(),
								request.getPassword(), arpanDownloadLink);
					}
				} else if (projectResponse.getName().equalsIgnoreCase("ummeed")) {
					EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
					emailContentRequestDto.setEmailId(request.getEmailId());
					emailContentRequestDto.setSubject(Constants.UMMEED_EMAIL_SUBJECT);
					emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(request.getFirstName()));
					emailContentRequestDto.setPassword(request.getPassword());
					emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
					emailContentRequestDto.setDownloadLink(ummeedDownloadLink);
					emailContentRequestDto.setProjectName(projectResponse.getName());
					emailContentRequestDto.setOrgName(organisationExisting.get().getName());
					emailContentRequestDto.setSign("Team- Ummeed");
					if (role.getRole().equalsIgnoreCase("FIELD-AGENT")
							|| role.getRole().equalsIgnoreCase("DISTRIBUTION-AGENT")) {
						emailContentRequestDto.setSign("Team- " + organisationExisting.get().getName() + " Ummeed");
						emailContentRequestDto.setSubject(
								projectResponse.getName() + " under " + organisationExisting.get().getName());
						if (role.getRole().equalsIgnoreCase("DISTRIBUTION-AGENT")) {
							emailContentRequestDto.setRole("distribution agent");
						} else {
							emailContentRequestDto.setRole("volunteer");
						}

						emailService.inviteFieldAgent(emailContentRequestDto);
					} else {
						emailService.inviteEmailSender(emailContentRequestDto);
					}

					if (savedEntity.getPhone() != null && !savedEntity.getPhone().isEmpty()) {
						smsGatewayService.sendUmmeedInviteSms(savedEntity.getPhone(),
								FileUtils.convertFirstLetterCapital(request.getFirstName()), request.getEmailId(),
								request.getPassword(), ummeedDownloadLink);
					}
				} else {
					EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
					emailContentRequestDto.setEmailId(request.getEmailId());
					emailContentRequestDto.setSubject("Nice to see you on board!");
					emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(request.getFirstName()));
					emailContentRequestDto.setPassword(request.getPassword());
					emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
					emailContentRequestDto.setDownloadLink(sapDownloadLink);
					emailContentRequestDto.setSign("Team- " + projectResponse.getName());
					emailService.inviteEmailSender(emailContentRequestDto);
					if (savedEntity.getPhone() != null && !savedEntity.getPhone().isEmpty()) {
						smsGatewayService.sendSapInviteSms(projectResponse.getName(), savedEntity.getPhone(),
								FileUtils.convertFirstLetterCapital(request.getFirstName()), request.getEmailId(),
								"agent@1234", sapDownloadLink);
					}
				}
			}

			userResponse = userDataMapper.mapAgentDetailsToResponse(request.getOrganisationId(), savedUserOtherDetails);
			userResponse.setUserProjectMapModel(mapProjectModel);
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

	/**
	 * addNewUserWithRole is to add the new generic user
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public UserDetailsResponseDto addNewUserWithRole(NewUserRequest request) {
		log.debug("Adding user by the admin starts");
		UserDetailsResponseDto userResponse = null;
		if (request.getUserRole().getRole() == null || request.getUserRole().getRole().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_ROLE_ID_NOT_FOUND);
		}
		if (request.getProjectId() == null || request.getProjectId().length() == 0) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_PROJECT_ID_NOT_FOUND);
		}
		if (request.getEmailId() == null || request.getEmailId().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_EMAIL_NOT_FOUND);
		}
		if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USERNAME_NOT_FOUND);
		}
		if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_ID_NOT_FOUND);
		}
		String createUser = securityUtil.getCurrentUser();
		try {
			Optional<Organisation> organisationExisting = organisationRepo.findById(request.getOrganisationId());
			if (organisationExisting.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_DATA_NOT_FOUND);
			}
			User entity = userDataMapper.mapAgentDetails(request);
			entity.setEmailVerified(true);
			entity.setInviteStatus(InviteStatus.PENDING);
			entity.setCreatedBy(createUser);
			User savedEntity = userRepository.save(entity);

			UserRequest userRequest = userDataMapper.mapNewUserDetailsToUserRequest(savedEntity.getId(), request);

			UserRoles userRoles = userRoleRepo.findByRole(request.getUserRole().getRole());
			if (Objects.isNull(userRoles)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_ROLE_NOT_FOUND);
			}
			userRequest.setRoleId(userRoles.getId());
			userRequest.setPassword(userRoles.getRole().toLowerCase() + "@1234");
			setPassword(savedEntity.getId(), userRequest);

			UserOrganisationMapping userOrgMapEntity = userDataMapper.mapNewUserOrganization(savedEntity.getId(),
					request.getOrganisationId());
			userOrgMapEntity.setCreatedBy(createUser);
			userOrganisationMappingRespository.save(userOrgMapEntity);

			UserProjectMapping userProjectMapping = userDataMapper.userProjectMapping(savedEntity.getId(),
					request.getProjectId());
			userProjectMapping.setCreatedBy(securityUtil.getCurrentUser());
			userProjectMappingRepository.save(userProjectMapping);

			UserOtherDetails userOtherDetails = userDataMapper.mapNewUserOtherDetails(savedEntity.getId(), request);
			userOtherDetails.setCreatedBy(securityUtil.getCurrentUser());
			UserOtherDetails savedUserOtherDetails = userOtherDetailsRepository.save(userOtherDetails);

			EmailContentRequestDto emailContentRequestDto = frameNewUserEmailRequest(request);
			emailService.sendUserInvitation(emailContentRequestDto);

			// implement notification for peer review
			EmailContentRequestDto emailContentNotificationRequestDto = new EmailContentRequestDto();
			emailContentNotificationRequestDto.setEmailId(request.getEmailId());
			emailContentNotificationRequestDto.setSubject(Constants.SUBJECT_PEER_REVIEW_NOTIFICATION);
			emailContentNotificationRequestDto.setFromMailId("no-reply@consumerlinks.in");
			emailContentNotificationRequestDto.setUserName(FileUtils.convertFirstLetterCapital(request.getFirstName()));
			emailContentNotificationRequestDto.setProjectName(request.getProjectName());
			emailContentNotificationRequestDto.setSign(request.getProjectName());
			emailContentNotificationRequestDto.setEmailId(request.getEmailId());
			emailContentNotificationRequestDto.setOrgName(organisationExisting.get().getName());
			emailService.sendUserNotificationPeerReview(emailContentNotificationRequestDto);

			userResponse = userDataMapper.mapAgentDetailsToResponse(request.getOrganisationId(), savedUserOtherDetails);
			log.debug("Adding user by the admin ends");
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.PASSWORD_ADD_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PASSWORD_ADD_FAIL);
			} else if (Constants.USER_UPDATE_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_FAIL);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_INVITE_FAIL);
			}
		}

	}

	private EmailContentRequestDto frameNewUserEmailRequest(NewUserRequest request) {
		EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
		emailContentRequestDto.setEmailId(request.getEmailId());
		emailContentRequestDto.setSubject(Constants.INVITE_USER_EMAIL_SUBJECT);
		emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(request.getFirstName()));
		emailContentRequestDto.setPassword(request.getUserRole().getRole().toLowerCase() + "@1234");
		emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");

		return emailContentRequestDto;
	}

	/**
	 * getUserDetails is to get the user details
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public UserDetailsResponseDto getUserDetails(String userId) {
		log.debug("Fetch agent details starts");
		try {
			UserDetailsResponseDto userDetailsResponseDto = new UserDetailsResponseDto();
			User userDetails = userRepository.findByUserIdpId(securityUtil.getCurrentUser());
			if (Objects.isNull(userDetails)) {
				throw new Exception(Constants.USER_NOT_FOUND);
			}

			List<UserProjectMapModel> userProjectMapModel = userProjectMappingRepository
					.getProjectUserMapModel(userDetails.getId());
			AddressDetailsModel addressDetailsModel = userAddressDetailsRepository.getUserAddress(userDetails.getId());
			userDetailsResponseDto = userOtherDetailsRepository.getUserOtherDetails(userDetails.getId());

			if (!Objects.isNull(userDetailsResponseDto)) {
				userDetailsResponseDto.setAddress(addressDetailsModel);
				userDetailsResponseDto.setUserProjectMapModel(userProjectMapModel);
				userDetailsResponseDto.setDetailsUpdated(userDetails.getDetailsUpdated());
			} else {
				userDetailsResponseDto = new UserDetailsResponseDto();
				userDetailsResponseDto.setUserId(userDetails.getId());
				userDetailsResponseDto.setFirstName(userDetails.getFirstName());
				userDetailsResponseDto.setMiddleName(userDetails.getMiddleName());
				userDetailsResponseDto.setLastName(userDetails.getLastName());
				userDetailsResponseDto.setEmailId(userDetails.getEmailId());
				userDetailsResponseDto.setContactNumber(userDetails.getPhone());
				userDetailsResponseDto.setAddress(addressDetailsModel);
				userDetailsResponseDto.setUserProjectMapModel(userProjectMapModel);
				userDetailsResponseDto.setDetailsUpdated(userDetails.getDetailsUpdated());
			}

			return userDetailsResponseDto;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_DETAILS_FETCH_FAIL);
			}
		}
	}

	/**
	 * getUserDetailsByEmailId is to fetch the user details by ID and project ID
	 */
	@Override
	public UserDetailsResponseDto getUserDetailsByEmailId(NewUserRequest newUserRequest) {
		log.debug("Fetch agent details starts");
		List<UserProjectMapModel> projectUserMapModel = new ArrayList<>();
		try {
			User userDetails = userRepository.findByEmailIdIgnoreCase(newUserRequest.getEmailId());
			if (Objects.isNull(userDetails)) {
				return addNewUserWithRole(newUserRequest);
			} else {
				Optional<Organisation> organisation = organisationRepo.findById(newUserRequest.getOrganisationId());
				if (organisation.isEmpty()) {
					throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_DATA_NOT_FOUND);
				}

				UserProjectMapModel userProjectMapModel = userProjectMappingRepository
						.findByUserIdAndProjectId(userDetails.getId(), newUserRequest.getProjectId());
				projectUserMapModel.add(userProjectMapModel);
				keycloakService.addRole(newUserRequest.getUserRole().getRole(), userDetails.getUserIdpId());
				UserDetailsResponseDto userDetailsResponseDto = new UserDetailsResponseDto();
				userDetailsResponseDto = userDataMapper.mapAgentDetailsToResponse(newUserRequest.getOrganisationId(),
						userDetails);

				EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
				emailContentRequestDto.setEmailId(newUserRequest.getEmailId());
				emailContentRequestDto.setSubject(Constants.SUBJECT_PEER_REVIEW_NOTIFICATION);
				emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
				emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(newUserRequest.getFirstName()));
				emailContentRequestDto.setProjectName(newUserRequest.getProjectName());
				emailContentRequestDto.setSign(newUserRequest.getProjectName());
				emailContentRequestDto.setEmailId(newUserRequest.getEmailId());
				emailContentRequestDto.setOrgName(organisation.get().getName());
				emailService.sendUserNotificationPeerReview(emailContentRequestDto);
				return userDetailsResponseDto;
			}
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.ORG_DATA_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_DATA_NOT_FOUND);
			} else if (Constants.PASSWORD_ADD_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PASSWORD_ADD_FAIL);
			} else if (Constants.USER_UPDATE_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_FAIL);
			} else if (Constants.USER_INVITE_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_INVITE_FAIL);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_DETAILS_FETCH_FAIL);
			}
		}
	}

	private Boolean emailExist(String emailId) {
		return userRepository.existsByEmailIdIgnoreCase(emailId);
	}

	@Override
	public UserResponse setPassword(String userId, UserRequest request) {
		UserResponse userResponse = null;
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_NOT_FOUND);
		}
		try {
			String userIdpId = keycloakService.createUser(request);

			UserRoles role = userRoleRepo.getOne(request.getRoleId());
			keycloakService.addRole(role.getRole(), userIdpId);
			User entity = user.get();
			entity.setUserIdpId(userIdpId);
			entity.setLastModifiedBy(userIdpId);
			entity.setModifiedAt(new Date().getTime());
			User savedEntity = userRepository.save(entity);

			UserRoleMapping userRoleMapping = new UserRoleMapping();
			userRoleMapping.setUser(savedEntity);
			userRoleMapping.setUserRoles(role);
			userRoleMapping.setCreatedBy(userIdpId);
			userRoleMapping.setCreatedAt(new Date().getTime());
			userRoleMappingRepo.save(userRoleMapping);
			userResponse = userDataMapper.userBodyEntityToResponseMapper(savedEntity);
			return userResponse;
		} catch (Exception exception) {
			String stackTrace = ExceptionUtils.getStackTrace(exception);
			log.error(stackTrace);
			if (exception.getMessage().equals(Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS)) {
				throw new SOException(ErrorCode.CONFLICT, Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS);
			} else if (Constants.PASSWORD_ADD_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PASSWORD_ADD_FAIL);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_FAIL);
			}
		}
	}

	@Override
	public UserResponse resetPasswordByPhone(String userId, UserRequest request) {
		// keycloakService.updateCredentials(request.getUserIdpId(),
		// request.getPassword());
		return null;
	}

	@Override
	@Transactional
	public UserResponse resetPassword(UserRequest request) {
		UserResponse userResponse = null;
		try {

			String userIdpId = securityUtil.getCurrentUser();

			keycloakService.updateCredentials(userIdpId, request.getPassword());
			User user = userRepository.findByUserIdpId(userIdpId);
			userResponse = userDataMapper.userBodyEntityToResponseMapper(user);
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PASSWORD_RESET_FAIL);
		}
	}

	@Override
	@Transactional
	public UserResponse updatePassword(UpdatePasswordRequest request) {
		UserResponse userResponse = null;
		User user = userRepository.findByEmailIdIgnoreCase(request.getEmail());
		if (user == null) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
		}
		try {

			keycloakService.updateCredentials(user.getUserIdpId(), request.getPassword());
			userResponse = userDataMapper.userBodyEntityToResponseMapper(user);
			return userResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_PASSWORD_UPDATE_FAIL);
		}
	}

	private User findById(String id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_NOT_FOUND);
		}
		return user.get();
	}

	@Override
	public UserCommonResponse getUser() {
		String userIdpId = securityUtil.getCurrentUser();
//		List<String> organisations = orgRepo.getOrganisationIdOfUser(userIdpId);

		User user = userRepository.findByUserIdpId(userIdpId);
		List<String> organisations = userOrgRepo.getOrganisationsIdOfUser(user.getId());
		List<String> projects = userProjectMappingRepository.getProjectIdOfUser(user.getId());
		GoodPlatformResponseVO<List<ProjectResponseDto>> projectDetails = projectClientApi.getProjectsByIds(projects);
		String userRole = userRoleMappingRepo.getUserRole(user.getId());
		List<ProjectModel> dataList = new ArrayList<>();
		for (ProjectResponseDto projectData : projectDetails.getData()) {
			dataList.add(new ProjectModel(projectData.getId(), projectData.getName(),
					projectData.getIsImmunizationProject()));
		}
		return new UserCommonResponse(user.getId(), user.getFirstName(), user.getLastName(), organisations, dataList,
				user.getEmailId(), userRole, user.getDetailsUpdated(), user.getInviteStatus());
	}

	@Override
	public UserDataExistenceResponse findUserExistenceByEmailId(String emailId) {
		log.debug("Check user existence in user table by email ID starts");
		UserDataExistenceResponse response = new UserDataExistenceResponse();
		try {
			User userData = userRepository.findByEmailIdIgnoreCase(emailId);
			if (Objects.isNull(userData)) {
				throw new Exception(Constants.USER_NOT_FOUND);
			}
			response.setResponseCode(HttpStatus.OK.value());
			response.setUserExists(true);
			response.setMessage(Constants.USER_GET_SUCCESS);
			log.debug("Check user existence in user table by email ID ends");
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				response.setResponseCode(HttpStatus.NOT_FOUND.value());
				response.setUserExists(false);
				response.setMessage(Constants.USER_EMAIL_NOT_FOUND);
				log.debug("Check user existence in user table by email ID ends");
				return response;
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_GET_FAIL);
			}
		}
	}

	@Override
	public UserDataExistenceResponse findUserExistenceByPhoneNumber(String phoneNumber) {
		log.debug("Check user existence in user table by phone number starts");
		UserDataExistenceResponse response = new UserDataExistenceResponse();
		try {
			User userData = userRepository.findByPhone(phoneNumber);
			if (Objects.isNull(userData)) {
				throw new Exception(Constants.USER_NOT_FOUND);
			}
			response.setResponseCode(HttpStatus.OK.value());
			response.setUserExists(true);
			response.setMessage(Constants.USER_GET_SUCCESS);
			log.debug("Check user existence in user table by phone number ends");
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.USER_NOT_FOUND.equals(exception.getMessage())) {
				response.setResponseCode(HttpStatus.NOT_FOUND.value());
				response.setUserExists(false);
				response.setMessage(Constants.USER_PHONE_NUMBER_NOT_FOUND);
				log.debug("Check user existence in user table by phone number ends");
				return response;
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_GET_FAIL);
			}
		}
	}

	@Override
	@Transactional
	public UserProjectMappingResponse mapUserProject(UserProjectMappingRequest userProjectMappingRequest) {
		log.debug("Mapping user and the project starts");
		if (userProjectMappingRequest.getUserIdpId() == null || userProjectMappingRequest.getUserIdpId().isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_IDP_ID_INVALID);
		}
		User user = userRepository.findByUserIdpId(userProjectMappingRequest.getUserIdpId());
		if (Objects.isNull(user)) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
		}
		try {
			UserProjectMapping userProjectMapping = userDataMapper.userProjectMapping(user.getId(),
					userProjectMappingRequest.getProjectId());
			userProjectMapping.setCreatedBy(securityUtil.getCurrentUser());
			userProjectMapping.setCreatedAt(new Date().getTime());
			UserProjectMapping userProjectMappingSaved = userProjectMappingRepository.save(userProjectMapping);
			List<String> userProjectMappedList = userProjectMappingRepository.getProjectIdOfUser(user.getId());
			UserProjectMappingResponse userProjectMappingResponse = userDataMapper.userProjectResponseMapping(
					userProjectMappingSaved.getId(), userProjectMappingSaved.getUser().getId());
			userProjectMappingResponse.setProjectId(userProjectMappedList);
			return userProjectMappingResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_PROJET_MAPPING_FAILED);
		}
	}

	@Override
	public CountResponse getItemsCount() {
		CountResponse response = new CountResponse();
		GoodPlatformResponseVO<Long> projectCount = projectClientApi.getProjectCount();
		try {
			response.setTotalProjects(projectCount.getData());
			String userIdpId = securityUtil.getCurrentUser();
			Long agentCount = userRepository.getAgentCount("FIELD-AGENT", userIdpId);
			response.setTotalAgents(agentCount);
			Long orgCount = orgRepo.countByCreatedBy(userIdpId);
			response.setTotalOrganisations(orgCount);
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FETCHING_COUNT_DETAILS_FAIL);
		}
	}

	@Override
	public OtpRegisterResponseDTO sendOtptToForgotPassword(String email) {

		if (!userRepository.existsByEmailIdIgnoreCase(email)) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_EMAIL_NOT_FOUND);
		}
		User user = userRepository.findByEmailIdIgnoreCase(email);
		if (user.getUserIdpId() == null || user.getEmailVerified() == null || user.getEmailVerified() == false) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_EMAIL_NOT_VERIFIED);
		}
		String otp = OtpUtility.generateOTP();
		Long expTime = OtpUtility.getExpirationTime(EmailUtils.EXPIRATION_MINUTES);
		return emailService.generateEmailOtpToRegister(email, expTime, otp);

	}

	@Override
	public OtpRegisterResponseDTO otpVerification(ConfirmEmailAccountRequestDto request) {
		User existingEntity = userRepository.findByEmailIdIgnoreCase(request.getEmail());
		if (Objects.isNull(existingEntity)) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EMAIL);
		}

		if (emailService.activateUserAccountByEmail(request)) {
			OtpRegisterResponseDTO response = new OtpRegisterResponseDTO("success", request.getEmail(),
					existingEntity.getId(), null);
			return response;
		} else {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL_OTP);
		}
	}

	@Override
	public List<UserProjectOrgIdModel> getProjectIdsByUserIdpId() {
		log.debug("Fetch project Ids by userIdpId");
		try {
			User user = userRepository.findByUserIdpId(securityUtil.getCurrentUser());
			List<String> userProjectIds = userProjectMappingRepository.getProjectIdOfUser(user.getId());
			if (userProjectIds.isEmpty()) {
				throw new Exception(Constants.NO_PROJECT_MAPPINGS_FOUND);
			}
			GoodPlatformResponseVO<List<ProjectIdNameModel>> userProjectOrgId = projectClientApi
					.getProjectsIdsNames(userProjectIds);
			if (userProjectOrgId.getData() == null || userProjectOrgId.getData().isEmpty()) {
				throw new Exception(Constants.NO_PROJECT_MAPPINGS_FOUND);
			}

			List<UserProjectOrgIdModel> userProjectOrgIdModelList = new ArrayList<>();
			ConcurrentMap<String, List<ProjectIdNameModel>> dataList = userProjectOrgId.getData().parallelStream()
					.collect(Collectors.groupingByConcurrent(ProjectIdNameModel::getOrganisationId));
			dataList.forEach((k, v) -> {
				UserProjectOrgIdModel userProjectOrgIdResponseModel = new UserProjectOrgIdModel();
				userProjectOrgIdResponseModel.setOrganisationId(k);
				userProjectOrgIdResponseModel.setProjectModel(v);
				userProjectOrgIdResponseModel.setOrganisationName(orgRepo.getOrganisationName(k));
				userProjectOrgIdModelList.add(userProjectOrgIdResponseModel);
			});
			return userProjectOrgIdModelList;
		} catch (Exception exception) {
			if (Constants.NO_PROJECT_MAPPINGS_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.NO_PROJECT_MAPPINGS_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PROJECT_IDS_FETCH_FAIL);
			}
		}
	}

	@Override
	public PaginatedResponse<InvitedUsersResponse> getInvitedUsers(String organisationId, String projectId,
			String roleId, Integer pageNumber, Integer pageSize, InviteStatus inviteStatus, String name) {
		Pageable page = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
		try {
			List<InvitedUsersResponse> response = new ArrayList<>();
			List<InvitedUsersResponse> filteredUser = new ArrayList<>();
			String status = inviteStatus != null ? inviteStatus.toString() : null;
			// Page<InvitedUsersResponse> invitedUsers =
			// orgRepo.getInvitesdUsers(organisationId, roleId, projectId,
			// inviteStatus.toString(), page);
			String userName = !StringUtils.isEmpty(name) ? name.toLowerCase() : null;

			String userIdpId = securityUtil.getCurrentUser();

			Page<InvitedUsersProjection> invitedUsers;

			if (StringUtils.isEmpty(projectId)) {
				invitedUsers = orgRepo.getInvitesdUsers(organisationId, roleId, status, userName, userIdpId, page);
			} else {
				invitedUsers = orgRepo.getInvitesdUsersWithProjectId(organisationId, roleId, projectId, userIdpId,
						status, userName, page);
			}
			if (invitedUsers.getContent() != null) {
				invitedUsers.getContent().forEach(item -> {
					filteredUser.add(new InvitedUsersResponse(item));
				});
			}
			for (InvitedUsersResponse user : filteredUser) {
				List<String> projectIds = userProjectMappingRepository.getProjectIdOfUser(user.getUserId());
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
	public InviteResendResponse resendUserInvitation(UserOtherDetailsRequestDto request) {

		User existingEntity = userRepository.findByEmailIdIgnoreCase(request.getEmailId());
		if (Objects.isNull(existingEntity)) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EMAIL);
		}
		if (!existingEntity.getInviteStatus().equals(InviteStatus.PENDING)) {
			throw new SOException(ErrorCode.INVALID_OPERATION, Constants.INVITE_STATUS_MISMATCH);
		}
		List<String> list = Arrays.asList(request.getProjectId());
		List<ProjectResponseDto> nameList = projectClientApi.getProjectsByIds(list).getData();
		for (ProjectResponseDto projectResponse : nameList) {
			if (projectResponse.getName().equalsIgnoreCase("arpan")) {
				keycloakService.updateCredentials(existingEntity.getUserIdpId(), "agent@1234");
				EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
				emailContentRequestDto.setEmailId(request.getEmailId());
				emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(existingEntity.getFirstName()));
				emailContentRequestDto.setPassword("agent@1234");
				emailContentRequestDto.setSubject(Constants.ARPAN_EMAIL_SUBJECT);
				emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
				emailContentRequestDto.setDownloadLink(arpanDownloadLink);
				emailContentRequestDto.setSign("Team- Arpan");
				emailService.inviteEmailSender(emailContentRequestDto);
				if (existingEntity.getPhone() != null && !existingEntity.getPhone().isEmpty()) {
					smsGatewayService.sendArpanInviteSms(existingEntity.getPhone(),
							FileUtils.convertFirstLetterCapital(existingEntity.getFirstName()), request.getEmailId(),
							"agent@1234", arpanDownloadLink);
				}
			}
			if (projectResponse.getName().equalsIgnoreCase("ummeed")) {
				keycloakService.updateCredentials(existingEntity.getUserIdpId(), "agent@1234");
				EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
				emailContentRequestDto.setEmailId(request.getEmailId());
				emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(existingEntity.getFirstName()));
				emailContentRequestDto.setPassword("agent@1234");
				emailContentRequestDto.setSubject(Constants.UMMEED_EMAIL_SUBJECT);
				emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
				emailContentRequestDto.setDownloadLink(ummeedDownloadLink);
				emailContentRequestDto.setSign("Team- Ummeed");
				emailService.inviteEmailSender(emailContentRequestDto);
				if (existingEntity.getPhone() != null && !existingEntity.getPhone().isEmpty()) {
					Boolean result = smsGatewayService.sendUmmeedInviteSms(existingEntity.getPhone(),
							FileUtils.convertFirstLetterCapital(existingEntity.getFirstName()), request.getEmailId(),
							"agent@1234", ummeedDownloadLink);

				}
			} else {
				keycloakService.updateCredentials(existingEntity.getUserIdpId(), "reinvite@1234");
				String passwordData = !StringUtils.isEmpty(request.getPassword()) ? request.getPassword()
						: "reinvite@1234";

				EmailContentRequestDto emailContentRequestDto = new EmailContentRequestDto();
				emailContentRequestDto.setEmailId(request.getEmailId());
				emailContentRequestDto.setSubject("Nice to see you on board!");
				emailContentRequestDto.setUserName(FileUtils.convertFirstLetterCapital(existingEntity.getFirstName()));
				emailContentRequestDto.setPassword(passwordData);
				emailContentRequestDto.setFromMailId("no-reply@consumerlinks.in");
				emailContentRequestDto.setDownloadLink(sapDownloadLink);
				emailContentRequestDto.setSign("Team- " + projectResponse.getName());
				emailService.inviteEmailSender(emailContentRequestDto);
				if (existingEntity.getPhone() != null && !existingEntity.getPhone().isEmpty()) {
					smsGatewayService.sendSapInviteSms(projectResponse.getName(), existingEntity.getPhone(),
							FileUtils.convertFirstLetterCapital(existingEntity.getFirstName()), request.getEmailId(),
							passwordData, sapDownloadLink);
				}
			}
		}
		return new InviteResendResponse(existingEntity.getEmailId(), "Succes");

	}

	/**
	 * @implNote Intention of this method to get social-admin by the organisation
	 *           and for the mg-beneficiary service
	 * @author Arya C Achari
	 * @since 12-Jan-2022
	 * 
	 * @param organisationId
	 * @return
	 */
	@Override
	public List<SocialAdminDetails> getSocialAdminDetails(String organisationId) {
		if (StringUtils.isEmpty(organisationId))
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_ID_NOT_FOUND);
		try {
			List<SocialAdminDetails> adminList = new ArrayList<SocialAdminDetails>();
			List<SocialAdminDetailsProjection> adminProjectionList = userRepository
					.findSocialAdminDetails(organisationId);
			if (adminProjectionList != null) {
				adminProjectionList.forEach(item -> {
					adminList.add(new SocialAdminDetails(item));
				});
				return adminList;
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error(ExceptionUtils.getMessage(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @see branch: user-by-id-minumum-details
	 * @author Arya C Achari
	 * @since 12-Jan-2021
	 * 
	 * @return
	 */
	@Override
	public UserMinDetails getUserMinimumDetails(String id) {
		try {
			return userRepository.findUserByUserIdpId(id);
		} catch (Exception ex) {
			log.debug(ExceptionUtils.getMessage(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_DETAILS_FETCH_FAIL);
		}
	}

	@Override
	public List<AgentDetailsDTO> getAgentDetails(String organisationId, String projectId, String date) {

		UserRoles role = userRoleRepo.findByRole("FIELD-AGENT");
		List<AgentDetailsDTO> agentDetails = userRepository.getAgentDetails(organisationId, projectId, role.getId());
		List<AgentDetailsDTO> result = new ArrayList<>();
		if (agentDetails != null && !agentDetails.isEmpty()) {
			for (AgentDetailsDTO agent : agentDetails) {

				agent.setBeneficiaryCount(
						beneficiaryClient.getCountByFieldAgentOwned(agent.getUserIdpId(), date).getData());
				result.add(agent);
			}
		}
		return result;
	}

	@Override
	public boolean updateUserStatus(String userIdpId, boolean status) {
		log.debug("Fetch project Ids by userIdpId");
		boolean success = false;
		if (userIdpId == null || userIdpId.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_IDP_ID_INVALID);
		}
		try {
			User existingEntity = userRepository.findByUserIdpId(userIdpId);
			if (Objects.isNull(existingEntity)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			}
			existingEntity.setActive(status);
			existingEntity.setModifiedAt(new Date().getTime());
			existingEntity.setLastModifiedBy(securityUtil.getCurrentUser());
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
			if (("enable").equals(status)) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_ENABLE_FAIL);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_DISABLE_FAIL);
			}
		}
		return response;
	}

	@Override
	@Transactional
	public UserResponse getVolunteer(String userId, String organisationId) {
		if (userOrganisationMappingRespository.existsByOrganisation_IdAndUser_Id(organisationId, userId)) {
			Optional<User> savedEntity = userRepository.findById(userId);
			if (savedEntity.isPresent()) {
				List<String> projectIds = userProjectMappingRepository.getProjectIdOfUser(savedEntity.get().getId());
				UserRoles roleData = userRoleMappingRepository.getUserRoleId(savedEntity.get().getId());
				UserResponse response = userDataMapper.userBodyEntityToResponseMapper(savedEntity.get());
				response.setProjectsId(projectIds);
				response.setRoleId(roleData.getId());
				response.setRole(roleData.getRole());
				return response;
			} else {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
			}

		} else {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
		}

	}

	/**
	 * Update detailss_updated & invite_status of user
	 * 
	 * @author Arya C Achari
	 * @since 15-Nov-2021
	 *
	 * @param id
	 * @return response
	 */
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

	/**
	 * Find the User details by Id(String)
	 * 
	 * @author Arya C Achari
	 * @since 15-Nov-2021
	 * 
	 */
	@Override
	public User findUserDetails(String id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND));
	}

	@Override
	public String getUserIdByIdpId(String idpId) {
		log.debug("Fetch user ID by user Idp id starts");
		try {
			User user = userRepository.findByUserIdpId(idpId);
			return user.getId();
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND);
		}
	}

	/**
	 * Update the invite status of user on initial login
	 * 
	 * @author Arya C Achari
	 * @since 08-Feb-2022
	 * 
	 * @param userIdpId
	 * @return
	 */
	@Override
	public String updateUserInviteStatus() {
		String userIdpId = securityUtil.getCurrentUser();
		log.info("Starting the initial login user's invite status update process.");
		try {
			User user = userRepository.findByUserIdpId(userIdpId);
			if (user != null) {
				if (user.getInviteStatus() == null || !user.getInviteStatus().equals(InviteStatus.ACCEPTED)) {
					log.info("Update if the user's status is PENDING or Null");
					user.setInviteStatus(InviteStatus.ACCEPTED);
					user.setModifiedAt(new Date().getTime());
					user.setLastModifiedBy(userIdpId);
					user = userRepository.save(user);
				}
				log.info("Update is completed");
				return user.getInviteStatus().toString();
			} else
				return null;
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_INVITE_STATUS_UPDATE_FAIL);
		}
	}

	@Override
	public void updateRole(String userId, String newRoleId, String oldRoleId) {
		UserRoles oldRole = userRoleRepo.getOne(oldRoleId);
		UserRoles newRole = userRoleRepo.getOne(newRoleId);
		// User user=userRepository.findByEmailId(emailId);
		UserMinDetails user = getUserMinimumDetails(userId);

		if (user != null) {
			keycloakService.updateRole(oldRole.getRole(), newRole.getRole(), user.getUserId());
			UserRoleMapping existingUserRoleMapping = userRoleMappingRepo.findByUser_Id(user.getId());
			existingUserRoleMapping.setUserRoles(newRole);
			existingUserRoleMapping.setModifiedAt(new Date().getTime());
			existingUserRoleMapping.setLastModifiedBy(securityUtil.getCurrentUser());
			userRoleMappingRepo.save(existingUserRoleMapping);
		}
	}

}
