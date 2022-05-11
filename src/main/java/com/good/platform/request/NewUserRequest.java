package com.good.platform.request;

import com.good.platform.enums.UserRolesEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String contactNumber;
	private String organisationId;
	private String projectId;
	private UserRolesEnum userRole;
	private String projectName;

}
