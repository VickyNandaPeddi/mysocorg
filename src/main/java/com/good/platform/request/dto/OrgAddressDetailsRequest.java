package com.good.platform.request.dto;

import java.util.List;

import com.good.platform.model.OrganisationAddressModel;

import lombok.Data;

@Data
public class OrgAddressDetailsRequest {

	/**
	 * organisationId is used to fetch the existing organisation data
	 */
	private String organisationId;

	private Integer countryId;

	/**
	 * addressList represents the collection of address detail data
	 */
	private List<OrganisationAddressModel> addressList;
}
