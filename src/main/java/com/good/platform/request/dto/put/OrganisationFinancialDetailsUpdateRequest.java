package com.good.platform.request.dto.put;

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


@Getter
@Setter
public class OrganisationFinancialDetailsUpdateRequest{

	/**
	 * Organisation Id of the related organisation.
	 */

	private String organisationId;

	/**
	 * Bank IFSC Code
	 */
	
	private String ifscCode;

	/**
	 * Organisation Bank Account Number
	 */

	private String accountNumber;

	/**
	 * Bank Branch Name
	 */

	private String branchName;

	/**
	 * Cancelled Cheque URL
	 */

	private String cancelledChequeUrl;

	/**
	 * Company PAN Card Number
	 */

	private String companyPanCardUrl;

	
	private List<LastAuditedStatementsUpdateRequest> lastAuditedStatements ;
	/**
	 * Cancelled Cheque URL filename
	 */

	private String cancelledChequeFilename;

	/**
	 * Company PAN Cardurl  Numberfile
	 */

	private String companyPanCardFilename;

}
