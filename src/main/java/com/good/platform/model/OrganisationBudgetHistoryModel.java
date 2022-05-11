package com.good.platform.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * OrganisationBudgetHistoryModel is to represent the model object of the budget history
 * of the organisation
 * @author Mohamedsuhail S
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class OrganisationBudgetHistoryModel {
	public OrganisationBudgetHistoryModel() {
		
	}
	
	public OrganisationBudgetHistoryModel(String budgetId, String financialYear, BigDecimal overallBudget) {
		this.budgetId = budgetId;
		this.financialYear = financialYear;
		this.overallBudget = overallBudget; 
	}
	
	public OrganisationBudgetHistoryModel(String budgetId, String financialYear, BigDecimal overallBudget,
			Integer noOfSingleYearPrograms, Integer noOfMultiYearPrograms,
			Integer noOfProgramsCompletedAsPerTimelines, Integer noOfProgramsUtilizationCertificatesIssued,
			Integer noOfProgramsAudited) {
		this.budgetId = budgetId;
		this.financialYear = financialYear;
		this.overallBudget = overallBudget;
		this.noOfSingleYearPrograms = noOfSingleYearPrograms;
		this.noOfMultiYearPrograms = noOfMultiYearPrograms;
		this.noOfProgramsCompletedAsPerTimelines = noOfProgramsCompletedAsPerTimelines;
		this.noOfProgramsUtilizationCertificatesIssued= noOfProgramsUtilizationCertificatesIssued;
		this.noOfProgramsAudited = noOfProgramsAudited;
	}
	
	private String budgetId;
	
	private String financialYear;

	private BigDecimal overallBudget;
	
	private Integer noOfSingleYearPrograms;
	
	private Integer noOfMultiYearPrograms;
	
	private Integer noOfProgramsCompletedAsPerTimelines;
	
	private Integer noOfProgramsUtilizationCertificatesIssued;
	
	private Integer noOfProgramsAudited;
	
	private List<OrganisationDonorHistoryModel> donorHistory;
	

}
