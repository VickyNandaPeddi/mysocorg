package com.good.platform.response.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Entity of last_audited_statements
 *
 * 
 * @since 14 Jun 2021
 */
@Getter
@Setter
@ToString
public class LastAuditedStatementsResponse {
	public LastAuditedStatementsResponse() {
		
	}

	public LastAuditedStatementsResponse(String id, Integer auditedYear,
			String auditor, String auditedStatementUrl, String auditedStatementFileName) {
		this.id = id;
		this.auditedYear = auditedYear;
		this.auditor = auditor;
		this.auditedStatementUrl = auditedStatementUrl;
		this.auditedStatementFileName = auditedStatementFileName;
	}

	private String id;
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
