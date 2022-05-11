package com.good.platform.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.good.platform.model.OrganisationMissionStatementsModel;
import com.good.platform.model.OrganisationSectorModel;
import com.good.platform.response.dto.OrgPurposeDetailsResponse;

/**
 * OrganisationPurposeDetailsMapper represents the mapping processes for purpose details
 * @author Mohamedsuhail S
 *
 */
@Mapper(componentModel = "spring")
public interface OrganisationPurposeDetailsMapper {
	
	/**
	 * organisationPurposeValuesMapper is to map the response data of sector, mission statements collectively as a response
	 * @param organisationId
	 * @param missionStatementModel
	 * @param organisationSectorModel
	 * @param about
	 * @return OrgPurposeDetailsResponse
	 */
	@Mappings({
		@Mapping(source = "organisationId", target = "organisationId"),
		@Mapping(source = "about", target = "about"),
		@Mapping(source = "missionStatementModel", target = "missionStatements"),
		@Mapping(source = "organisationSectorModel", target = "sectors"),
		@Mapping(source = "visionStatement", target = "visionStatement"),
		@Mapping(source = "valueStatement", target = "valueStatement")
	})
    OrgPurposeDetailsResponse organisationPurposeValuesMapper(String organisationId, List<OrganisationMissionStatementsModel> missionStatementModel,
    		List<OrganisationSectorModel> organisationSectorModel, String about, String visionStatement, String valueStatement);

}
