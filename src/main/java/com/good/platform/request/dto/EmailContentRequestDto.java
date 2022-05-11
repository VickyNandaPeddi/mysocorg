package com.good.platform.request.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class EmailContentRequestDto {
	
	public EmailContentRequestDto() {
	}
	private String userName;
	private String emailId;
	private String ccEmailId;
	private String bccEmailId;
	private String subject;
	private String message;
	private String password;
	private String sign;
	private String FromMailId;
	private String downloadLink;
	private String projectName;
	private String orgName;
	private String role;


}
