/**
 * 
 */
package com.good.platform.service.impl;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.good.platform.client.fileupload.FileUploadClient;
import com.good.platform.entity.LastAuditedStatements;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationDMSDetails;
import com.good.platform.entity.OrganisationFinancialDetails;
import com.good.platform.entity.OrganisationLegalDetails;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.model.LastAuditStatementsModel;
import com.good.platform.repository.DocumentRepository;
import com.good.platform.repository.LastAuditedStatementsRepository;
import com.good.platform.repository.OrganisationFinancialDetailsRepository;
import com.good.platform.repository.OrganisationLegalDetailsRepository;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.request.WorkdocsFileUploadRequest;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.OrganisationDocumentsResponse;
import com.good.platform.response.dto.WorkdocsFileUploadResponse;
import com.good.platform.service.DocumentService;
import com.good.platform.utility.Constants;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Handle all the functionalities related to the documents in the system
 * 
 * @author Anjana
 */
@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	FileUploadClient fileUploadClient;
	@Autowired
	OrganisationLegalDetailsRepository organisationLegalDetailsRepository;
	@Autowired
	OrganisationFinancialDetailsRepository organisationFinancialDetailsRepository;
	@Autowired
	LastAuditedStatementsRepository lastAuditedStatementsRepo;
	@Autowired
	OrganisationRepository organisationRepo;

	OrganisationLegalDetails organisationLegalDetails = null;
	OrganisationFinancialDetails organisationFinancialDetails = null;

	/**
	 * uploadDocsFromS3ToWorkDocs is to get all the documents related to an
	 * organisation from s3 bucket to workdocs while the organisation is getting
	 * approved
	 * 
	 * @param organisationId Id of the Organisation
	 * @throws Exception
	 */
	@Override
	public String uploadDocsFromS3ToWorkDocs(String organisationId) {
		log.debug("upload process triggered");
		if(StringUtils.isEmpty(organisationId)) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		GoodPlatformResponseVO<WorkdocsFileUploadResponse> workdocsFileUploadResponse = new GoodPlatformResponseVO<WorkdocsFileUploadResponse>();
		List<WorkdocsFileUploadResponse> listOfResponse = new ArrayList<>();
		String folderId = "";
		boolean folderExistFl = false;
		boolean documentExistFl = false;
		boolean documentExistFlAudit = false;
		try {
			Optional<Organisation> organisationData = organisationRepo.findById(organisationId);
			if(organisationData.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORGANISATION_DATA_NOT_FOUND);
			}
			OrganisationDocumentsResponse documentsResponse = documentRepository.getOrganisationDocuments(organisationId);
			OrganisationDMSDetails organisationDMSDetails = documentRepository.findByOrganisationId(organisationId);
			Class clazz = documentsResponse.getClass();
			String organisationName = documentsResponse.getOrganisationName();
			if (organisationDMSDetails != null) {
				folderId = organisationDMSDetails.getWorkdocsOrgFolderId();
				folderExistFl = true;
			}
			/*
			 * Get all the getter methods from the class to get the file name to move from
			 * s3 bucket to workdocs. Exlucde getClass and getOrganisationName methods since
			 * those are not having any file names to be stored to workdocs. The value after
			 * the last '/' of s3 URL contains the file name. One folder will be created in
			 * the workdocs for one organisation and all the documents for that organisation
			 * will be saved there
			 */
			for (PropertyDescriptor propertyDescriptior : Introspector.getBeanInfo(OrganisationDocumentsResponse.class)
					.getPropertyDescriptors()) {
				String methodName = propertyDescriptior.getReadMethod().getName();
				String documentName = "";
				if (!methodName.equals("getClass") && !methodName.equals("getOrganisationName") && !methodName.equals("getAuditedStatementUrl")) {
					documentName = (String) clazz.getDeclaredMethod(methodName).invoke(documentsResponse);
					if (documentName != null) {
						documentExistFl = true;
						documentName = documentName.substring(documentName.lastIndexOf('/') + 1, documentName.length());
						workdocsFileUploadResponse = fileUploadClient.uploadFileToWorkDocsFromS3(
								new WorkdocsFileUploadRequest(organisationName, folderId, documentName, null));
						createWorkDocsUrlData(workdocsFileUploadResponse, organisationId, methodName);
						if (folderId.equals("")) {
							folderId = workdocsFileUploadResponse.getData().getFolderId();
						}
					}
				}else if(methodName.equals("getAuditedStatementUrl")) {
					Class lastAuditClass = LastAuditStatementsModel.class;
					List<LastAuditStatementsModel> auditedStatementsList = lastAuditedStatementsRepo.
							getLastAuditStatementsDetailsByOrganisationId(organisationId);
					for(LastAuditStatementsModel statement : auditedStatementsList) {
						documentName = (String) lastAuditClass.getDeclaredMethod(methodName).invoke(statement);
						if(documentName != null) {
							documentExistFlAudit = true;
							documentName = documentName.substring(documentName.lastIndexOf('/') + 1, documentName.length());
							if(Objects.isNull(workdocsFileUploadResponse) ||Objects.isNull( workdocsFileUploadResponse.getData()) ||
									StringUtils.isEmpty( workdocsFileUploadResponse.getData().getFolderId()	)) {
								folderId = "";
							}else {
								folderId = workdocsFileUploadResponse.getData().getFolderId();
							}
							workdocsFileUploadResponse = fileUploadClient.uploadFileToWorkDocsFromS3(
									new WorkdocsFileUploadRequest(organisationName, folderId, documentName, null));
							listOfResponse.add(workdocsFileUploadResponse.getData());
						}
					}
					if(documentExistFlAudit) {
						createWorkDocsUrlDataForLastAuditStatements(listOfResponse, organisationData.get(), methodName,auditedStatementsList,
								organisationDMSDetails, folderId);
						if (folderId.equals("")) {
							folderId = workdocsFileUploadResponse.getData().getFolderId();
						}
					}
				}
			}
			if (documentExistFl)
				saveWorkDocsDetails(folderId, organisationDMSDetails, folderExistFl);
			log.debug("upload process completed");
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | SOException exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.SAVING_WORK_DOC_DETAILS_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SAVING_WORK_DOC_DETAILS_FAIL);
			} else if (Constants.SAVING_WORK_DOC_DETAILS_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SAVING_WORK_DOC_DETAILS_FAIL);
			} else if (Constants.SAVING_WORK_DOC_DETAILS_FAIL.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SAVING_WORK_DOC_DETAILS_FAIL);
			}else if (Constants.ORGANISATION_DATA_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORGANISATION_DATA_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.WORKDOC_UPLOAD_FAIL);
			}
		}

		return "Documents uploaded Successfully";
	}

	/**
	 * saveWorkDocsDetails is to save the workdocs folder id and document url after
	 * upload
	 * 
	 * @param folderId               Id created in workdocs for the organisation
	 * @param organisationDMSDetails existing DMS details available for the document
	 * @param folderExistFl          if the folder is already existing in DMS or not
	 */
	private void saveWorkDocsDetails(String folderId, OrganisationDMSDetails organisationDMSDetails,
			Boolean folderExistFl) {
		log.debug("Saving work docs data started");
		try {
			if (!folderExistFl) {
				Organisation organisation = organisationLegalDetails != null
						? organisationLegalDetails.getOrganisation()
						: organisationFinancialDetails.getOrganisation();
				organisationDMSDetails = getOrganisationDMSDetails(organisation.getId());
				organisationDMSDetails.setOrganisation(organisation);
				organisationDMSDetails.setWorkdocsOrgFolderId(folderId);
				documentRepository.save(organisationDMSDetails);
			}
			if (organisationLegalDetails != null)
				organisationLegalDetailsRepository.save(organisationLegalDetails);
			if (organisationFinancialDetails != null)
				organisationFinancialDetailsRepository.save(organisationFinancialDetails);
			log.debug("Saving work docs data ends");
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SAVING_WORK_DOC_DETAILS_FAIL);
		}
	}
	
	/**
	 * OrganisationDMSDetails is to fetch the details of dms details
	 * @param organisationId
	 * @return
	 */
	private OrganisationDMSDetails getOrganisationDMSDetails(String organisationId) {
		try {
			OrganisationDMSDetails organisationDMSDetails = documentRepository.findByOrganisationId(organisationId);
			if(!Objects.isNull(organisationDMSDetails)) {
				return organisationDMSDetails;
			}else {
				return new OrganisationDMSDetails();
			}
		}catch(Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FETCH_ORG_DMS_DETAILS_FAILED);
		}
	}

	/**
	 * createWorkDocsUrlData set the url of the documents uploaded to workdocs
	 * 
	 * @param workdocsFileUploadResponse response getting after the document are
	 *                                   uploaded to workdocs
	 * @param organisationId             id of the organisation
	 * @param methodName                 getter method name to change it to setter
	 *                                   method and set the data dynamically
	 */
	private void createWorkDocsUrlData(GoodPlatformResponseVO<WorkdocsFileUploadResponse> workdocsFileUploadResponse,
			String organisationId, String methodName) {
		log.debug("Creating work docs data for saving");
		String workDocsUrl = workdocsFileUploadResponse.getData().getDocumentId() + ":"
				+ workdocsFileUploadResponse.getData().getVersionId();
		organisationLegalDetails = organisationLegalDetailsRepository.findByOrganisationId(organisationId);
		organisationFinancialDetails = organisationFinancialDetailsRepository.findByOrganisationId(organisationId);
		methodName = methodName.replaceFirst("g", "s");
		if (methodName.indexOf("Url") != 0) {
			methodName = methodName.replace("Url", "WorkdocsUrl");
		} else {
			methodName = methodName + "WorkdocsUrl";
		}

		try {
			if (organisationLegalDetails != null) {
				Method legalMethod = OrganisationLegalDetails.class.getDeclaredMethod(methodName, String.class);
				legalMethod.invoke(organisationLegalDetails, workDocsUrl);
			} else {
				throw new NoSuchMethodException("NoSuchMethodException");
			}
		} catch (NoSuchMethodException exception) {
			try {
				if (organisationFinancialDetails != null) {
					Method finMethod = OrganisationFinancialDetails.class.getDeclaredMethod(methodName, String.class);
					finMethod.invoke(organisationFinancialDetails, workDocsUrl);
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException exception1) {
				log.error(ExceptionUtils.getStackTrace(exception1));
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SAVING_WORK_DOC_DETAILS_FAIL);
			}

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SAVING_WORK_DOC_DETAILS_FAIL);
		}

	}
	
	/**
	 * createWorkDocsUrlDataForLastAuditStatements, It is implemented specially for the last audit statements
	 * as it is having multiple data
	 * @param workdocsFileUploadResponse
	 * @param organisationData
	 * @param method
	 * @param auditedStatementsList
	 * @param organisationDMSDetails
	 * @param folderId
	 */
	private void createWorkDocsUrlDataForLastAuditStatements(
			List<WorkdocsFileUploadResponse> workdocsFileUploadResponse,
			Organisation organisationData, String method, List<LastAuditStatementsModel> auditedStatementsList,
			OrganisationDMSDetails organisationDMSDetails, String folderId) {
		String workDocUrl = "";
		int count = 0;
		List<LastAuditedStatements> updateList = new ArrayList<>();
		try {
			organisationDMSDetails = getOrganisationDMSDetails(organisationData.getId());
			organisationDMSDetails.setOrganisation(organisationData);
			organisationDMSDetails.setWorkdocsOrgFolderId(folderId);
			documentRepository.save(organisationDMSDetails);
			
			for(LastAuditStatementsModel lastAuditStatementsModel : auditedStatementsList) {
				workDocUrl = workdocsFileUploadResponse.get(count).getDocumentId() + ":" +
						workdocsFileUploadResponse.get(count).getVersionId() ;
				Optional<LastAuditedStatements> lastAuditedStatements = lastAuditedStatementsRepo.findById(lastAuditStatementsModel.getId());
				lastAuditedStatements.get().setAuditedStatementWorkdocsUrl(workDocUrl);
				updateList.add(lastAuditedStatements.get());
				count++;
			}
			lastAuditedStatementsRepo.saveAll(updateList);
		}catch(Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SAVING_WORK_DOC_DETAILS_FAIL);
		}
	}

	/**
	 * getDocsFromWorkDocs is to download the documents by passing the document id
	 * and version id
	 * 
	 * @param pass the data in the form of documentId:versionId
	 */
	@Override
	public ResponseEntity<Resource> getDocsFromWorkDocs(String documentData) {
		ResponseEntity<Resource> resource = fileUploadClient.getFileContentFromWorkDocs(documentData);
		return resource;
	}
}
