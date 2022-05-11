package com.good.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents Entity for '"organisation_address" table.
 *
 * @author Sreeju <sree.u@giglabz.com>
 * @since 14 Jun 2021
 */
@Getter
@Setter
@Entity(name = "organisation_address")
public class OrganisationAddress extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 * Address Title - eg: Corporate Address, etc
	 * (title represents the heading of the address in the organisation locations page)
	 */
	@Column(name = "title")
	private String title;

	/**
	 * address represents the "Address" field in the organisation Locations page
	 */
	@Column(name = "address", columnDefinition = "TEXT")
	private String address;

	/**
	 * country represents the "Country" field in the organisation Locations page
	 */
	@Column(name = "country")
	private String country;

	/**
	 * state represents the "State" field in the organisation Locations page
	 */
	@Column(name = "state")
	private String state;

	/**
	 * city represents the "City" field in the organisation Locations page
	 */
	@Column(name = "city")
	private String city;

	/**
	 * pincode is to represent the "Pincode" field in the organisation Locations page
	 */
	@Column(name = "pincode")
	private String pincode;

	/**
	 * latitude is to represent the latitude value got from the google maps API
	 */
	@Column(name = "latitude", columnDefinition = "DECIMAL(11,7)")
	private BigDecimal latitude;

	/**
	 * longitude is to represent the longitude value got from the google API
	 */
	@Column(name = "longitude", columnDefinition = "DECIMAL(11,7)")
	private BigDecimal longitude;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "taluk")
	private String taluk;

}
