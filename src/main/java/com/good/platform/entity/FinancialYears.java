package com.good.platform.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Enity for financial_years table
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */
@ToString
@Getter
@Setter
@Entity(name = "financial_years")
public class FinancialYears extends DocumentId {
	
	public FinancialYears() {
	}

	public FinancialYears(String financialYearsId) {
		this.setId(financialYearsId);
	}

	private String financialYear;
}
