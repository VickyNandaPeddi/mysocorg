package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationMissionStatements;
import com.good.platform.model.OrganisationMissionStatementsModel;

/**
 * OrganisationMissionStatementMapper is to represent all the mapping functionalities of mission statements part 
 * of organisation purpose page
 * @author Mohamedsuhail S
 *
 */
@Mapper(componentModel = "spring")
public interface OrganisationMissionStatementMapper {
	
    /**
     * organisationMissionStatementsValuesMapper is to map OrganisationMissionStatementsModel (Model) -> OrganisationMissionStatements (Entity)
     * @param model
     * @param organisation
     * @return OrganisationMissionStatements
     */
    @Mapping(source = "organisation", target = "organisation")
    @Mapping(source = "model.statement", target = "statement")
    @Mapping(source = "model.id", target = "id", ignore = true)
    public OrganisationMissionStatements organisationMissionStatementsValuesMapper(OrganisationMissionStatementsModel model,
    		Organisation organisation);
    
    /**
     * organisationMissionStatementsUpdateValuesMapper is to map OrganisationMissionStatementsModel (Model) -> OrganisationMissionStatements (Entity)
     * @param model
     * @param organisation
     * @return
     */
    @Mapping(source = "organisation", target = "organisation")
    @Mapping(source = "model.statement", target = "statement")
    @Mapping(source = "model.id", target = "id")
    public OrganisationMissionStatements organisationMissionStatementsUpdateValuesMapper(OrganisationMissionStatementsModel model,
    		Organisation organisation);
    
    /**
     * organisationMissionStatementsValuesMapper is to map OrganisationMissionStatements (Entity) -> OrganisationMissionStatementsModel (Model)
     * @param organisationId
     * @param entity
     * @return
     */
    @Mapping(source = "entity.statement", target = "statement")
    @Mapping(source = "entity.id", target = "id")
    public OrganisationMissionStatementsModel organisationMissionStatementsValuesMapper(String organisationId,
    		OrganisationMissionStatements entity);
    
}
