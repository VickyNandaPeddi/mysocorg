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
@Entity(name = "organisation_12a_80g_details")
public class Organisation12A80G extends DocumentId {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	
	@Column(name="certificate_number")
	private String org12a80gCertificateNumber;
	
	@Column(name="certificate_issue_date")
	private LocalDate org12a80gIssueDate;
	
	@Column(name="certificate_validity_date")
	private LocalDate org12a80gValidityDate;
}
