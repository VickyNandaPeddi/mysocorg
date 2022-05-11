package com.good.platform.response.dto;

import java.math.BigDecimal;
import java.util.List;

import com.good.platform.enums.BeneficiaryLocation;
import com.good.platform.enums.BeneficiaryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationBeneficiaryHistoryResponse {
	private String id;
	private String organisationId;
	private String beneficiaryType;
	private Integer minAge;
	private Integer maxAge;
	private String location;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private List<BeneficiaryImpactedHistoryResponse> beneficiaryImpactedHistory;
//	private List<OrganisationBeneficiaryTargetsResponse> targetCategory;
	private List<String> targetCategory;
	private boolean deleted;
	private Long createdAt;
	private Long modifiedAt;
	private String createdBy;
	private String lastModifiedBy;
	private String locationTitle;
	private List<BeneficiaryLocationsResponse> beneficiaryLocations;
}