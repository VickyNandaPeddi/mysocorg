package com.good.platform.request.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BeneficiaryLocationsRequest {

	private String id;
	private String location;
	private String locationTitle;
	private List<BeneficiaryGmapLocationsRequest> beneficiaryGmapLocations;
}
