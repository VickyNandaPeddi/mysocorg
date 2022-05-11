package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.EmailEntity;
import com.good.platform.entity.PhoneOTP;

@Repository
public interface PhoneOTPRepository extends JpaRepository<PhoneOTP, String>{
	
	PhoneOTP findByOtp(String otp);
	
	PhoneOTP findByPhone(String Phone);
	
	
	PhoneOTP findByOtpAndPhone(String otp,String phone);
	
}
