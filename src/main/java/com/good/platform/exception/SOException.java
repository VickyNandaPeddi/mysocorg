package com.good.platform.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SOException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	private String message;

	public SOException(String message) {
		super(message);
		this.message = message;
	}

	public SOException(Integer error, String message) {
		super(message);
		this.errorCode = error;
		this.message = message;
	}

	public SOException(Integer error, Exception ex) {
		super(ex);
		this.errorCode = error;
		this.message = ex.getMessage();
	}

	public SOException(Exception ex) {
		super(ex);
		this.message = ex.getMessage();
	}

	public SOException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode.getCode();
		this.message = message;
	}
	public SOException(ErrorCode errorCode, Exception ex) {
		super(ex);
		this.errorCode = errorCode.getCode();
		this.message = ex.getCause().getMessage();
		
	}

}
