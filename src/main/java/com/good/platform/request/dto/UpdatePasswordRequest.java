package com.good.platform.request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {

	private String email;
	private String password;
}
