package com.good.platform.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.good.platform.entity.FinancialYears;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.FinancialYearsMapper;
import com.good.platform.model.FinancialYearsModel;
import com.good.platform.repository.FinancialYearsRepository;
import com.good.platform.request.dto.FinancialYearsRequestDto;
import com.good.platform.response.dto.FinancialYearsResponseDto;
import com.good.platform.service.FinancialYearsService;
import com.good.platform.utility.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * FinancialYearsServiceImpl is to get the service implementations or business
 *  implementations
 * @author Mohamedsuhail S
 *
 */
@Service
@Slf4j
public class FinancialYearsServiceImpl implements FinancialYearsService{
	
	@Autowired
	FinancialYearsRepository financialYearsRepo;
	
	@Autowired
	FinancialYearsMapper financialYearsMapper;
	
	@Autowired
	SecurityUtil securityUtil;
	
	/**
	 * getFinancialYears is to fetch the data list
	 */
	@Override
	public FinancialYearsResponseDto getFinancialYears() {
		log.debug("Fetch the financial years list starts");
		try{
			List<FinancialYearsModel> dataList = new ArrayList<>();
			List<FinancialYears> financialYears = financialYearsRepo.findAll();
			if(!financialYears.isEmpty()) {
				for(FinancialYears financialYear : financialYears) {
					dataList.add(new FinancialYearsModel(financialYear.getId(),
							financialYear.getFinancialYear()));
				}
			}
			FinancialYearsResponseDto financialYearsResponseDto = FinancialYearsResponseDto.builder()
					.financialYears(dataList).build();
			log.debug("Fetch the financial years list ends");
			return financialYearsResponseDto;
		}catch(Exception exception) {
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FINANCIAL_YEARS_FETCH_FAIL);
		}
	}

	@Override
	public FinancialYearsResponseDto addFinancialYears(FinancialYearsRequestDto financialYearsRequest) {
		log.debug("Fetching the financial years list starts");
		try {
			List<FinancialYearsModel> dataList = new ArrayList<>();
			for(String data : financialYearsRequest.getFinancialYears()) {
				FinancialYears financialYears = financialYearsRepo.findByFinancialYear(data);
				if(Objects.isNull(financialYears)) {
					FinancialYears financialYearEntity = financialYearsMapper.financialYearsMapper(data);
					financialYearEntity.setCreatedBy(securityUtil.getCurrentUser());
					FinancialYears savedEntity = financialYearsRepo.save(financialYearEntity);
					dataList.add(financialYearsMapper.financialYearsMapper(savedEntity));
				}
			}
			FinancialYearsResponseDto financialYearsResponseDto = FinancialYearsResponseDto.builder().financialYears(dataList).build();
			log.debug("Fetching the financial years list ends");
			return financialYearsResponseDto;
		}catch(Exception exception) {
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FINANCIAL_YEARS_ADD_FAIL);
		}
	}

}
