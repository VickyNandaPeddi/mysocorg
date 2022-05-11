package com.good.platform.service;

import com.good.platform.request.dto.OrgAddressDetailsRequest;
import com.good.platform.response.dto.OrgAddressDetailsResponse;

public interface OrgAddressService {
	
	public OrgAddressDetailsResponse addOrgAddressDetails(OrgAddressDetailsRequest orgAddressDetailsRequest);
	
	public OrgAddressDetailsResponse updateOrgAddressDetails(OrgAddressDetailsRequest request);
	
	public OrgAddressDetailsResponse getOrgAddressData(String id);

	public Boolean deleteOrgAddress(String addressId, String organisationId);


	
}
