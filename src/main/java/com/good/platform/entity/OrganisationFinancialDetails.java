package com.good.platform.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Entity of organisation_financial_details table.
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */
@ToString
@Getter
@Setter
@Entity(name = "organisation_financial_details")
public class OrganisationFinancialDetails extends DocumentId {

	public OrganisationFinancialDetails() {
	}

	public OrganisationFinancialDetails(String financialDetailsId) {
		this.setId(financialDetailsId);
	}
	
	
	
	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id")
	private Organisation organisation;

	/**
	 * Bank IFSC Code
	 */
	@Column(name = "ifsc_code")
	private String ifscCode;

	/**
	 * Organisation Bank Account Number
	 */
	@Column(name = "account_number")
	private String accountNumber;

	/**
	 * Bank Branch Name
	 */
	@Column(name = "branch_name")
	private String branchName;

	/**
	 * Cancelled Cheque URL
	 */
	@Column(name = "cancelled_cheque_url")
	private String cancelledChequeUrl;

	/**
	 * Company PAN Card Number
	 */
	@Column(name = "company_pancard_url")
	private String companyPanCardUrl;

	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisationFinancialDetails")
	//private List<LastAuditedStatements> lastAuditedStatements = new ArrayList<>();
	/**
	 * Cancelled Cheque URL filename
	 */
	@Column(name = "cancelled_cheque_filename")
	private String cancelledChequeFilename;

	/**
	 * Company PAN Cardurl  Numberfile
	 */
	@Column(name = "company_pancard_filename")
	private String companyPanCardFilename;
	
	// Workdocs URL for cancelled cheque
	@Column(name = "cancelled_cheque_work_docs_url", length = 4000)
	private String cancelledChequeWorkdocsUrl;
	
	//Workdocs URL for pancard
	@Column(name = "company_pancard_work_docs_url", length = 4000)
	private String companyPanCardWorkdocsUrl;
}
