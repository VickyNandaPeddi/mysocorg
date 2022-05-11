package com.good.platform.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDetailsModel {

	public AddressDetailsModel(String userId, String addressLine1, String streetName, String city, String state,
			String pincode, BigDecimal latitude, BigDecimal longitude) {
		this.userId = userId;
		this.addressLine1 = addressLine1;
		this.streetName = streetName;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public AddressDetailsModel() {
		
	}
	
	private String userId;
	private String addressLine1;
	private String streetName;
	private String city;
	private String state;
	private String pincode;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
}
