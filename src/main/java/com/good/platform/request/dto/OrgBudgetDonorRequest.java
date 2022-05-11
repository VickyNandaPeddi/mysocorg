package com.good.platform.request.dto;

import java.util.List;

import com.good.platform.model.OrganisationBudgetHistoryModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgBudgetDonorRequest {
	
	private String organisationId;
	private List<OrganisationBudgetHistoryModel> budgetHistory;
	
}
