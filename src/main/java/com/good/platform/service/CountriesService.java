package com.good.platform.service;

import com.good.platform.response.dto.CountriesResponseDto;

/**
 * CountriesService is to handle the countries service implementations
 * @author Mohamedsuhail S
 *
 */
public interface CountriesService {
	
	/**
	 * getCountriesList is to get the whole countries list
	 */
	public CountriesResponseDto getCountriesList();

	/**
	 * getCountry is to get the particular country based on the id from the list
	 * @param in
	 */
	public String getCountry(int id);

	String getCountryByName(String countryName);

}
