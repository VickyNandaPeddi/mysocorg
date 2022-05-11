package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.model.FinancialYearsModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinancialYearsResponseDto {
	
	private List<FinancialYearsModel> financialYears;

}
