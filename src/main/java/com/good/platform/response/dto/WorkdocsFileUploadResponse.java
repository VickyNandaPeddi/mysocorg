package com.good.platform.response.dto;

import lombok.Data;

@Data
public class WorkdocsFileUploadResponse {

	private String organisationName;
	private String folderId;
	private String documentId;
	private String documentName;
	private String versionId;
	private String documentUrl;
	
}
