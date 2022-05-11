package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@Entity(name = "beneficiary_locations")
public class BeneficiaryLocations extends DocumentId{
	
	public BeneficiaryLocations() {
		
	}
	
	public BeneficiaryLocations(String id) {
		this.setId(id);
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_beneficiary_history_id", nullable = false)
	private OrganisationBeneficiaryHistory organisationBeneficiaryHistory;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;
	
	/**
	 * Beneficiary impacted location eg:Rural,Urban
	 */
	@Column(name = "location")
	private String location;
	
	@Column(name = "location_title")
	private String locationTitle;
	
	

}
