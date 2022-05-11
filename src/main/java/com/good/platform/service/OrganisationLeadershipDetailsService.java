package com.good.platform.service;

import com.good.platform.request.dto.AmlCheckRequest;
import com.good.platform.request.dto.OrgLeadershipDetailsRequest;
import com.good.platform.response.dto.AmlCheckResponse;
import com.good.platform.response.dto.OrgLeadershipDetailsResponse;

public interface OrganisationLeadershipDetailsService {

	/**
	 * addOrganisationLeadershipDetails is to add the members list
	 * @param orgLeadershipDetailsRequest
	 * @return
	 */
	OrgLeadershipDetailsResponse addOrganisationLeadershipDetails(
			OrgLeadershipDetailsRequest orgLeadershipDetailsRequest);

	/**
	 * updateOrganisationLeadershipDetails is to update the members list
	 * @param orgLeadershipDetailsRequest
	 * @return
	 */
	OrgLeadershipDetailsResponse updateOrganisationLeadershipDetails(
			OrgLeadershipDetailsRequest orgLeadershipDetailsRequest);

	/**
	 * getOrganisationLeadershipDetails is to fetch the members list
	 * @param id
	 * @return
	 */
	OrgLeadershipDetailsResponse getOrganisationLeadershipDetails(String id);

	Boolean deleteLeadershipDetails(String organisationId, String memberId);

	AmlCheckResponse updateAmlCheck(AmlCheckRequest amlCheckRequest);

	String getAmlCheckStatus(String memberId);
	
}
