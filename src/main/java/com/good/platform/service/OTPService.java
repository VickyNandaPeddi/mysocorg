package com.good.platform.service;

import javax.validation.Valid;

import com.good.platform.request.dto.ConfirmEmailAccountRequestDto;
import com.good.platform.request.dto.EmailContentRequestDto;
import com.good.platform.request.dto.EmailRequestDto;
import com.good.platform.response.dto.EmailResponseDto;
import com.good.platform.response.dto.OtpRegisterResponseDTO;
import com.good.platform.response.dto.OtpResponseDTO;
import com.good.platform.response.dto.PhoneOTPResponseDTO;

public interface OTPService {
	PhoneOTPResponseDTO generateOTP(String phone,Long expirationTime,String otp);

	boolean verifyPhoneOTP(ConfirmEmailAccountRequestDto confirmAccountRequestDto);


}
