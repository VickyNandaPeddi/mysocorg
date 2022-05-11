package com.good.platform.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	private String message;

	public EmailException(String message) {
		super(message);
		this.message = message;
	}

	public EmailException(Integer error, String message) {
		super(message);
		this.errorCode = error;
		this.message = message;
	}

	public EmailException(Integer error, Exception ex) {
		super(ex);
		this.errorCode = error;
		this.message = ex.getMessage();
	}

	public EmailException(Exception ex) {
		super(ex);
		this.message = ex.getMessage();
	}

	public EmailException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode.getCode();
		this.message = message;
	}

}
