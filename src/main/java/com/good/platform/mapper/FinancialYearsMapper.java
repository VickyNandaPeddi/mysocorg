package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.FinancialYears;
import com.good.platform.model.FinancialYearsModel;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FinancialYearsMapper {
	
	@Mapping(source = "data", target = "financialYear")
	public FinancialYears financialYearsMapper(String data);
	
	@Mapping(source = "financialYear", target = "financialYear")
	public FinancialYearsModel financialYearsMapper(FinancialYears entity);

}
