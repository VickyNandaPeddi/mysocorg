package com.good.platform.response.dto;

import java.util.List;

import com.good.platform.request.dto.BeneficiaryGmapLocationsRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryLocationsResponse {
	public BeneficiaryLocationsResponse() {
		
	}
	
	public BeneficiaryLocationsResponse(String id, String location, String locationTitle) {
		super();
		this.id = id;
		this.location = location;
		this.locationTitle = locationTitle;
	}
	private String id;
	private String location;
	private String locationTitle;
	private List<BeneficiaryGmapLocationsResponse> beneficiaryGmapLocations;

}
