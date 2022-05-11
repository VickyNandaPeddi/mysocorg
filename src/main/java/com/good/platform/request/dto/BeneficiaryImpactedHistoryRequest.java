package com.good.platform.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BeneficiaryImpactedHistoryRequest {
	private Integer totalBeneficiaryImpacted;

	private String financialYear;
}
