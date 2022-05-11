package com.good.platform.entity.validator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.good.platform.entity.DocumentId;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "organisation_review")
public class OrganisationReview extends DocumentId {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;

	@Column(name = "overview_status")
	private String overviewStatus;
	@Column(name = "overview_comment")
	private String overviewComment;
	@Column(name = "leadership_status")
	private String leadershipStatus;
	@Column(name = "leadership_comment")
	private String leadershipComment;
	@Column(name = "location_status")
	private String locationStatus;
	@Column(name = "location_comment")
	private String locationComment;
	@Column(name = "purpose_status")
	private String purposeStatus;
	@Column(name = "purpose_comment")
	private String purposeComment;
	@Column(name = "track_record_history_status")
	private String trackRecordHistoryStatus;
	@Column(name = "track_record_history_comment")
	private String trackRecordHistoryComment;
	@Column(name = "track_record_beneficiary_status")
	private String trackRecordBeneficiaryStatus;
	@Column(name = "track_record_beneficiary_comment")
	private String trackRecordBeneficiaryComment;
	@Column(name = "financial_documents_status")
	private String financialDocumentsStatus;
	@Column(name = "financial_documents_comment")
	private String financialDocumentsComment;
	@Column(name = "legal_documents_status")
	private String legalDocumentsStatus;
	@Column(name = "legal_documents_comment")
	private String legalDocumentsComment;

}
