package com.good.platform.response;

import lombok.Data;

@Data
public class UserMinDetails {
	
	private String id;
	private String userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String contactNumber;
	
	public UserMinDetails(String id, String userId, String firstName, String middleName, String lastName,
			String emailId, String contactNumber) {
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
	}
}