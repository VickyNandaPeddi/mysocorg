package com.good.platform.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpRegisterResponseDTO {
	
	private String status;
	private String email;
	private String userId;
	private String phone;
}
