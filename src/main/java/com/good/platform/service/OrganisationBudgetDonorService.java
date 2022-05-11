package com.good.platform.service;

import com.good.platform.request.dto.OrgBudgetDonorRequest;
import com.good.platform.response.dto.OrgBudgetDonorResponse;

public interface OrganisationBudgetDonorService {
	
	/**
	 *addBudgetDonorHistoryDetails is to save the organisation budget and donor history data
	 *@param OrgBudgetDonorRequest
	 */
	public OrgBudgetDonorResponse addBudgetDonorHistoryDetails(OrgBudgetDonorRequest orgBudgetDonorRequest);
	
	/**
	 * updateBudgetDonorHistoryDetails is to update the budget donor history details
	 * @param request
	 * @return
	 */
	public OrgBudgetDonorResponse updateBudgetDonorHistoryDetails(OrgBudgetDonorRequest request);
	
	/**
	 *getBudgetDonorHistoryDetails is to fetch the organization's purpose detail data
	 */
	public OrgBudgetDonorResponse getBudgetDonorHistoryDetails(String id);

	public Boolean deleteOrgDonorHistory(String organisationId, String donorId);

	
}
