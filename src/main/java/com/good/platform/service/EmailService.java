package com.good.platform.service;

import javax.validation.Valid;

import com.good.platform.request.dto.BeneficiaryOnboardingEmailRequestDto;
import com.good.platform.request.dto.ConfirmEmailAccountRequestDto;
import com.good.platform.request.dto.EmailContentRequestDto;
import com.good.platform.request.dto.EmailRequestDto;
import com.good.platform.response.dto.EmailResponseDto;
import com.good.platform.response.dto.OtpRegisterResponseDTO;
import com.good.platform.response.dto.OtpResponseDTO;

public interface EmailService {

	void sendEmail(EmailRequestDto requestDto);

	EmailResponseDto saveTokenData(EmailRequestDto requestDto);

	//OtpRegisterResponseDTO generateEmailOtpToRegister(String email);
	
	OtpRegisterResponseDTO generateEmailOtpToRegister(String email,Long expTime,String otp) ;

	boolean activateUserAccountByEmail(@Valid ConfirmEmailAccountRequestDto confirmAccountRequestDto);

	OtpResponseDTO verifyEmailOtp(String tokenId, String email);

	void inviteEmailSender(EmailContentRequestDto emailContentRequestDto);
	//void inviteFieldAgent(EmailContentRequestDto emailContentRequestDto);

	void sendUserInvitation(EmailContentRequestDto emailContentRequestDto);

	void inviteFieldAgent(EmailContentRequestDto emailContentRequestDto);

	EmailResponseDto deleteTokenById(ConfirmEmailAccountRequestDto confirmEmailAccountRequestDto);

	/**
	 * sendExistingUserNotificationPeerReview is to send the notification to peer reviewer for
	 * reminding the pending approvals
	 * @param emailContentRequestDto
	 */
	void sendUserNotificationPeerReview(EmailContentRequestDto emailContentRequestDto);

	/**
	 * Email sent to beneficiary with login credetials
	 * @author Arya C Achari
	 * @since 25-Feb-2022
	 * 
	 * @param requestDto
	 */
	void beneficiaryOnboardingEmail(BeneficiaryOnboardingEmailRequestDto requestDto);

}
