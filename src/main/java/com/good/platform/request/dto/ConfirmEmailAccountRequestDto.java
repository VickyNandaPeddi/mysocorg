package com.good.platform.request.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmEmailAccountRequestDto {

	@NotNull
	private String email;
	@NotNull
	private String emailOtp;
	
	
	private String phone;
	
	private String phoneOtp;

}
