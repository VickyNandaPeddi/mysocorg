package com.good.platform.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "organisation_fcra_details")
public class OrganisationFCRADetails extends DocumentId{

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;
	
	@Column(name="fcra_number")
	private String fcraNumber;
	
	@Column(name="fcra_issue_date")
	private LocalDate fcraIssueDate;
	
	@Column(name="fcra_expiry_date")
	private LocalDate fcraExpiryDate;
}
