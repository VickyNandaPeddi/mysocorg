package com.good.platform.model;

import lombok.Data;

@Data
public class OrganisationModel {

	private String organisationId;
	private String organisationName;
	
	public OrganisationModel() {
	}
	public OrganisationModel(String organisationId, String organisationName) {
		this.organisationId = organisationId;
		this.organisationName = organisationName;
	}
	
	
	
}
