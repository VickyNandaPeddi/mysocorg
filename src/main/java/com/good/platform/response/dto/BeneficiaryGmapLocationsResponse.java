package com.good.platform.response.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BeneficiaryGmapLocationsResponse {
	
    private String id;
	private BigDecimal latitude;
	private BigDecimal longitude;
}
