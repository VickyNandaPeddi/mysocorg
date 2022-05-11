package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.model.AddressDetailsModel;
import com.good.platform.model.UserProjectMapModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsResponseDto {
	
	public UserDetailsResponseDto(String id, String userId, String firstName, String middleName, String lastName, String emailId,
			String contactNumber, String organisationId, String education, String educationDetails) {
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.organisationId = organisationId;
		this.education = education;
		this.educationDetails = educationDetails;
	}

	private String id;
	private String userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String contactNumber;
	private String organisationId;
	private AddressDetailsModel address;
	private List<UserProjectMapModel> userProjectMapModel;
	private String education;
	private String educationDetails;
	private Boolean detailsUpdated;
	
}
