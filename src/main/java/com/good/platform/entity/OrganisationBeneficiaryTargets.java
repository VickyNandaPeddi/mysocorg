
package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.good.platform.enums.BeneficiaryTargetCategory;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents entity of organisation_beneficiary_targets table.
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 15 Jun 2021
 */

@Getter
@Setter
@Entity(name = "organisation_beneficiary_targets")
public class OrganisationBeneficiaryTargets extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 Beneficiary history Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_beneficiary_history_id", nullable = false)
	private OrganisationBeneficiaryHistory organisationBeneficiaryHistory;

	/**
	 * Beneficiary Target Category
	 */
	@Column(name = "target_category")
	private String targetCategory;
}
