package com.good.platform.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.entity.User;
import com.good.platform.repository.UserRepository;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.OtpRegisterResponseDTO;
import com.good.platform.response.dto.PhoneOTPResponseDTO;
import com.good.platform.service.EmailService;
import com.good.platform.service.OTPService;
import com.good.platform.service.SmsGatewayService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.EmailUtils;
import com.good.platform.utility.OtpUtility;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

	private final EmailService emailService;
	private final OTPService otpService;
	private final UserRepository userRepository;
	private final SmsGatewayService smsGatewayService;

	@GetMapping("/send/otp")
	public GoodPlatformResponseVO<OtpRegisterResponseDTO> getEmailRegisterOtp(
			@RequestParam(name = "email") String email) {
		User existingEntity = userRepository.findByEmailIdIgnoreCase(email);
		String otp = OtpUtility.generateOTP();
		Long expTime = OtpUtility.getExpirationTime(EmailUtils.EXPIRATION_MINUTES);
		OtpRegisterResponseDTO result = emailService.generateEmailOtpToRegister(email, expTime, otp);
		PhoneOTPResponseDTO otpDetails = otpService.generateOTP(existingEntity.getPhone(), expTime, otp);
		smsGatewayService.sendUserRegistrationOTP(otpDetails, existingEntity.getFirstName());
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OtpRegisterResponseDTO>(), result,
				Constants.VERIFICATION_MAIL_SEND_SUCCESS, Constants.VERIFICATION_MAIL_SEND_FAILURE);
	}

}
