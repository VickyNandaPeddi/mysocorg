package com.good.platform.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.model.OrganisationBudgetHistoryModel;
import com.good.platform.response.dto.OrgBudgetDonorResponse;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationBudgetDonorHistoryMapper {
	
	
	@Mapping(source = "organisationId", target = "organisationId")
	@Mapping(source = "budgetHistoryList", target = "budgetHistory")
    public OrgBudgetDonorResponse organisationBudgetDonorHistoryValuesMapper(String organisationId,
    		List<OrganisationBudgetHistoryModel> budgetHistoryList);
    
}
