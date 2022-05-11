package com.good.platform.service.impl;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.good.platform.entity.PhoneOTP;
import com.good.platform.exception.EmailException;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.OTPMapper;
import com.good.platform.repository.PhoneOTPRepository;
import com.good.platform.request.dto.ConfirmEmailAccountRequestDto;
import com.good.platform.response.dto.PhoneOTPResponseDTO;
import com.good.platform.service.OTPService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.OtpUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {
	private final OTPMapper otpMapper;
	private final PhoneOTPRepository phoneOTPRepo;

//activateUserAccountByPhone
	
	@Override
	public boolean verifyPhoneOTP(ConfirmEmailAccountRequestDto confirmAccountRequestDto) {
		try {
			PhoneOTP phoneOtp = phoneOTPRepo.findByOtpAndPhone(confirmAccountRequestDto.getPhoneOtp(),
					confirmAccountRequestDto.getPhone());

			if (Objects.isNull(phoneOtp)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.PHONE_OTP_NOT_FOUND);
			}
			if (OtpUtility.isTokenExpired(phoneOtp.getExpirationTime())) {
				throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.PHONE_OTP_EXPIRED);
			}

			phoneOTPRepo.delete(phoneOtp);

			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if(Constants.PHONE_OTP_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.PHONE_OTP_NOT_FOUND);
			}else if(Constants.PHONE_OTP_EXPIRED.equals(exception.getMessage())) {	
				throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.PHONE_OTP_EXPIRED);
			}else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PHONE_OTP_VERIFICATION_FAIL);
			}
		}

	}

//
//	reserved for FORGOT-PASSWORD FLOW  
//	public OtpResponseDTO verifyPhoneOtp(String otp,String phone) {
//		
//		PhoneOTP phoneOtp = phoneOTPRepo.findByOtpAndPhone(otp,phone);
//		
//		if(Objects.isNull(phoneOtp)) { 
//			throw new SOException(ErrorCode.NOT_FOUND, Constants.PHONE_OTP_NOT_FOUND);
//		}
//		if (OtpUtility.isTokenExpired(phoneOtp.getExpirationTime())) {
//			throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.EMAIL_VERIFICATION_OTP_EXPIRED);
//		}
//		OtpResponseDTO otpResponse = new OtpResponseDTO("OTP Matched","Success");
//		return otpResponse;
//	}

	@Override
	public PhoneOTPResponseDTO generateOTP(String phone,Long expirationTime,String otp) {
	//	public PhoneOTPResponseDTO generateOTP(String phone) {
		PhoneOTP phoneOtp = phoneOTPRepo.findByPhone(phone);
		if (phoneOtp != null) {
			phoneOTPRepo.delete(phoneOtp);
		}

		phoneOtp = PhoneOTP.builder().phone(phone).otp(otp).isExpired(false)
				//.timeSpan(String.valueOf(OtpUtility.EXPIRATION_MINUTES)).expirationStartTime(new Date().getTime())
				.timeSpan(String.valueOf(OtpUtility.EXPIRATION_MINUTES)).expirationStartTime(new Date().getTime())
				.expirationTime(expirationTime).build();
		phoneOtp = phoneOTPRepo.save(phoneOtp);
		return otpMapper.ToPhoneOTPResponseDTO(phoneOtp);

	}

}
