package com.good.platform.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkdocsFileUploadRequest {

	private String organisationName;
	private String folderId;
	private String documentName;
	private String versionId;
	
}
