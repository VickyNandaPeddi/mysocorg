package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents Entity of organisation_mission_statements table.
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */
@Getter
@Setter
@Entity(name = "organisation_mission_statements")
public class OrganisationMissionStatements extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 * Mission Statement
	 */
	@Column(name = "statement")
	private String statement;
}
