package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.model.DropdownDataModel;

import lombok.Builder;
import lombok.Data;

/**
 * CountriesResponseDto represents the respose data structure of the data countries
 * @author Mohamedsuhail S
 *
 */
@Data
@Builder
public class CountriesResponseDto {

	private List<DropdownDataModel> countryList;
}
