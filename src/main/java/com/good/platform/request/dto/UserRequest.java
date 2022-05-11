package com.good.platform.request.dto;

import lombok.Data;

@Data
public class UserRequest {
	private String id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phone;
	private String userName;
    private String password;
	private String profileImageUrl;
	//private String userIdpId;
	private String roleId;
	private String profileImageFilename;

	

}
