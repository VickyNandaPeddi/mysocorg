package com.good.platform.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailModel {
	
	private String fromEmailId;
	private String toEmailId;
	private String ccEmailId;
	private String bccEmailId;
	private String subject;
	private String message;
	private Map<String, Object> props;

}
