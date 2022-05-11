package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.model.OrganisationBudgetHistoryModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrgBudgetDonorResponse {
	
	public OrgBudgetDonorResponse() {
	}
	
	public OrgBudgetDonorResponse(String organisationId, List<OrganisationBudgetHistoryModel> budgetHistory) {
		this.organisationId = organisationId;
		this.budgetHistory = budgetHistory;
	}
	
	private String organisationId;
	private List<OrganisationBudgetHistoryModel> budgetHistory;
	
}
