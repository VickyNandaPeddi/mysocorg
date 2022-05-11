package com.good.platform.response.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrganisationBeneficiaryTargetsResponse {

	private String organisationBeneficiaryTargetsId;
	private String targetCategory;

	public OrganisationBeneficiaryTargetsResponse() {

	}

	public OrganisationBeneficiaryTargetsResponse(String organisationBeneficiaryTargetsId, String targetCategory) {

		this.organisationBeneficiaryTargetsId = organisationBeneficiaryTargetsId;
		this.targetCategory = targetCategory;
	}
}
