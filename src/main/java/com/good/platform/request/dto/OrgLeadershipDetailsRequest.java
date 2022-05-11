package com.good.platform.request.dto;

import java.util.List;

import com.good.platform.model.OrganisationMembersCountModel;
import com.good.platform.model.OrganisationMembersModelRequest;

import lombok.Data;

/**
 * OrgLeadershipDetailsRequest is to represent the request payload of the
 * organisation leadership page CRUD operations
 * 
 * @author Mohamedsuhail S
 *
 */
@Data
public class OrgLeadershipDetailsRequest {

	/**
	 * organisationId represents the organisational data
	 */
	private String organisationId;

	private List<OrganisationMembersModelRequest> organisationMembersList;
	
	private List<OrganisationMembersCountModel> organisationMembersCount;
	
	private String memberDetailsId;

	private Integer noOfPermanentEmployees;

	private Boolean boardOfDirectorsWorkIndependently;

	private Boolean legalDepartment;

	private Boolean externalLegalCouncilForLegalAssistance;
}
