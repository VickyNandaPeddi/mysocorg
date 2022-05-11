package com.good.platform.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
	
	private String id;
	private String validatorTokenId;
	private String email;
	private String role;
	private Boolean isExpired;
	private String timeSpan;
	private Long expirationStartTime;
	private Long expirationTime;
	private String firstName;
	
}
