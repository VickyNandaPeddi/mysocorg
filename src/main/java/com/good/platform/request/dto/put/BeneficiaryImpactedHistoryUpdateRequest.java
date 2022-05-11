package com.good.platform.request.dto.put;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BeneficiaryImpactedHistoryUpdateRequest {
	private String  beneficiaryImpactedHistoryId; 
	
	private Integer totalBeneficiaryImpacted;

	private String financialYear;
}
