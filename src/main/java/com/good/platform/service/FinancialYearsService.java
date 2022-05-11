package com.good.platform.service;

import com.good.platform.request.dto.FinancialYearsRequestDto;
import com.good.platform.response.dto.FinancialYearsResponseDto;

public interface FinancialYearsService {
	
	public FinancialYearsResponseDto getFinancialYears();
	
	public FinancialYearsResponseDto addFinancialYears(FinancialYearsRequestDto financialYearsRequest);

}
