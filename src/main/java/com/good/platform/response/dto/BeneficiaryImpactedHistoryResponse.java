package com.good.platform.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryImpactedHistoryResponse {

	
	public BeneficiaryImpactedHistoryResponse() {
		
	}
	public BeneficiaryImpactedHistoryResponse(String beneficiaryImpactedHistoryId ,Integer totalBeneficiaryImpacted, String financialYear) {
		this.beneficiaryImpactedHistoryId=beneficiaryImpactedHistoryId;
		this.totalBeneficiaryImpacted = totalBeneficiaryImpacted;
		this.financialYear = financialYear;
	}

	private String beneficiaryImpactedHistoryId;
	private Integer totalBeneficiaryImpacted;

	private String financialYear;
}
