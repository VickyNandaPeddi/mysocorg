package com.good.platform.request.dto.put;



import java.math.BigDecimal;
import java.util.List;

import com.good.platform.request.dto.BeneficiaryLocationsRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class OrganisationBeneficiaryHistoryUpdateRequest {
   
	 
	private String organisationId;

	private String beneficiaryType;

	private Integer minAge;

	private Integer maxAge;

	private String location;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private List<BeneficiaryImpactedHistoryUpdateRequest> beneficiaryImpactedHistory;

//	private List<BeneficiaryTargetCategoryUpdateRequest> targetCategory;
	
	private List<String> targetCategory;
	private String locationTitle;
	private List<BeneficiaryLocationsRequest> beneficiaryLocations;
}