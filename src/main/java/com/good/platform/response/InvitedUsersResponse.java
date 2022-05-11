package com.good.platform.response;

import java.util.List;

import com.good.platform.enums.InviteStatus;
import com.good.platform.model.ProjectIdNameModel;
import com.good.platform.projection.InvitedUsersProjection;

import lombok.Data;

@Data
public class InvitedUsersResponse {

	public InvitedUsersResponse(String organisationId, String organisationName, String userId, String firstName,
			String lastName, String emailId, String phone, String userIdpId, InviteStatus inviteStatus,
			String middleName, boolean active) {
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phone = phone;
		this.userIdpId = userIdpId;
		this.inviteStatus = inviteStatus;
		this.middleName = middleName;
		this.active = active;
	}
	
	public InvitedUsersResponse(String organisationId, String organisationName, String userId, String firstName,
			String lastName, String emailId, String phone, String userIdpId, InviteStatus inviteStatus,
			String middleName, boolean active, String role) {
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phone = phone;
		this.userIdpId = userIdpId;
		this.inviteStatus = inviteStatus;
		this.middleName = middleName;
		this.active = active;
		this.role = role;
	}

	public InvitedUsersResponse(String organisationId, String organisationName, String userId, String firstName,
			String lastName, String emailId, String phone, String userIdpId, InviteStatus inviteStatus,
			String middleName) {
		super();
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phone = phone;
		this.userIdpId = userIdpId;
		this.inviteStatus = inviteStatus;
		this.middleName = middleName;
	}

	public InvitedUsersResponse(InvitedUsersProjection projection) {
		this.organisationId = projection.getOrganisationId();
		this.organisationName = projection.getOrganisationName();
		this.userId = projection.getUserId();
		this.firstName = projection.getFirstName();
		this.lastName = projection.getLastName();
		this.emailId = projection.getEmailId();
		this.phone = projection.getPhone();
		this.userIdpId = projection.getUserIdpId();
		this.inviteStatus = (projection.getInviteStatus() != null) ?  InviteStatus.valueOf(projection.getInviteStatus()):null;
		this.middleName = projection.getMiddleName();
		this.active = projection.isActive();
		this.role = projection.getRole();
	}
	
	private String organisationId;
	private String organisationName;
	private String userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phone;
	private String userIdpId;
	private List<ProjectIdNameModel> projects;
	private InviteStatus inviteStatus;
	private String middleName;
	private String role;
	private boolean active;
}
