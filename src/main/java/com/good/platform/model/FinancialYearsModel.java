package com.good.platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialYearsModel {

	private String id;
	private String financialYear;
	
	public FinancialYearsModel(String id, String financialYear) {
		this.id = id;
		this.financialYear = financialYear;
	}
	
}
