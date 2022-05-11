package com.good.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents Entity of organisation_budget_history table
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */

@Getter
@Setter
@Entity(name = "organisation_budget_history")
public class OrganisationBudgetHistory extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "financial_year_id", nullable = false) private
	 * FinancialYears financialYear;
	 */
	@Column(name = "financial_year")
	private String financialYear;
	
	@Column(name = "overall_budget")
	private BigDecimal overallBudget;
	
	//Added from tasks from book of words
	@Column(name = "no_of_single_year_programs")
	private Integer noOfSingleYearPrograms;
	
	@Column(name = "no_of_multi_year_programs")
	private Integer noOfMultiYearPrograms;
	
	@Column(name = "no_of_programs_completed_as_per_timelines")
	private Integer noOfProgramsCompletedAsPerTimelines;
	
	@Column(name = "no_of_programs_utilization_certificates_issued")
	private Integer noOfProgramsUtilizationCertificatesIssued;
	
	@Column(name = "no_of_programs_audited")
	private Integer noOfProgramsAudited;
	
}
