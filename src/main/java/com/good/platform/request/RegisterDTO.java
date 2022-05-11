package com.good.platform.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String phone;
	private String userName;
    private String password;
	private String profileImageUrl;
	private String roleId;
	private String profileImageFilename;
	private String organisationId;
	private String projectId;
	private String beneficiaryId;
}
