package com.good.platform.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationAddressModel {
	
	/**
	 * Id of the related address details.
	 */
	private String id;

	/**
	 * Address Title - eg: Corporate Address, etc
	 * (title represents the heading of the address in the organisation locations page)
	 */
	private String title;

	/**
	 * address represents the "Address" field in the organisation Locations page
	 */
	private String address;

	/**
	 * country represents the "Country" field in the organisation Locations page
	 */
	private Integer country;

	/**
	 * state represents the "State" field in the organisation Locations page
	 */
	private String state;

	/**
	 * city represents the "City" field in the organisation Locations page
	 */
	private String city;

	/**
	 * pincode is to represent the "Pincode" field in the organisation Locations page
	 */
	private String pincode;

	/**
	 * latitude is to represent the latitude value got from the google maps API
	 */
	private BigDecimal latitude;

	/**
	 * longitude is to represent the longitude value got from the google API
	 */
	private BigDecimal longitude;
	
	private String district;
	
	private String taluk;

}
