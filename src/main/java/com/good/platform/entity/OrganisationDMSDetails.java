package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Entity to keep the details of document management
 * system of the organisation
 *
 * @author Anjana
 * @since 14 Jan 2022
 */
@ToString
@Getter
@Setter
@Entity(name = "organisation_dms_details")
public class OrganisationDMSDetails extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 * The id of the folder created for organisation
	 */
	@Column(name = "work_docs_org_folder_id")
	private String workdocsOrgFolderId;

}
