package com.good.platform.request.dto.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrganisationReviewDTO {
	
	private String overviewStatus;
	private String overviewComment;
	
	private String leadershipStatus;
	private String leadershipComment;
	
	private String locationStatus;
	private String locationComment;
	
	private String purposeStatus;
	private String purposeComment;
	
	private String trackRecordHistoryStatus;
	private String trackRecordHistoryComment;
	
	private String trackRecordBeneficiaryStatus;
	private String trackRecordBeneficiaryComment;
	
	private String financialDocumentsStatus;
	private String financialDocumentsComment;
	
	private String legalDocumentsStatus;
	private String legalDocumentsComment;
	private String status;
	private String comment;
}
