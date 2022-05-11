package com.good.platform.service;

import com.good.platform.entity.User;
import com.good.platform.enums.InviteStatus;
import com.good.platform.request.dto.UserOtherDetailsRequestDto;
import com.good.platform.response.InvitedUsersResponse;
import com.good.platform.response.PaginatedResponse;
import com.good.platform.response.dto.InviteResendResponse;
import com.good.platform.response.dto.UserDetailsResponseDto;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.response.dto.UserStatusResponse;

public interface AdminService {

	PaginatedResponse<InvitedUsersResponse> getInvitedUsers(String organisationId, String projectId, String roleId,
			Integer pageNumber, Integer pageSize, InviteStatus inviteStatus, String name);
	
	UserResponse inviteUsers(UserOtherDetailsRequestDto request);

	UserResponse getVolunteer(String userId);

	boolean enableDisableUser(String userIdpId, String string);

	UserDetailsResponseDto updateAgentByAdmin(UserOtherDetailsRequestDto request, String userId);

	UserStatusResponse updateUserStatus(String id);

	User findUserDetails(String id);

	InviteResendResponse resendUserInvitation(UserOtherDetailsRequestDto request);

}
