package com.good.platform.model;

import lombok.Builder;
import lombok.Data;

/**
 * CountriesModel is to represent the countries list model
 * @author Mohamedsuhail S
 *
 */
@Data
@Builder
public class CountriesModel {
	
	private String id;
	private String countries;

}
