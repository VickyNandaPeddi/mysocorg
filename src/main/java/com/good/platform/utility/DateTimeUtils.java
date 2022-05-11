package com.good.platform.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * DateTimeUtils is to handle all the Date and time utilities
 * 
 * @author Mohamedsuhail S
 *
 */
@Service
public class DateTimeUtils {

	/**
	 * getCurrentTime is to get the current time in Long as return type
	 * 
	 * @return current time in Long
	 */

	public static Long getCurrentTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
		return zdt.toInstant().toEpochMilli();
	}

	/**
	 * convertStringToLocalDate is to convert the String to LocalDateTime
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime convertStringToLocalDate(String strDate) {
		DateTimeFormatter df = null;
		if (strDate.contains("T")) {
			df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		} else {
			df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		}

		return LocalDateTime.parse(strDate, df);

	}
	
	public static LocalDate convertUnixToLocalDate(Long utc) {
		LocalDate localDate = Instant.ofEpochMilli(utc).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
		return localDate;
	}

}
