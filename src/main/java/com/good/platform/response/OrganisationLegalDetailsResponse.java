package com.good.platform.response;

import java.time.LocalDate;

import com.good.platform.response.dto.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationLegalDetailsResponse extends BaseResponse{

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
	 * 80G/12A Certificate file path
	 */

	private String urlOf80G12ACertificate;
	
	private String formCSR1Url;
	
	private String certificateOfIncorporationFilename;
	
	

	private String trustDeedFilename;
	
	private String memorandumOfAssociationFilename;
	
	
	private String articlesOfAssociationFilename;

	private String urlOf80G12A12AACertificateFilename;
	
	private String formCSR1Filename;
	private String fcraApprovalLetterFilename;
	
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
	
	private String CSRPolicyFileName;
	private String CSRPolicyUrl;
	
	private String CINFileName;
	private String CINUrl;
	
	private String LLPINFileName;
	private String LLPINFileUrl;
	
	private String registerCertificateFileName;
	private String registerCertificateUrl;
	
	private String RBIFileName;
	private String RBIFileUrl;
	
	private String SEBIFileName;
	private String SEBIFileUrl;
	
	private String bankStatementFileName;
	private String bankStatementUrl;
	
	private String auditedBalanceSheetFileName;
	private String auditedBalanceSheetUrl;
	
	private String statementOfCashFileName;
	private String statementOfCashUrl;
	
	private String statementOfFunctionalExpensesFileName;
	private String statementOfFunctionalExpensesUrl;

}