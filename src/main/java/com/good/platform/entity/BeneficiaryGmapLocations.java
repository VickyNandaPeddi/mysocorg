package com.good.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@Entity(name = "beneficiary_gmap_locations")
public class BeneficiaryGmapLocations extends DocumentId{
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "beneficiary_locations_id", nullable = false)
	private BeneficiaryLocations beneficiaryLocations;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_beneficiary_history_id", nullable = false)
	private OrganisationBeneficiaryHistory organisationBeneficiaryHistory;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;
	
	/**
	 * Address latitude
	 */
	@Column(name = "latitude", columnDefinition = "DECIMAL(11,7)")
	private BigDecimal latitude;

	/**
	 * Address longitude
	 */
	@Column(name = "longitude", columnDefinition = "DECIMAL(11,7)")
	private BigDecimal longitude;
	
	

}
