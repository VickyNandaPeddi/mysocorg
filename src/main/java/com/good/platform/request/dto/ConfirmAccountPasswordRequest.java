package com.good.platform.request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmAccountPasswordRequest {
	
	private String email;
	
	private String emailOtp;
	
    private String password;
    
	private String roleId;

}
