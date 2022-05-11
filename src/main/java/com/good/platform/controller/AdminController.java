package com.good.platform.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.good.platform.request.dto.UserOtherDetailsRequestDto;
import com.good.platform.response.InvitedUsersResponse;
import com.good.platform.response.PaginatedResponse;
import com.good.platform.response.UserCommonResponse;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.InviteResendResponse;
import com.good.platform.response.dto.UserDetailsResponseDto;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.service.AdminService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/admin")
@Slf4j
public class AdminController {
	
	@Autowired
	AdminService adminService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/invited-users")
	public GoodPlatformResponseVO<Page<InvitedUsersResponse>> getAgents(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "organisationId", required = false) String organisationId,
			@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "roleId", required = false) String roleId,
			@RequestParam(value = "inviteStatus", required = false) InviteStatus inviteStatus,
			@RequestParam(value = "name", required = false) String name) throws ParseException {
		log.debug("Fetch All invited users and  project details");
		PaginatedResponse<InvitedUsersResponse> dataList = adminService.getInvitedUsers(organisationId, projectId,
				roleId, pageNumber, pageSize, inviteStatus, name);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<UserProjectOrgIdModel>>(), dataList,
				Constants.INVITED_USERS_FETCH_SUCCESS, Constants.INVITED_USERS_FETCH_FAIL);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/invite")
	public GoodPlatformResponseVO<UserResponse> inviteUsers(@RequestBody UserOtherDetailsRequestDto request) {
		UserResponse response = adminService.inviteUsers(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.USER_ADD_SUCCESS, Constants.USER_ADD_FAIL);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/disable-user")
	public boolean disableUser(@RequestParam(name = "userIdpId", required = true) String userIdpId) {
		boolean response = adminService.enableDisableUser(userIdpId, "disable");
		return response;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/enable-user")
	public boolean enableUser(@RequestParam(name = "userIdpId", required = true) String userIdpId) {
		boolean response = adminService.enableDisableUser(userIdpId, "enable");
		return response;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/volunteer")
	public GoodPlatformResponseVO<UserResponse> getVolunteer(
			@RequestParam(name = "userId", required = true) String userId) {
		UserResponse response = adminService.getVolunteer(userId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserCommonResponse>(), response,
				Constants.USER_GET_SUCCESS, Constants.USER_GET_FAIL);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{userId}/update-volunteer")
	public GoodPlatformResponseVO<UserDetailsResponseDto> updateUserDetailsByAdmin(
			@RequestBody UserOtherDetailsRequestDto request, @PathVariable("userId") String userId) {
		UserDetailsResponseDto response = adminService.updateAgentByAdmin(request, userId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserDetailsResponseDto>(), response,
				Constants.USER_DETAILS_UPDATE_SUCCESS, Constants.USER_DETAILS_UPDATE_FAIL);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/resend/invite-user")
	public GoodPlatformResponseVO<InviteResendResponse> resendUserInvitation(
			@RequestBody UserOtherDetailsRequestDto request) {
		InviteResendResponse response = adminService.resendUserInvitation(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.INVITE_RESEND_SUCCESS, Constants.INVITE_RESEND_FAIL);
	}

}
