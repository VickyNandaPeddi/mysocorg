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
 * This class represents Entity of organisation_donor_history table
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */

@Getter
@Setter
@Entity(name = "organisation_donor_history")
public class OrganisationDonorHistory extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "budget_history_id", nullable = false)
	private OrganisationBudgetHistory budgetHistory;

	/**
	 * Donor's name
	 */
	@Column(name = "donor_name")
	private String donorName;

	/**
	 * Donor's country
	 */
	@Column(name = "country")
	private String country;

	/**
	 * Amount funded by the donor.
	 */
	@Column(name = "amount_funded")
	private BigDecimal amountFunded;

	/**
	 * Percentage of the contributed amount from the overall Budget.
	 */
	@Column(name = "percentage_contributed", precision = 6, scale = 3)
	private BigDecimal percentageContributed;

}
