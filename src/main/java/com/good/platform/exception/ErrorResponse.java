package com.good.platform.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private int errorCode;

	private String message;

	private Boolean success;

	private Object errorData;

}
