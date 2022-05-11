package com.good.platform.service;


import com.good.platform.response.dto.PhoneOTPResponseDTO;

public interface SmsGatewayService {

	boolean sendArpanInviteSms(String phoneNumber, String beneficiaryName, String emailId, String password,String downloadLink);

	public boolean sendUmmeedInviteSms(String phoneNumber, String beneficiaryName, String emailId, String beneficiaryPassword,String downloadLink);

	boolean sendUserRegistrationOTP(PhoneOTPResponseDTO otpDetails, String firstName);

	boolean sendSapInviteSms(String programName, String phoneNumber, String beneficiaryName, String emailId,
			String beneficiaryPassword, String downloadLink);
	
}
