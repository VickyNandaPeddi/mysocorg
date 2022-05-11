package com.good.platform.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OtpUtility {
	public static final int EXPIRATION_MINUTES = 15;

	public static String generateOTP() {
		int randomPin = (int) (Math.random() * 9000) + 1000;
		String otp = String.valueOf(randomPin);
		return otp;
	}

	public static Boolean isTokenExpired(Long expirationEndTime) {
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(expirationEndTime), ZoneId.systemDefault());
		return LocalDateTime.now().isAfter(date);
	}

	public static Long getExpirationTime(int expirationTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, EXPIRATION_MINUTES);
		Date addMinutes = calendar.getTime();
		return addMinutes.getTime();
	}

}
