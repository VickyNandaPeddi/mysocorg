package com.good.platform.request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationStatusRequestDto {
	
	private String status;
	
	private String comment;
	
	public OrganisationStatusRequestDto() { }

	public OrganisationStatusRequestDto(String status, String comment) {
		super();
		this.status = status;
		this.comment = comment;
	}
}
