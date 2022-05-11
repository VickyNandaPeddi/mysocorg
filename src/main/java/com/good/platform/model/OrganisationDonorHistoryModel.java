package com.good.platform.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationDonorHistoryModel {
	
	public OrganisationDonorHistoryModel(String donorId, String donorName, String country, BigDecimal amountFunded,
			BigDecimal percentageContributed) {
		this.donorId = donorId;
		this.donorName = donorName;
		this.country = country;
		this.amountFunded = amountFunded;
		this.percentageContributed = percentageContributed;
	}
	
	public OrganisationDonorHistoryModel() {
	}
	
	private String donorId;
	
	private String donorName;
	
	private String country;
	
	private BigDecimal amountFunded;
	
	private BigDecimal percentageContributed;


}
