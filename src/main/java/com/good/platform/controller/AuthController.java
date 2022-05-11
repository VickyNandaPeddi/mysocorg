package com.good.platform.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.request.dto.ConfirmAccountPasswordRequest;
import com.good.platform.request.dto.ConfirmEmailAccountRequestDto;
import com.good.platform.request.dto.ForgotPasswordRequest;
import com.good.platform.request.dto.UpdatePasswordRequest;
import com.good.platform.request.dto.UserRequest;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.OtpRegisterResponseDTO;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.service.EmailService;
import com.good.platform.service.UserService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final UserService userService;

	private final EmailService emailService;

	/**
	 * used for REGISTRATION-FLOW
	 * confirmAccount is to confirm the user account
	 * 
	 * @param confirmAccountRequestDto
	 */

	@PutMapping("/{userId}/email-otp-verification")
	public GoodPlatformResponseVO<UserResponse> confirmAccount(@PathVariable String userId,
			@Valid @RequestBody ConfirmEmailAccountRequestDto confirmAccountRequestDto) {
		UserResponse response = userService.confirmAccount(userId, confirmAccountRequestDto);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.EMAIL_OTP_VERIFICATION_SUCCESS, Constants.EMAIL_OTP_VERIFICATION_FAIL);
	}

	@PutMapping("/{userId}/password")
	public GoodPlatformResponseVO<UserResponse> setPassword(@PathVariable String userId,
			@RequestBody UserRequest request) {
		UserResponse response = userService.setPassword(userId, request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.USER_UPDATE_SUCCESS, Constants.USER_UPDATE_FAIL);
	}
	
	@PutMapping("/{userId}/otp-verification/password")
	public GoodPlatformResponseVO<UserResponse> confirmAndSetPassword(@PathVariable String userId,
			@RequestBody ConfirmAccountPasswordRequest confirmAccountPasswordRequest) {
		UserResponse userResponse = userService.otpVerifyAndSetPassword(userId, confirmAccountPasswordRequest);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), userResponse,
				Constants.OTP_VERIFICATION_DONE_PASSWORD_SUCCESS,
				Constants.OTP_VERIFICATION_DONE_PASSWORD_FAIL);
	}

	@PostMapping("/forgot-password")
	public GoodPlatformResponseVO<OtpRegisterResponseDTO> forgotPasswordByEmail(
			@RequestBody ForgotPasswordRequest request) {
		OtpRegisterResponseDTO result =userService.sendOtptToForgotPassword(request.getEmail());
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OtpRegisterResponseDTO>(), result,
				Constants.VERIFICATION_MAIL_SEND_SUCCESS, Constants.VERIFICATION_MAIL_SEND_FAILURE);

	}
	//used for FORGOT-PASSWORD FLOW
	@PostMapping("/email-otp-verification")
	public GoodPlatformResponseVO<OtpRegisterResponseDTO> otpVerification (@RequestBody ConfirmEmailAccountRequestDto request) {
		OtpRegisterResponseDTO response =	userService.otpVerification(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.EMAIL_OTP_VERIFICATION_SUCCESS, Constants.EMAIL_OTP_VERIFICATION_FAIL);

	}
	
	@PostMapping("/password/update")
	public GoodPlatformResponseVO<UserResponse> updatePassword(@RequestBody UpdatePasswordRequest request) {
		UserResponse response = userService.updatePassword(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.USER_PASSWORD_UPDATE_SUCCESS, Constants.USER_PASSWORD_UPDATE_FAIL);
	}
	

}
