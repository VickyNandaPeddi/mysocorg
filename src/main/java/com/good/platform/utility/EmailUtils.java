package com.good.platform.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailUtils {

	public static final int EXPIRATION_MINUTES = 15;

	public Boolean isTokenExpired(Long expirationEndTime) {
		LocalDateTime date =
			    LocalDateTime.ofInstant(Instant.ofEpochMilli(expirationEndTime), ZoneId.systemDefault());
		return LocalDateTime.now().isAfter(date);
	}
	
	public Long getCurrentTime() {
		return new Date().getTime();
	}
	
	public Long getExpirationTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, EXPIRATION_MINUTES);
        Date addMinutes = calendar.getTime();
        return addMinutes.getTime();
	}
}
