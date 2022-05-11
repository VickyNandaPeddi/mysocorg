package com.good.platform.request.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrganisationBeneficiaryHistoryRequest {

	private String organisationId;

	private String beneficiaryType;

	private Integer minAge;

	private Integer maxAge;

	private String location;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private List<BeneficiaryImpactedHistoryRequest> beneficiaryImpactedHistory;

	private List<String> targetCategory;
	private String locationTitle;
	private List<BeneficiaryLocationsRequest> beneficiaryLocations;
	
}