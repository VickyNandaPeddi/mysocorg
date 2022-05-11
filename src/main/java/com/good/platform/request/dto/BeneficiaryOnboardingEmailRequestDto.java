package com.good.platform.request.dto;

import lombok.Data;

/**
 * Beneficiary onboarding email
 * @author Arya C Achari
 * @since 25-Feb-2022
 *
 */
@Data
public class BeneficiaryOnboardingEmailRequestDto {
	
	private String beneficiaryName;
	private String userName;
	private String password;
	private String emailId;
	private String ccEmailId;
	private String bccEmailId;
	private String subject;
	private String message;
	private String FromMailId;
}
