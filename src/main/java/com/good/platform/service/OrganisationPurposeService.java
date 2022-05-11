package com.good.platform.service;

import com.good.platform.request.dto.OrgPurposeDetailsRequest;
import com.good.platform.response.dto.OrgPurposeDetailsResponse;

public interface OrganisationPurposeService {
	
	/**
	 *addOrganisationPurposeDetails is to add the details regarding the purpose of the organisation.
	 *@param OrgPurposeDetailsRequest
	 */
	public OrgPurposeDetailsResponse addOrganisationPurposeDetails(OrgPurposeDetailsRequest orgPurposeDetailsRequest);
	
	/**
	 * updateOrganisationPurposeDetails is to update the organisation purpose details
	 * @param orgPurposeDetailsRequest
	 * @param id
	 * @return
	 */
	public OrgPurposeDetailsResponse updateOrganisationPurposeDetails(OrgPurposeDetailsRequest orgPurposeDetailsRequest);
	
	/**
	 * getOrganisationPurposeDetails is to get the organisationpurpose details
	 * @param id
	 * @return
	 */
	public OrgPurposeDetailsResponse getOrganisationPurposeDetails(String id);

	public Boolean deleteMissionStatement(String missionStatementId, String organisationId);


	
}
