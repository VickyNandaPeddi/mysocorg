package com.good.platform.request.dto;

import com.good.platform.model.AddressDetailsModel;

import lombok.Data;

@Data
public class UserOtherDetailsRequestDto {
	
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String contactNumber;
	private String organisationId;
	private String password;
	private AddressDetailsModel addressModel;
	private String[] projectId;
	private String educationQualification;
	private String educationDetails;
	private String userRoleId;
	private Boolean detailsUpdated;
}
