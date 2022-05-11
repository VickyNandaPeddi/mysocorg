package com.good.platform.request.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents Entity of last_audited_statements
 *
 * 
 * @since 14 Jun 2021
 */
@Getter
@Setter
public class LastAuditedStatementsRequest{


	/**
	 * Year of the audit
	 */
	
	private Integer auditedYear;

	/**
	 * Name of the auditor
	 */
	
	private String auditor;

	/**
	 * Last Audited Statement Uploaded URL path
	 */
	
	private String auditedStatementUrl;
	/**
	 *  Last Audited Statement Uploaded  file name
	 */
	private String auditedStatementFileName;
}
