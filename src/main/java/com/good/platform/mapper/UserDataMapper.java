package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;
import com.good.platform.entity.UserAddressDetails;
import com.good.platform.entity.UserOrganisationMapping;
import com.good.platform.entity.UserOtherDetails;
import com.good.platform.entity.UserProjectMapping;
import com.good.platform.entity.UserRoleMapping;
import com.good.platform.entity.UserRoles;
import com.good.platform.helper.MappingHelper;
import com.good.platform.model.AddressDetailsModel;
import com.good.platform.model.UserProjectMapModel;
import com.good.platform.request.NewUserRequest;
import com.good.platform.request.RegisterDTO;
import com.good.platform.request.dto.BeneficiaryRegisterDTO;
import com.good.platform.request.dto.UserOtherDetailsRequestDto;
import com.good.platform.request.dto.UserRequest;
import com.good.platform.response.dto.UserDetailsResponseDto;
import com.good.platform.response.dto.UserProjectMappingResponse;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.response.dto.UserStatusResponse;

@Mapper(componentModel = "spring", imports = MappingHelper.class)
public interface UserDataMapper {

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(target = "userRoles", expression = "java(MappingHelper.getUserRoleObject(userRoleId))") })
	public UserRoleMapping mapUserRole(String userId, String userRoleId);

	@Named("getUserRoleData")
	default UserRoles getUserRoleData(String userRoleId) {
		return new UserRoles(userRoleId);
	}

	@Mappings({ @Mapping(source = "id", target = "id"), @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "contactNumber", target = "phone"), @Mapping(source = "emailId", target = "userName"),
			@Mapping(source = "middleName", target = "middleName") })
	public User mapAgentDetails(UserOtherDetailsRequestDto userOtherDetailsRequestDto);

	@Mappings({ @Mapping(source = "userOtherDetailsRequestDto.firstName", target = "firstName"),
			@Mapping(source = "userOtherDetailsRequestDto.middleName", target = "middleName"),
			@Mapping(source = "userOtherDetailsRequestDto.lastName", target = "lastName"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "emailId"),
			@Mapping(source = "userOtherDetailsRequestDto.contactNumber", target = "phone"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "userName") })
	public User mapAgentDetails(NewUserRequest userOtherDetailsRequestDto);

	@Mappings({ @Mapping(source = "id", target = "id"), @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "phone", target = "phone"), @Mapping(source = "userName", target = "userName") })
	public User userValuesMapper(UserRequest dto);

	@Mappings({ @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "phone", target = "phone"), @Mapping(source = "userName", target = "userName") })
	public UserRequest userValuesMapper(User entity);

	@Mappings({ @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "phone", target = "phone"), @Mapping(source = "userName", target = "userName") })
	public UserRequest userValuesMapper(UserResponse entity);

	@Mappings({ @Mapping(source = "id", target = "id"), @Mapping(source = "active", target = "active"),
			@Mapping(source = "deleted", target = "deleted"), @Mapping(source = "createdAt", target = "createdAt"),
			@Mapping(source = "modifiedAt", target = "modifiedAt"),
			@Mapping(source = "createdBy", target = "createdBy"),
			@Mapping(source = "lastModifiedBy", target = "lastModifiedBy"),
			@Mapping(source = "userIdpId", target = "userIdpId"), @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "phone", target = "phone"), @Mapping(source = "userName", target = "userName") })
	public UserResponse userBodyEntityToResponseMapper(User entity);

	@Mappings({ @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "phone", target = "phone"), @Mapping(source = "userName", target = "userName") })
	public UserResponse userBodyRequestToResponseMapper(UserRequest entity);

	@Mappings({ @Mapping(source = "userOtherDetailsRequestDto.id", target = "id"),
			@Mapping(source = "userOtherDetailsRequestDto.firstName", target = "firstName"),
			@Mapping(source = "userOtherDetailsRequestDto.middleName", target = "middleName"),
			@Mapping(source = "userOtherDetailsRequestDto.lastName", target = "lastName"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "emailId"),
			@Mapping(source = "userOtherDetailsRequestDto.contactNumber", target = "contactNumber"),
			@Mapping(source = "userOtherDetailsRequestDto.educationQualification", target = "education"),
			@Mapping(source = "userOtherDetailsRequestDto.educationDetails", target = "educationDetails"),
			@Mapping(source = "userOtherDetailsRequestDto.organisationId", target = "organisation", qualifiedByName = "getOrganisationData"),
			@Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"), })
	public UserOtherDetails mapAgentOtherDetails(String userId, UserOtherDetailsRequestDto userOtherDetailsRequestDto);

	@Mappings({ @Mapping(source = "userOtherDetailsRequestDto.firstName", target = "firstName"),
			@Mapping(source = "userOtherDetailsRequestDto.middleName", target = "middleName"),
			@Mapping(source = "userOtherDetailsRequestDto.lastName", target = "lastName"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "emailId"),
			@Mapping(source = "userOtherDetailsRequestDto.contactNumber", target = "contactNumber"),
			@Mapping(target = "organisation", expression = "java(MappingHelper.mapOrganisationEntity(userOtherDetailsRequestDto.getOrganisationId()))"),
			@Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))") })
	public UserOtherDetails mapNewUserOtherDetails(String userId, NewUserRequest userOtherDetailsRequestDto);

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(source = "userOtherDetailsRequestDto.addressModel.addressLine1", target = "addressLine1"),
			@Mapping(source = "userOtherDetailsRequestDto.addressModel.streetName", target = "streetName"),
			@Mapping(source = "userOtherDetailsRequestDto.addressModel.city", target = "city"),
			@Mapping(source = "userOtherDetailsRequestDto.addressModel.state", target = "state"),
			@Mapping(source = "userOtherDetailsRequestDto.addressModel.pincode", target = "pincode"),
			@Mapping(source = "userOtherDetailsRequestDto.addressModel.latitude", target = "latitude"),
			@Mapping(source = "userOtherDetailsRequestDto.addressModel.longitude", target = "longitude") })
	public UserAddressDetails mapAgentAddressDetails(String userId,
			UserOtherDetailsRequestDto userOtherDetailsRequestDto);

	@Mappings({ @Mapping(source = "userAddressDetails.user.id", target = "userId"),
			@Mapping(source = "userAddressDetails.addressLine1", target = "addressLine1"),
			@Mapping(source = "userAddressDetails.streetName", target = "streetName"),
			@Mapping(source = "userAddressDetails.city", target = "city"),
			@Mapping(source = "userAddressDetails.state", target = "state"),
			@Mapping(source = "userAddressDetails.pincode", target = "pincode"),
			@Mapping(source = "userAddressDetails.latitude", target = "latitude"),
			@Mapping(source = "userAddressDetails.longitude", target = "longitude") })
	public AddressDetailsModel mapAgentAddressDetails(UserAddressDetails userAddressDetails);

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(source = "userOtherDetailsRequestDto.organisationId", target = "organisation", qualifiedByName = "getOrganisationData") })
	public UserOrganisationMapping mapAgentPartnerOrganization(String userId,
			UserOtherDetailsRequestDto userOtherDetailsRequestDto);

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(target = "organisation", expression = "java(MappingHelper.mapOrganisationEntity(organisationId))") })
	public UserOrganisationMapping mapNewUserOrganization(String userId, String organisationId);

	@Named("getOrganisationData")
	default Organisation getOrganisationData(String organisationId) {
		return new Organisation(organisationId);
	}

	@Named("getUserAddressDetails")
	default UserAddressDetails getUserAddressDetails(String addressId) {
		return new UserAddressDetails(addressId);
	}

	@Named("getUserPartnerOrganisationDetails")
	default UserOrganisationMapping getUserPartnerOrganisationDetails(String partnershipId) {
		return new UserOrganisationMapping(partnershipId);
	}

	@Named("getUserProjectDetails")
	default UserProjectMapping getUserProjectDetails(String userProjectId) {
		return new UserProjectMapping(userProjectId);
	}

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(source = "projectId", target = "projectId") })
	public UserProjectMapping userProjectMapping(String userId, String projectId);

	@Mappings({ @Mapping(target = "projectId", source = "userProjectMapping.projectId"),
			@Mapping(target = "userId", source = "userProjectMapping.user.id") })
	public UserProjectMapModel userProjectMapping(UserProjectMapping userProjectMapping);

	@Mappings({ @Mapping(target = "id", source = "userProjectMappingId"),
			@Mapping(target = "userId", source = "userId") })
	public UserProjectMappingResponse userProjectResponseMapping(String userProjectMappingId, String userId);

	@Mappings({ @Mapping(source = "userId", target = "id"),
			@Mapping(source = "userOtherDetailsRequestDto.firstName", target = "firstName"),
			@Mapping(source = "userOtherDetailsRequestDto.lastName", target = "lastName"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "emailId"),
			@Mapping(source = "userOtherDetailsRequestDto.contactNumber", target = "phone"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "userName"),
			@Mapping(source = "userOtherDetailsRequestDto.userRoleId", target = "roleId") })
	public UserRequest mapAgentOtherDetailsToUserRequest(String userId,
			UserOtherDetailsRequestDto userOtherDetailsRequestDto);

	@Mappings({ @Mapping(source = "userId", target = "id"),
			@Mapping(source = "userOtherDetailsRequestDto.firstName", target = "firstName"),
			@Mapping(source = "userOtherDetailsRequestDto.lastName", target = "lastName"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "emailId"),
			@Mapping(source = "userOtherDetailsRequestDto.contactNumber", target = "phone"),
			@Mapping(source = "userOtherDetailsRequestDto.emailId", target = "userName"), })
	public UserRequest mapNewUserDetailsToUserRequest(String userId, NewUserRequest userOtherDetailsRequestDto);

	@Mappings({ @Mapping(source = "userOtherDetails.id", target = "id"),
			@Mapping(source = "userOtherDetails.user.id", target = "userId"),
			@Mapping(source = "userOtherDetails.firstName", target = "firstName"),
			@Mapping(source = "userOtherDetails.lastName", target = "lastName"),
			@Mapping(source = "userOtherDetails.emailId", target = "emailId"),
			@Mapping(source = "userOtherDetails.contactNumber", target = "contactNumber"),
			@Mapping(source = "organisationId", target = "organisationId"),
			@Mapping(source = "userOtherDetails.educationDetails", target = "educationDetails"),
			@Mapping(source = "userOtherDetails.education", target = "education") })
	public UserDetailsResponseDto mapAgentDetailsToResponse(String organisationId, UserOtherDetails userOtherDetails);

	@Mappings({ @Mapping(source = "userOtherDetails.id", target = "userId"),
			@Mapping(source = "userOtherDetails.firstName", target = "firstName"),
			@Mapping(source = "userOtherDetails.middleName", target = "middleName"),
			@Mapping(source = "userOtherDetails.lastName", target = "lastName"),
			@Mapping(source = "userOtherDetails.emailId", target = "emailId"),
			@Mapping(source = "userOtherDetails.phone", target = "contactNumber"),
			@Mapping(target = "organisationId", source = "organisationId") })
	public UserDetailsResponseDto mapAgentDetailsToResponse(String organisationId, User userOtherDetails);

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(target = "organisation", expression = "java(MappingHelper.getOrganisationObject(organisationId))") })
	public UserOrganisationMapping mapAgentPartnerOrganization(String userId, String organisationId);

	/**
	 * @author Arya C Achari
	 * @since 16-Nov-2021
	 * 
	 * @param entity
	 * @return
	 */
	@Mappings({ @Mapping(source = "id", target = "id"), @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "inviteStatus", target = "inviteStatus"),
			@Mapping(source = "detailsUpdated", target = "detailsUpdated") })
	public UserStatusResponse userEntityMappingToUserStatusResponse(User entity);

	@Mappings({

			@Mapping(source = "firstName", target = "firstName"), @Mapping(source = "lastName", target = "lastName"),
			@Mapping(source = "emailId", target = "emailId"), @Mapping(source = "contactNumber", target = "phone"),
			@Mapping(source = "emailId", target = "userName"), @Mapping(source = "middleName", target = "middleName") })
	public User mapAdminDetails(UserOtherDetailsRequestDto userOtherDetailsRequestDto);

	@Mappings({ @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "phone", target = "phone"), @Mapping(source = "middleName", target = "middleName") })
	public User mapBeneficiaryDetails(RegisterDTO request);

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(source = "request.organisationId", target = "organisation", qualifiedByName = "getOrganisationData") })
	public UserOrganisationMapping mapOrganization(String userId, RegisterDTO request);

	@Mappings({ @Mapping(source = "emailId", target = "emailId"), @Mapping(source = "password", target = "password") })
	public UserRequest mapUserEntity(RegisterDTO entity);

	@Mappings({ @Mapping(source = "firstName", target = "firstName"),
			@Mapping(source = "lastName", target = "lastName"), @Mapping(source = "emailId", target = "emailId"),
			@Mapping(source = "phone", target = "phone"), @Mapping(source = "middleName", target = "middleName") })
	public User mapToUserFromBeneficiaryRegisterDTO(BeneficiaryRegisterDTO request);

	@Mappings({ @Mapping(source = "emailId", target = "emailId"), @Mapping(source = "password", target = "password") })
	public UserRequest mapUserEntityFromBeneficiaryRegisterDTO(BeneficiaryRegisterDTO entity);

	@Mappings({ @Mapping(target = "user", expression = "java(MappingHelper.getUserObject(userId))"),
			@Mapping(source = "request.organisationId", target = "organisation", qualifiedByName = "getOrganisationData") })
	public UserOrganisationMapping mapOrganizationFromBeneficiaryRegisterDTO(String userId, BeneficiaryRegisterDTO request);

}
