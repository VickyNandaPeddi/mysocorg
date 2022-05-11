package com.good.platform.request.dto.put;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationLegalDetailsUpdateRequest {

	private String id;
	
	/**
	 * Organisation Id of the related organisation.
	 */

	private String organisationId;

	/**
	 * Uploaded file path of Certificate Of Incorporation
	 */

	private String certificateOfIncorporationUrl;

	/**
	 * Uploaded file path of FCRA Approval Letter
	 */

	private String fcraApprovalLetter;

	/**
	 * FCRA Approval Letter Validity Date
	 */

	private LocalDate fcraApprovalValidity;

	/**
	 * Uploaded file path of Trust Deed.
	 */
	
	private String trustDeedUrl;

	/**
	 * Uploaded file path of Memorandum of Association
	 */

	private String memorandumOfAssociationUrl;

	/**
	 * Uploaded file path of Articles of Association
	 */

	private String articlesOfAssociationUrl;

	private LocalDate articlesOfAssociationValidity;

	/**
	 * 80G/12A/12AA Certificate file path
	 */

	private String urlOf80G12ACertificate;
	
	
	/**
	 * formCSR1Url is to save the file path of the CSR document
	 */
	private String formCSR1Url;
	
	private String certificateOfIncorporationFilename;
	/**
	 * Uploaded file name of Trust Deed.
	 */
	
	
	private String trustDeedFilename;
	/**
	 * Uploaded file name of Memorandum of Association
	 */

	private String memorandumOfAssociationFilename;
	
	/**
	 * Uploaded file name of Articles of Association
	 */
	
	private String articlesOfAssociationFilename;
	/**
	 * 80G/12A/12AA Certificate file name
	 */
	
	private String urlOf80G12A12AACertificateFilename;
	/**
	 * formCSR1Url is to save the file name
	 */

	private String formCSR1Filename;
	
	private String fcraApprovalLetterFilename;
	
	//added 27/12/2021
	private String byeLawsFilename;

	private String byeLawsUrl;

	private String LLPDeedFilename;

	private String LLPDeedUrl;

	private String partnershipDeedFilename;

	private String partnershipDeedUrl;

	private String certificateOfRegistrationFilename;

	private String certificateOfRegistrationUrl;

	private String shareCertificateFilename;

	private String shareCertificateUrl;

	private String commencementCertificateFilename;

	private String commencementCertificateUrl;

	private String licenseUnderSection8Filename;

	private String licenseUnderSection8Url;

	private String bombayShopEstablishmentCertificateFilename;

	private String bombayShopEstablishmentCertificateUrl;

	private String professionalTaxEnrollmentCertificateFilename;

	private String professionalTaxEnrollmentCertificateUrl;

	private String professionalTaxRegistrationCertificateFilename;

	private String professionalTaxRegistrationCertificateUrl;

	private String ESIRegistrationFilename;

	private String ESIRegistrationUrl;

	private String PFRegistrationFilename;

	private String PFRegistrationUrl;

	private String certificateByDoeFilename;

	private String certificateByDoeUrl;

	private String TANFilename;

	private String TANUrl;

	private String section11RegistrationFilename;

	private String section11RegistrationUrl;

	private String certificateOfAffiliationFilename;

	private String certificateOfAffiliationUrl;

	private String entityProofFilename;

	private String entityProofUrl;

	private String companyAppilcationFilename;

	private String companyAppilcationUrl;

	private String SEBIRegistrationCertificateFilename;

	private String SEBIRegistrationCertificateUrl;

}
