package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.good.platform.enums.SectorType;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents Entity for organisation_sector table Sectors such as
 * Agriculture, Education, Skills etc.,
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */

@Getter
@Setter
@Entity(name = "organisation_sector")
public class OrganisationSector extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 * PRIMARY / SECONDARY
	 */
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private SectorType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sector_id")
	private Sectors sector;

}
