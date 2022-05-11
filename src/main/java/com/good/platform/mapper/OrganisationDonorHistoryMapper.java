package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationBudgetHistory;
import com.good.platform.entity.OrganisationDonorHistory;
import com.good.platform.model.OrganisationDonorHistoryModel;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationDonorHistoryMapper {
	
	@Mapping(source = "organisationId", target = "organisation", qualifiedByName = "getOrganisation")
	@Mapping(source = "model.donorId", target = "id")
	@Mapping(source = "model.donorName", target = "donorName")
	@Mapping(source = "model.country", target = "country")
	@Mapping(source = "model.amountFunded", target = "amountFunded")
	@Mapping(source = "model.percentageContributed", target = "percentageContributed")
    public OrganisationDonorHistory organisationDonorHistoryValuesMapper(OrganisationDonorHistoryModel model,
    		String organisationId);
	
	@Mapping(source = "entity.id", target = "donorId")
	@Mapping(source = "entity.donorName", target = "donorName")
	@Mapping(source = "entity.country", target = "country")
	@Mapping(source = "entity.amountFunded", target = "amountFunded")
	@Mapping(source = "entity.percentageContributed", target = "percentageContributed")
	public OrganisationDonorHistoryModel organisationDonorHistoryValuesMapper(OrganisationDonorHistory entity,
			String organisationId);
	
	@Named("getOrganisation")
	default Organisation getOrganisation(String id) {
		return new Organisation(id);
	}
	
}
