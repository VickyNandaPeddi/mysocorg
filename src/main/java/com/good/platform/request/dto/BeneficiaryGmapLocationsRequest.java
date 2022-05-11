package com.good.platform.request.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class BeneficiaryGmapLocationsRequest {
    private String id;
	private BigDecimal latitude;
	private BigDecimal longitude;

}
