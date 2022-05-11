package com.good.platform.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponseDto {
	
	private String id;
	private String validatorTokenId;
	private String email;
	private String role;
	private Boolean isExpired;
	private String timeSpan;
	private Long expirationStartTime;
	private Long expirationTime;
	
}
