package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.good.platform.model.DropdownDataModel;
import com.good.platform.response.dto.CountriesResponseDto;
import com.good.platform.service.CountriesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CountriesServiceImpl implements CountriesService {

	/**
	 * getCountriesList is to get the whole countries list
	 */
	@Override
	public CountriesResponseDto getCountriesList() {
		int id = 1;
		List<DropdownDataModel> countriesList = new ArrayList<>();
		String[] countriesArray = Locale.getISOCountries();
		Arrays.sort(countriesArray);
		List<String> countriesDataList = Arrays.asList(countriesArray);
		List<String> countryNamesList = new ArrayList<String>();
		countriesList.add(new DropdownDataModel(String.valueOf(0), "-- Select Country --"));
		for (String countries : countriesDataList) {
			Locale countryData = new Locale("", countries);
			countryNamesList.add(countryData.getDisplayCountry());
		}
		Collections.sort(countryNamesList);
		for(String names : countryNamesList) {
			countriesList.add(new DropdownDataModel(String.valueOf(id), names));
			id++;
		}
		return CountriesResponseDto.builder().countryList(countriesList).build();
	}

	/**
	 * getCountry is to get the particular country based on the id from the list
	 * 
	 * @param in
	 */
	@Override
	public String getCountry(int id) {
		CountriesResponseDto countriesResponseDto = getCountriesList();
		Optional<DropdownDataModel> dropdownModel = countriesResponseDto.getCountryList().stream()
				.filter(country -> country.getId().equals(String.valueOf(id))).findFirst();
		if (dropdownModel.isEmpty()) {
			return "";
		} else {
			return dropdownModel.get().getValue();
		}
	}
	
	@Override
	public String getCountryByName(String countryName) {
		CountriesResponseDto countriesResponseDto = getCountriesList();
		Optional<DropdownDataModel> dropdownModel = countriesResponseDto.getCountryList().stream()
				.filter(country -> country.getValue().equals(countryName)).findFirst();
		if(dropdownModel.isEmpty()) {
			return "0";
		}else {
			return dropdownModel.get().getId();
		}
	}
}
