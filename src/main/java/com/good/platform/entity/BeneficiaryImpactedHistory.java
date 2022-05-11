package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "beneficiary_impacted_history")
public class BeneficiaryImpactedHistory extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 * Organisation Beneficiary History Id of the related impacted history.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "beneficiary_history_id", nullable = false)
	private OrganisationBeneficiaryHistory beneficiaryHistoryId;

	/**
	 * Total no of beneficiaries impacted
	 */
	@Column(name = "total_beneficiary_impacted")
	private Integer totalBeneficiaryImpacted;

	/**
	 * Financial year
	 */
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "financial_year_id", nullable = false)
//	private FinancialYears financialYear;

	@Column(name = "financial_year")
	private String financialYear;
}
