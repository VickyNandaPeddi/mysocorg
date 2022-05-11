package com.good.platform.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneOTPResponseDTO {
	
	private String id;
	private String otp;
	private String email;
	private String phone;
	private Boolean isExpired;
	private String timeSpan;
	private Long expirationStartTime;
	private Long expirationTime;

	
}
