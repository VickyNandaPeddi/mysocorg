package com.good.platform.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationStatusResponseDto {
	
	private String organisationId;
	
	private String status;
	
	private String comment;
	
	public OrganisationStatusResponseDto() { }

	public OrganisationStatusResponseDto(String organisationId, String status, String comment) {
		super();
		this.organisationId = organisationId;
		this.status = status;
		this.comment = comment;
	}
}
