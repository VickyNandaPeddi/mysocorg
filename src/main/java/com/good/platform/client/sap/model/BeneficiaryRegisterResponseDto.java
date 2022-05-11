package com.good.platform.client.sap.model;

import lombok.Data;

/**
 * @author Arya C Achari
 * @since 25-Feb-2022
 *
 */
@Data
public class BeneficiaryRegisterResponseDto {

	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String mobile;
	private String projectId;
	private String createdBy;
	
}
