package com.good.platform.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataExistenceResponse {
	
	private int responseCode;
	private String message;
	private boolean userExists;

}
