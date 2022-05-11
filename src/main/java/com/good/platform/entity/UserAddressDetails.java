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
 * UserAddressDetails is to persist the user address details
 * @author Mohamedsuhail S
 *
 */
@Getter
@Setter
@Entity(name = "user_address_details")
public class UserAddressDetails extends DocumentId {
	
	public UserAddressDetails() {
	}

	public UserAddressDetails(String addressId) {
		this.setId(addressId);
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "address_line_1")
	private String addressLine1;
	
	@Column(name = "street_name")
	private String streetName;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "pincode")
	private String pincode;
	
	@Column(name = "latitude", columnDefinition = "DECIMAL(11,7)") 
	private BigDecimal latitude;
	
	@Column(name = "longitude", columnDefinition = "DECIMAL(11,7)")
	private BigDecimal longitude;

}
