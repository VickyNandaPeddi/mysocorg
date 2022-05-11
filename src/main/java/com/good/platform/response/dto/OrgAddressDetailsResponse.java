package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.model.OrganisationAddressModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrgAddressDetailsResponse {
	private String organisationId;
	private Integer countryId;
	private List<OrganisationAddressModel> addressList;
}
