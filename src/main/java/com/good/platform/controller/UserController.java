package com.good.platform.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.enums.InviteStatus;
import com.good.platform.model.UserProjectOrgIdModel;
import com.good.platform.request.NewUserRequest;
import com.good.platform.request.dto.RoleUpdateRequest;
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
import com.good.platform.response.dto.SocialAdminDetails;
import com.good.platform.response.dto.UserDataExistenceResponse;
import com.good.platform.response.dto.UserDetailsResponseDto;
import com.good.platform.response.dto.UserProjectMappingResponse;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.response.dto.UserStatusResponse;
import com.good.platform.service.EmailService;
import com.good.platform.service.SmsGatewayService;
import com.good.platform.service.UserService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final EmailService emailService;
	private final SmsGatewayService smsService;
	

	@PostMapping()
	public GoodPlatformResponseVO<UserResponse> addUsers(@RequestBody UserRequest request) {
		UserResponse response = userService.addUser(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.USER_ADD_SUCCESS, Constants.USER_ADD_FAIL);
	}

	@PostMapping("/invite-user")
	public GoodPlatformResponseVO<UserDetailsResponseDto> inviteUsers(@RequestBody UserOtherDetailsRequestDto request) {
		UserDetailsResponseDto response = userService.addAgentByAdmin(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.USER_ADD_SUCCESS, Constants.USER_ADD_FAIL);
	}

	@PutMapping("/update-user")
	public GoodPlatformResponseVO<UserDetailsResponseDto> addUserDetails(
			@RequestBody UserOtherDetailsRequestDto request) {
		UserDetailsResponseDto response = userService.updateAgent(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), response,
				Constants.USER_DETAILS_UPDATE_SUCCESS, Constants.USER_DETAILS_UPDATE_FAIL);
	}

	@PutMapping("/{userId}/update-volunteer")
	public GoodPlatformResponseVO<UserDetailsResponseDto> updateUserDetailsByAdmin(
			@RequestBody UserOtherDetailsRequestDto request, @PathVariable("userId") String userId) {
		UserDetailsResponseDto response = userService.updateAgentByAdmin(request, userId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), response,
				Constants.USER_DETAILS_UPDATE_SUCCESS, Constants.USER_DETAILS_UPDATE_FAIL);
	}

	@GetMapping("/get-user-details/{id}")
	public GoodPlatformResponseVO<UserDetailsResponseDto> getUserDetails(@PathVariable("id") String id) {
		UserDetailsResponseDto response = userService.getUserDetails(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}
	
	@PostMapping("/user-details")
	public GoodPlatformResponseVO<UserDetailsResponseDto> getUserDetailsByEmail(@RequestBody NewUserRequest newUserRequest) {
		UserDetailsResponseDto response = userService.getUserDetailsByEmailId(newUserRequest);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}

	@PutMapping("/reset-password")
	public GoodPlatformResponseVO<UserResponse> resetPassword(@RequestBody UserRequest request) {
		log.debug("request to reset password  {}", request);
		UserResponse response = userService.resetPassword(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.PASSWORD_RESET_SUCCESS, Constants.PASSWORD_RESET_FAIL);

	}

	@GetMapping()
	public GoodPlatformResponseVO<UserCommonResponse> getUser() {
		UserCommonResponse response = userService.getUser();
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserCommonResponse>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}

	@GetMapping("/find-by-email/{emailId}")
	public UserDataExistenceResponse getUserExistenceByEmailId(@PathVariable("emailId") String emailId) {
		UserDataExistenceResponse response = userService.findUserExistenceByEmailId(emailId);
		return response;
	}

	@GetMapping("/find-by-phone/{phoneNumber}")
	public UserDataExistenceResponse getUserExistenceByPhone(@PathVariable("phoneNumber") String phoneNumber) {
		UserDataExistenceResponse response = userService.findUserExistenceByPhoneNumber(phoneNumber);
		return response;
	}

	@PostMapping("/user-project-mapping")
	public GoodPlatformResponseVO<UserProjectMappingResponse> addUserProjectMapping(
			@RequestBody UserProjectMappingRequest userProjectMappingRequest) {
		UserProjectMappingResponse response = userService.mapUserProject(userProjectMappingRequest);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserProjectMappingResponse>(), response,
				Constants.USER_PROJECT_MAPPING_SUCCESS, Constants.USER_PROJECT_MAPPING_FAIL);
	}

	@GetMapping("/admin-dashboard-count")
	public GoodPlatformResponseVO<CountResponse> getItemsCount() {
		CountResponse response = userService.getItemsCount();
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<CountResponse>(), response,
				Constants.FETCHING_COUNT_DETAILS_SUCCESS, Constants.FETCHING_COUNT_DETAILS_FAIL);
	}

	@GetMapping("/assigned-projects")
	public GoodPlatformResponseVO<List<UserProjectOrgIdModel>> getAgentProjectDetails() {
		log.debug("Fetch agent project details");
		List<UserProjectOrgIdModel> dataList = userService.getProjectIdsByUserIdpId();
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<UserProjectOrgIdModel>>(), dataList,
				Constants.PROJECT_ORG_MAPPINGS_FETCH_SUCCESS, Constants.PROJECT_ORG_MAPPINGS_FETCH_FAIL);
	}

	@GetMapping("/invited")
	public GoodPlatformResponseVO<Page<InvitedUsersResponse>> getAgents(
			@RequestParam(value = "organisationId", required = true) String organisationId,
			@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "roleId", required = false) String roleId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "inviteStatus", required = false) InviteStatus inviteStatus,
			@RequestParam(value = "name", required = false) String name) throws ParseException {
		log.debug("Fetch All invited users and  project details");
		PaginatedResponse<InvitedUsersResponse> dataList = userService.getInvitedUsers(organisationId, projectId,
				roleId, pageNumber, pageSize, inviteStatus, name);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<UserProjectOrgIdModel>>(), dataList,
				Constants.INVITED_USERS_FETCH_SUCCESS, Constants.INVITED_USERS_FETCH_FAIL);
	}

	@PostMapping("/resend/invite-user")
	public GoodPlatformResponseVO<InviteResendResponse> resendUserInvitation(
			@RequestBody UserOtherDetailsRequestDto request) {
		InviteResendResponse response = userService.resendUserInvitation(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.INVITE_RESEND_SUCCESS, Constants.INVITE_RESEND_FAIL);
	}

	/**
	 * @implNote This is a feign call to the mg-beneficiary service
	 * @author Arya C Achari
	 * @since 12-Jan-2022
	 * 
	 * @param organisationId
	 * @return
	 */
	@GetMapping("/get-social-admin-details/{organisationId}")
	public GoodPlatformResponseVO<List<SocialAdminDetails>> getSocialAdminDetails(
			@PathVariable("organisationId") String organisationId) {
		List<SocialAdminDetails> response = userService.getSocialAdminDetails(organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<SocialAdminDetails>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}
	
	/**
	 * @see branch: user-by-id-minumum-details
	 * @implNote This is a feign call to the mg-beneficiary service
	 * @author Arya C Achari
	 * @since 12-Jan-2022
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get-user-min-details/{id}")
	public GoodPlatformResponseVO<UserDetailsResponseDto> getUserMinimumDetails(@PathVariable("id") String id) {
		UserMinDetails response = userService.getUserMinimumDetails(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}

	@GetMapping("/agent/beneficiaries")
	public GoodPlatformResponseVO<List<AgentDetailsDTO>> getAgentDetails(
			@RequestParam(value = "organisationId", required = true) String organisationId,
			@RequestParam(value = "projectId", required = true) String projectId,
			@RequestParam(value = "date", required = true) String date) throws ParseException {

		List<AgentDetailsDTO> dataList = userService.getAgentDetails(organisationId, projectId,date);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<UserProjectOrgIdModel>>(), dataList,
				Constants.AGENT_DETAILS_FETCH_SUCCESS, Constants.AGENT_DETAILS_FETCH_FAIL);
	}
	
	@PutMapping("/{userIdpId}/status/{status}")
	public GoodPlatformResponseVO<Boolean> updateActiveStatus(@PathVariable("userIdpId") String userIdpId,
			@PathVariable("status") Boolean status){
		boolean response = userService.updateUserStatus(userIdpId, status);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), response,
				Constants.UPDATE_USER_STATUS_SUCCESS, Constants.UPDATE_USER_STATUS_FAILED);
	}
	
	@PutMapping("/disable-user")
	public boolean disableUser(@RequestParam(name = "userIdpId", required = true) String userIdpId) {
		boolean response = userService.enableDisableUser(userIdpId, "disable");
		return response;
	}
	
	@PutMapping("/enable-user")
	public boolean enableUser(@RequestParam(name = "userIdpId", required = true) String userIdpId) {
		boolean response = userService.enableDisableUser(userIdpId, "enable");
		return response;
	}
	
	@GetMapping("/volunteer")
	public GoodPlatformResponseVO<UserResponse> getVolunteer(@RequestParam(name = "userId", required = true) String userId,
			@RequestParam(name = "organisationId", required = true) String organisationId) {
		UserResponse response = userService.getVolunteer(userId,organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserCommonResponse>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}
	
	@PutMapping("/{userId}/status")
	public GoodPlatformResponseVO<UserStatusResponse> updateUserStatus(@PathVariable("userId") String userId) {
		UserStatusResponse response = userService.updateUserStatus(userId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserCommonResponse>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}
	
	/**
	 * addNewUserWithRole is to add new generic user 
	 * @param newUserRequest
	 * @return
	 */
	@PostMapping("/add-user")
	public GoodPlatformResponseVO<UserDetailsResponseDto> addNewUserWithRole(@RequestBody NewUserRequest newUserRequest) {
		UserDetailsResponseDto userResponse = userService.addNewUserWithRole(newUserRequest);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), userResponse,
				Constants.USER_ADD_SUCCESS, Constants.USER_ADD_FAIL);
	}
	
	/**
	 * getUserIdUsingIdpId is to fetch the user ID by using user Idp Id
	 * @param userIdpId
	 * @return
	 */
	@GetMapping("/get-user-id/{userIdpId}")
	public GoodPlatformResponseVO<String> getUserIdUsingIdpId(@PathVariable("userIdpId") String userIdpId){
		String response = userService.getUserIdByIdpId(userIdpId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), response,
				Constants.USER_ID_FETCH_SUCCESS, Constants.USER_ID_FETCH_FAIL);
	}
	
	
	/**
	 * Update the invite status on initial login of user
	 * @author Arya C Achari
	 * @since 08-Feb-2022
	 * 
	 * @return string
	 */
	@PutMapping("/invite-status")
	public GoodPlatformResponseVO<String> updateInviteStatus() {
		String response = userService.updateUserInviteStatus();
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<String>(), response,
				Constants.USER_INVITE_STATUS_UPDATE_SUCCESS, Constants.USER_INVITE_STATUS_UPDATE_FAIL);
	}
	
	@PostMapping("/update-role")
	public void updateUserRole(@RequestBody RoleUpdateRequest roleUpdateRequest) {
		userService.updateRole(roleUpdateRequest.getUserId(),roleUpdateRequest.getNewRoleId(),roleUpdateRequest.getOldRoleId());
	}

}
