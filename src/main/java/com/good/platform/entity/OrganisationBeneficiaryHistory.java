package com.good.platform.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Entity of organisation_beneficiary_history table
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */
@ToString
@Getter
@Setter
@Entity(name = "organisation_beneficiary_history")
public class OrganisationBeneficiaryHistory extends DocumentId {

	public OrganisationBeneficiaryHistory() {

	}

	public OrganisationBeneficiaryHistory(String id) {
		this.setId(id);
	}

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 * Beneficiary type
	 */
	@Column(name = "beneficiary_type")
	private String beneficiaryType;

	/**
	 * People beneficiary min-age
	 */
	@Column(name = "min_age")
	private Integer minAge;

	/**
	 * People beneficiary max-age
	 */
	@Column(name = "max_age")
	private Integer maxAge;

	/**
	 * Beneficiary impacted location eg:Rural,Urban
	 */
	@Column(name = "location")
	private String location;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "beneficiaryHistoryId")
	private List<BeneficiaryImpactedHistory> totalBeneficiaryImpacted = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisationBeneficiaryHistory")
	private List<OrganisationBeneficiaryTargets> organisationBeneficiaryTargets = new ArrayList<>();
	
	@Column(name = "location_title")
	private String locationTitle;

}
