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
@Entity(name = "organisation_members_details")
public class OrganisationMembersDetails extends DocumentId {
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;
	
	
	@Column(name = "no_of_permanent_employees")
	private Integer noOfPermanentEmployees;
	
	@Column(name = "bod_work_independently",columnDefinition = "boolean default false")
	private Boolean boardOfDirectorsWorkIndependently;
	
	@Column(name = "legal_department",columnDefinition = "boolean default false")
	private Boolean legalDepartment;
	
	@Column(name = "external_legal_council",columnDefinition = "boolean default false")
	private Boolean externalLegalCouncilForLegalAssistance;

}
