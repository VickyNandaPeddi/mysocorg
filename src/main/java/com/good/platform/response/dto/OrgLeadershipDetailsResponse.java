package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.model.OrganisationMembersCountModel;
import com.good.platform.model.OrganisationMembersModel;

import lombok.Data;

/**
 * OrgLeadershipDetailsResponse is to represent the request payload of the
 * organisation leadership page CRUD operations
 * 
 * @author Mohamedsuhail S
 *
 */
@Data
public class OrgLeadershipDetailsResponse {

	/**
	 * organisationId represents the organisational data
	 */
	private String organisationId;

	private List<OrganisationMembersModel> organisationMembersList;
	
	private List<OrganisationMembersCountModel> organisationMembersCount;
	
	private String memberDetailsId;

	private Integer noOfPermanentEmployees;

	private Boolean boardOfDirectorsWorkIndependently;

	private Boolean legalDepartment;

	private Boolean externalLegalCouncilForLegalAssistance;

}
