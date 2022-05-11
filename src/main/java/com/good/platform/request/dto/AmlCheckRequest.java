package com.good.platform.request.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AmlCheckRequest {

	private String memberId;
	
	private String organisationId;
	
	private String amlChecked;
	
}
