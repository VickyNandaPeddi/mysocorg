package com.good.platform.service;

import java.util.List;

import com.good.platform.entity.User;
import com.good.platform.enums.InviteStatus;
import com.good.platform.model.UserProjectOrgIdModel;
import com.good.platform.request.NewUserRequest;
import com.good.platform.request.dto.ConfirmAccountPasswordRequest;
import com.good.platform.request.dto.ConfirmEmailAccountRequestDto;
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
import com.good.platform.response.dto.InviteResendResponse;
import com.good.platform.response.dto.OtpRegisterResponseDTO;
import com.good.platform.response.dto.SocialAdminDetails;
import com.good.platform.response.dto.UserDataExistenceResponse;
import com.good.platform.response.dto.UserDetailsResponseDto;
import com.good.platform.response.dto.UserProjectMappingResponse;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.response.dto.UserStatusResponse;

public interface UserService {

	UserResponse addUser(UserRequest request);

	UserCommonResponse getUser();

	UserResponse confirmAccount(String userId, ConfirmEmailAccountRequestDto confirmAccountRequestDto);

	UserResponse setPassword(String userId, UserRequest request);

	UserResponse resetPasswordByPhone(String userId, UserRequest request);

	public UserResponse resetPassword(UserRequest request);

	UserDetailsResponseDto addAgentByAdmin(UserOtherDetailsRequestDto request);

	UserDetailsResponseDto updateAgent(UserOtherDetailsRequestDto request);

	/**
	 * getUserDetails is to get the user details
	 * 
	 * @param id
	 * @return
	 */
	UserDetailsResponseDto getUserDetails(String id);

	public UserResponse updatePassword(UpdatePasswordRequest request);

	UserDataExistenceResponse findUserExistenceByEmailId(String emailId);

	UserDataExistenceResponse findUserExistenceByPhoneNumber(String phoneNumber);

	UserProjectMappingResponse mapUserProject(UserProjectMappingRequest userProjectMappingRequest);

	CountResponse getItemsCount();

	OtpRegisterResponseDTO sendOtptToForgotPassword(String email);

	OtpRegisterResponseDTO otpVerification(ConfirmEmailAccountRequestDto request);

	List<UserProjectOrgIdModel> getProjectIdsByUserIdpId();

	PaginatedResponse<InvitedUsersResponse> getInvitedUsers(String organisationId, String projectId, String RoleId,
			Integer pageNumber, Integer pageSize, InviteStatus inviteStatus, String name);

	InviteResendResponse resendUserInvitation(UserOtherDetailsRequestDto request);

	UserResponse otpVerifyAndSetPassword(String userId, ConfirmAccountPasswordRequest confirmAccountRequestDto);


	/**
	 * @implNote Intention of this method to get social-admin by the organisation
	 *           and for the mg-beneficiary service
	 * @author Arya C Achari
	 * @since 12-Jan-2022
	 * 
	 * @param organisationId
	 * @return
	 */
	List<SocialAdminDetails> getSocialAdminDetails(String organisationId);

	/**
	 * @see branch: user-by-id-minumum-details
	 * @author Arya C Achari
	 * @since 12-Jan-2021
	 * 
	 * @return
	 */
	UserMinDetails getUserMinimumDetails(String id);

	List<AgentDetailsDTO> getAgentDetails(String organisationId, String projectId, String date);

	boolean updateUserStatus(String userIdpId, boolean status);

	boolean enableDisableUser(String userIdpId, String status);

	/**
	 * updateAgentByAdmin is to update the agent details by admin
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	UserDetailsResponseDto updateAgentByAdmin(UserOtherDetailsRequestDto request, String userId);

	UserResponse getVolunteer(String userId, String organisationId);

	/**
	 * Setting the status details_updated & invite_status are update for user
	 * 
	 * @author Arya C Achari
	 * @since 15-Nov-2021
	 * 
	 * @param id
	 * @return
	 */
	UserStatusResponse updateUserStatus(String id);
	
	/**
	 * @author Arya C Achari
	 * @since 15-Nov-2021
	 * 
	 * @param id
	 * @return
	 */
	public User findUserDetails(String id);

	/**
	 * addNewUserWithRole is to add the new generic user 
	 * @param request
	 * @return
	 */
	UserDetailsResponseDto addNewUserWithRole(NewUserRequest request);

	/**
	 * getUserDetailsByEmailId is to fetch the user details by ID and project ID
	 */
	UserDetailsResponseDto getUserDetailsByEmailId(NewUserRequest newUserRequest);

	/**
	 * getUserIdByIdpId is to fetch the user id by using userIdpId
	 * @param idpId
	 * @return
	 */
	String getUserIdByIdpId(String idpId);

	/**
	 * Update the invite status of user on initial login
	 * @author Arya C Achari
	 * @since 08-Feb-2022
	 * 
	 * @param userIdpId
	 * @return
	 */
	String updateUserInviteStatus();
	
	void updateRole(String userId, String newRoleId, String oldRoleId);

}
