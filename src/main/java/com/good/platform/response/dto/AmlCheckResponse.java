package com.good.platform.response.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AmlCheckResponse {

	private String memberId;
	
	private String organisationId;
	
	private String amlChecked;
	
}
