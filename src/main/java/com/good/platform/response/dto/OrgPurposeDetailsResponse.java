package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.model.OrganisationMissionStatementsModel;
import com.good.platform.model.OrganisationSectorModel;

import lombok.Data;

/**
 * OrgPurposeDetailsResponse is to represent the purpose details of the organisation
 * @author Mohamedsuhail S
 *
 */
@Data
public class OrgPurposeDetailsResponse {
	/**
	 * organisationId is to represent the organisation ID 
	 */
	private String organisationId;
	/**
	 * sectors is to represent the sector type of the purpose details
	 */
	private List<OrganisationSectorModel> sectors;
	/**
	 * missionStatements is to represent the mission statements part of the purpose page
	 */
	private List<OrganisationMissionStatementsModel> missionStatements;
	/**
	 * about is to represent the organisation description
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
