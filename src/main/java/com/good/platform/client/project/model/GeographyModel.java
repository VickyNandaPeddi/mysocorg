package com.good.platform.client.project.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeographyModel {

	public GeographyModel(String area, String state, BigDecimal latitude, BigDecimal longitude) {
		this.area = area;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	private String area;
	
	private String state;
	
	private BigDecimal latitude;
	
	private BigDecimal longitude;
	
}
