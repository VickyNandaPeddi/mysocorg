package com.good.platform.request.dto;

import lombok.Data;

/**
 * This class created for not to violate the existing RegisterDTO by adding the
 * new field createdBy. This class used for onboard the imported beneficiary
 * from excel.
 * 
 * @author Arya C Achari
 * @since 25-Feb-2022
 *
 */
@Data
public class BeneficiaryRegisterDTO {

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
	private String createdBy;
}
