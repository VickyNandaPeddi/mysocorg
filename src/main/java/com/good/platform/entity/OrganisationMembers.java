package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents Entity class corresponding to its table.
 * 
 * @author Sreeju<sreeju.u@giglabz.com>
 *
 */

@Getter
@Setter
@Entity(name = "organisation_members")
public class OrganisationMembers extends DocumentId {
	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	/**
	 * Organisation member name
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Organisation member type - FOUNDER, BOARD_OF_DIRECTOR, etc.,
	 */
	@Column(name = "type")	
	private String type;

	/**
	 * Member type that is not included in the OrgMemberType - Custom member
	 * designation
	 */
	@Column(name = "other_type")
	private String otherType;

	/**
	 * Member's email id
	 */
	@Column(name = "email")
	private String email;

	/**
	 * Member's phone number
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * Member's id proof
	 */
	@Column(name = "id_proof")
	private String idProof;

	/**
	 * Id proof other than the mentioned in IdProof enum
	 */
	@Column(name = "other_id_proof")
	private String otherIdProof;

	/**
	 * Member's id proof number.
	 */
	@Column(name = "id_proof_number", columnDefinition = "TEXT")
	private String idProofNumber;

	/**
	 * is the member authorised signatory
	 */
	@Column(name = "authorised_signatory")
	private Boolean authorisedSignatory;
	
	@Column(name = "permanent_employee",columnDefinition = "boolean default false")
	private Boolean permanentEmployee;
	
	
	
	
	@Column(name = "aml_checked", columnDefinition = "varchar(255) default 'NOT_CHECKED'")
	private String amlChecked;

	@Column(name = "sort_order" , columnDefinition ="int default 0")
	private Integer sortOrder;

}
