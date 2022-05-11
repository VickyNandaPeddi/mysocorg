/**
 * 
 */
package com.good.platform.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * Handle all the functionalities related to the documents in the system
 * 
 * @author Anjana
 */
public interface DocumentService {

	/**
	 * uploadDocsFromS3ToWorkDocs is to get all the documents related to an
	 * organisation from s3 bucket to workdocs while the organisation is getting
	 * approved
	 * 
	 * @param organisationId     Id of the Organisation
	 */
	public String uploadDocsFromS3ToWorkDocs(String organisationId);

	/**
	 * getDocsFromWorkDocs is to download the documents by passing the document id
	 * and version id
	 * 
	 * @param pass the data in the form of documentId:versionId
	 */
	public ResponseEntity<Resource> getDocsFromWorkDocs(String documentData);
	
}
