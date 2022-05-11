package com.good.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.service.DocumentService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * DocumentController is to route functionality requests related to the
 * documents in the system
 * 
 * @author Anjana Rajan
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/document")
@Slf4j
public class DocumentController {

	@Autowired
	DocumentService documentService;

	/**
	 * uploadDocsFromS3ToWorkDocs is to get all the documents related to an
	 * organisation from s3 bucket to workdocs while the organisation is getting
	 * approved
	 * 
	 * @param organisationId Id of the Organisation
	 * @throws Exception
	 */
	@PostMapping("/{organisationId}")
	public GoodPlatformResponseVO<String> uploadDocsFromS3ToWorkDocs(@PathVariable("organisationId") String organisationId)
			throws Exception {
		String response = documentService.uploadDocsFromS3ToWorkDocs(organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<String>(), response,
				Constants.WORKDOC_UPLOAD_SUCCESS, Constants.WORKDOC_UPLOAD_FAIL);

	}

	/**
	 * getDocsFromWorkDocs is to download the documents by passing the document id
	 * and version id
	 * 
	 * @param pass the data in the form of documentId:versionId
	 * @throws Exception
	 */
	@GetMapping("/{documentData}")
	public ResponseEntity<Resource> getDocsFromWorkDocs(@PathVariable String documentData) throws Exception {
		ResponseEntity<Resource> response = documentService.getDocsFromWorkDocs(documentData);
		return response;

	}

}
