package com.good.platform.request.dto;

import java.util.List;

import com.good.platform.model.OrganisationMissionStatementsModel;
import com.good.platform.model.OrganisationSectorModel;

import lombok.Data;

/**
 * OrgPurposeDetailsRequest is to represent the request payload of the organisation purpose page
 * CRUD operations
 * @author Mohamedsuhail S
 *
 */
@Data
public class OrgPurposeDetailsRequest {
	
	/**
	 * organisationId represents the organisational data 
	 */
	private String organisationId;
	/**
	 * sectors represents the sector section in the Organisation purpose screen
	 */
	private List<OrganisationSectorModel> sectors;
	/**
	 * missionStatements represents the missions statements of the organisation
	 */
	private List<OrganisationMissionStatementsModel> missionStatements;
	/**
	 * about represents the about data of the organisation
	 */
	private String about;
	
	/**
	 * valueStatement is to add the values statement of the organisation 
	 */
	private String valueStatement;
	
	/**
	 * visionStatement is to add the values statement of the organisation 
	 */
	private String visionStatement;
	
}
