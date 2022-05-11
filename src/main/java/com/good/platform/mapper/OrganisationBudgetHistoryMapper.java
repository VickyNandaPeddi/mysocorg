package com.good.platform.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.FinancialYears;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationBudgetHistory;
import com.good.platform.model.OrganisationBudgetHistoryModel;
import com.good.platform.model.OrganisationDonorHistoryModel;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationBudgetHistoryMapper {
	
	@Mappings({
		@Mapping(source = "organisation", target = "organisation"),
		@Mapping(source = "model.budgetId", target = "id"),
		@Mapping(source = "financialYear", target = "financialYear"),
		@Mapping(source = "model.overallBudget", target = "overallBudget"),
		@Mapping(source = "model.noOfSingleYearPrograms", target = "noOfSingleYearPrograms"),
		@Mapping(source = "model.noOfMultiYearPrograms", target = "noOfMultiYearPrograms"),
		@Mapping(source = "model.noOfProgramsCompletedAsPerTimelines", target = "noOfProgramsCompletedAsPerTimelines"),
		@Mapping(source = "model.noOfProgramsUtilizationCertificatesIssued", target = "noOfProgramsUtilizationCertificatesIssued"),
		@Mapping(source = "model.noOfProgramsAudited", target = "noOfProgramsAudited")
	})
    public OrganisationBudgetHistory organisationBudgetHistoryValuesMapper(OrganisationBudgetHistoryModel model,
    		Organisation organisation,String financialYear);

	@Mappings({
		@Mapping(source = "entity.id", target = "budgetId"),
		@Mapping(source = "entity.financialYear", target = "financialYear"),
		@Mapping(source = "entity.overallBudget", target = "overallBudget"),
		@Mapping(target = "donorHistory", ignore=true),
		@Mapping(source = "entity.noOfSingleYearPrograms", target = "noOfSingleYearPrograms"),
		@Mapping(source = "entity.noOfMultiYearPrograms", target = "noOfMultiYearPrograms"),
		@Mapping(source = "entity.noOfProgramsCompletedAsPerTimelines", target = "noOfProgramsCompletedAsPerTimelines"),
		@Mapping(source = "entity.noOfProgramsUtilizationCertificatesIssued", target = "noOfProgramsUtilizationCertificatesIssued"),
		@Mapping(source = "entity.noOfProgramsAudited", target = "noOfProgramsAudited")
	})
    public OrganisationBudgetHistoryModel organisationBudgetHistoryValuesMapper(OrganisationBudgetHistory entity, String organisationId,
    		List<OrganisationDonorHistoryModel> modelList);
	
	@Named("getFinancialYears")
	default FinancialYears mapFinancialYearsEntity(String financialYearsId) {
		return new FinancialYears(financialYearsId);
	}
	
}
