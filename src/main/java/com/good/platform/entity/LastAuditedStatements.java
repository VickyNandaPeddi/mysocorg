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
 * This class represents Entity of last_audited_statements
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */
@ToString
@Getter
@Setter
@Entity(name = "last_audited_statements")
public class LastAuditedStatements extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id")
	private Organisation organisation;

	/**
	 * Financial Details Id
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "financial_detail_id")
	private OrganisationFinancialDetails organisationFinancialDetails;

	/**
	 * Year of the audit
	 */
	@Column(name = "audited_year")
	private Integer auditedYear;

	/**
	 * Name of the auditor
	 */
	@Column(name = "auditor")
	private String auditor;

	/**
	 * Last Audited Statement Uploaded URL path
	 */
	@Column(name = "audited_statement_url")
	private String auditedStatementUrl;
	
	/**
	 * auditedStatementWorkdocsUrl is to save the work docs url of the audited statements
	 */
	@Column(name = "audited_statement_work_docs_url", length = 4000)
	private String auditedStatementWorkdocsUrl;
	
	/**
	 * Last Audited Statement Uploaded file name
	 */
	@Column(name = "audited_statement_file_name")
	private String auditedStatementFileName;
}
