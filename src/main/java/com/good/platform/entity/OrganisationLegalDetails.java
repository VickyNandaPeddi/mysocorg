package com.good.platform.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Entity of organisation_legal_details table.
 *
 * @author Sreeju <sreeju.u@giglabz.com>
 * @since 14 Jun 2021
 */
@ToString
@Getter
@Setter
@Entity(name = "organisation_legal_details")
public class OrganisationLegalDetails extends DocumentId {

	/**
	 * Organisation Id of the related organisation.
	 */
	
	  @ManyToOne(fetch = FetchType.LAZY, optional = false)
	  @JoinColumn(name = "organisation_id", nullable = false) 
	  private Organisation organisation;
	 

	/**
	 * Uploaded file path of Certificate Of Incorporation
	 */
	@Column(name = "certificate_of_incorporation_url")
	private String certificateOfIncorporationUrl;

	/**
	 * Uploaded file path of FCRA Approval Letter
	 */
	@Column(name = "fcra_approval_letter")
	private String fcraApprovalLetter;

	/**
	 * FCRA Approval Letter Validity Date
	 */
	@Column(name = "fcra_approval_validity", columnDefinition = "DATE")
	private LocalDate fcraApprovalValidity;

	/**
	 * Uploaded file path of Trust Deed.
	 */
	@Column(name = "trust_deed_url")
	private String trustDeedUrl;

	/**
	 * Uploaded file path of Memorandum of Association
	 */
	@Column(name = "memorandum_of_association_url")
	private String memorandumOfAssociationUrl;

	/**
	 * Uploaded file path of Articles of Association
	 */
	@Column(name = "articles_of_association")
	private String articlesOfAssociationUrl;

	/**
	 * Article of Association validity date.
	 */
	@Column(name = "articles_of_association_validity", columnDefinition = "DATE")
	private LocalDate articlesOfAssociationValidity;

	/**
	 * 80G/12A Certificate file path
	 */
	@Column(name = "url_of_80G_12A_certificate")
	private String urlOf80G12ACertificate;
	
	/**
	 * formCSR1Url is to save the file path of CSR documents
	 */
	@Column(name = "form_csr_1")
	private String formCSR1Url;
	/**
	 * Uploaded file name of Certificate Of Incorporation
	 */

	@Column(name = "certificate_of_incorporation_filename")
	private String certificateOfIncorporationFilename;
	/**
	 * Uploaded file name of Trust Deed.
	 */
	
	@Column(name = "trust_deed_filename")
	private String trustDeedFilename;
	/**
	 * Uploaded file name of Memorandum of Association
	 */
	@Column(name = "memorandum_of_association_filename")
	private String memorandumOfAssociationFilename;
	
	/**
	 * Uploaded file name of Articles of Association
	 */
	@Column(name = "articles_of_association_filename")
	private String articlesOfAssociationFilename;
	/**
	 * 80G/12A/12AA Certificate file name
	 */
	@Column(name = "url_of_80G_12A_12AA_certificate_filename")
	private String urlOf80G12A12AACertificateFilename;
	/**
	 * formCSR1Url is to save the file name
	 */
	@Column(name = "form_csr_1_filename")
	private String formCSR1Filename;
	
	@Column(name = "fcra_approval_letter_filename")
	private String fcraApprovalLetterFilename;
	
	
	@Column(name = "bye_laws_filename")
	private String byeLawsFilename;
	
	@Column(name = "bye_lawws_url")
	private String byeLawsUrl;
	
	@Column(name = "llp_deed_filename")
	private String LLPDeedFilename;
	
	@Column(name = "llp_deed_url")
	private String LLPDeedUrl;
	
	@Column(name = "partnership_deed_filename")
	private String partnershipDeedFilename;
	
	@Column(name = "partnership_deed_url")
	private String partnershipDeedUrl;
	
	@Column(name = "certificate_of_registration_filename")
	private String certificateOfRegistrationFilename;
	
	@Column(name = "certificate_of_registration_url")
	private String certificateOfRegistrationUrl;
	
	@Column(name = "share_certificate_filename")
	private String shareCertificateFilename;
	
	@Column(name = "share_certificate_url")
	private String shareCertificateUrl;
	
	@Column(name = "commencement_certificate_filename")
	private String commencementCertificateFilename;
	
	@Column(name = "commencement_certificate_url")
	private String commencementCertificateUrl;
	
	@Column(name = "license_under_section_8_filename")
	private String licenseUnderSection8Filename;
	
	@Column(name = "license_under_section_8_url")
	private String licenseUnderSection8Url;
	
	@Column(name = "bombay_shop_establishment_certificate_filename")
	private String bombayShopEstablishmentCertificateFilename;
	
	@Column(name = "bombay_shop_establishment_certificate_url")
	private String bombayShopEstablishmentCertificateUrl;
	
	@Column(name = "professional_tax_enrollment_certificate_filename")
	private String professionalTaxEnrollmentCertificateFilename;
	
	@Column(name = "professional_tax_enrollment_certificate_url")
	private String professionalTaxEnrollmentCertificateUrl;
	
	@Column(name = "professional_tax_registration_certificate_filename")
	private String professionalTaxRegistrationCertificateFilename;
	
	@Column(name = "professional_tax_registration_certificate_url")
	private String professionalTaxRegistrationCertificateUrl;
	
	@Column(name = "esi_registration_filename")
	private String ESIRegistrationFilename;
	
	@Column(name = "esi_registration_url")
	private String ESIRegistrationUrl;
	
	@Column(name = "pf_registration_filename")
	private String PFRegistrationFilename;
	
	@Column(name = "pf_registration_url")
	private String PFRegistrationUrl;
	
	@Column(name = "certificate_by_doe_filename")
	private String certificateByDoeFilename;
	
	@Column(name = "certificate_by_doe_url")
	private String certificateByDoeUrl;
	
	@Column(name = "tan_filename")
	private String TANFilename;
	
	@Column(name = "tan_url")
	private String TANUrl;
	
	@Column(name = "section_11_registration_filename")
	private String section11RegistrationFilename;
	
	@Column(name = "section_11_registration_url")
	private String section11RegistrationUrl;
	
	@Column(name = "certificate_of_affiliation_filename")
	private String certificateOfAffiliationFilename;
	
	@Column(name = "certificate_of_affiliation_url")
	private String certificateOfAffiliationUrl;
	
	@Column(name = "entity_proof_filename")
	private String entityProofFilename;
	
	@Column(name = "entity_proof_url")
	private String entityProofUrl;
	
	@Column(name = "company_application_filename")
	private String companyAppilcationFilename;
	
	@Column(name = "company_application_url")
	private String companyAppilcationUrl;
	
	@Column(name = "sebi_registration_certificate_filename")
	private String SEBIRegistrationCertificateFilename;
	
	@Column(name = "sebi_registration_certificate_url")
	private String SEBIRegistrationCertificateUrl;
	
	/* Below columns are using to save the workdocs url of the documents */
	@Column(name = "articles_of_association_work_docs_url", length = 4000)
	private String articlesOfAssociationWorkdocsUrl;
	
	@Column(name = "certificate_of_incorporation_work_docs_url", length = 4000)
	private String certificateOfIncorporationWorkdocsUrl;
	
	@Column(name = "fcra_approval_letter_work_docs_url", length = 4000)
	private String fcraApprovalLetterWorkdocsUrl;
	
	@Column(name = "form_csr_1_work_docs_url", length = 4000)
	private String formCSR1WorkdocsUrl;
	
	@Column(name = "memorandum_of_association_work_docs_url", length = 4000)
	private String memorandumOfAssociationWorkdocsUrl;
	
	@Column(name = "trust_deed_work_docs_url", length = 4000)
	private String trustDeedWorkdocsUrl;
	
	@Column(name = "work_docs_url_of_80G_12A_12AA_certificate", length = 4000)
	private String workdocsUrlOf80G12A12AACertificate;
	
	@Column(name = "esi_registration_work_docs_url", length = 4000)
	private String ESIRegistrationWorkdocsUrl;
	
	@Column(name = "llp_deeed_work_docs_url", length = 4000)
	private String LLPDeedWorkdocsUrl;
	
	@Column(name = "pf_registration_work_docs_url", length = 4000)
	private String PFRegistrationWorkdocsUrl;
	
	@Column(name = "sebi_registrationcertificate_work_docs_url", length = 4000)
	private String SEBIRegistrationCertificateWorkdocsUrl;
	
	@Column(name = "tan_work_docs_url", length = 4000)
	private String TANWorkdocsUrl;
	
	@Column(name = "bombay_shop_establishment_certificate_work_docs_url", length = 4000)
	private String bombayShopEstablishmentCertificateWorkdocsUrl;
	
	@Column(name = "bye_lawws_work_docs_url", length = 4000)
	private String byeLawsWorkdocsUrl;
	
	@Column(name = "certificate_by_doe_work_docs_url", length = 4000)
	private String certificateByDoeWorkdocsUrl;
	
	@Column(name = "certificate_of_affiliation_work_docs_url", length = 4000)
	private String certificateOfAffiliationWorkdocsUrl;
	
	@Column(name = "certificate_of_registration_work_docs_url", length = 4000)
	private String certificateOfRegistrationWorkdocsUrl;
	
	@Column(name = "commencement_certificate_work_docs_url", length = 4000)
	private String commencementCertificateWorkdocsUrl;
	
	@Column(name = "company_application_work_docs_url", length = 4000)
	private String companyAppilcationWorkdocsUrl;
	
	@Column(name = "entity_proof_work_docs_url", length = 4000)
	private String entityProofWorkdocsUrl;
	
	@Column(name = "license_under_section_8_work_docs_url", length = 4000)
	private String licenseUnderSection8WorkdocsUrl;
	
	@Column(name = "partnership_deed_work_docs_url", length = 4000)
	private String partnershipDeedWorkdocsUrl;
	
	@Column(name = "professional_tax_enrollment_certificate_work_docs_url", length = 4000)
	private String professionalTaxEnrollmentCertificateWorkdocsUrl;
	
	@Column(name = "professional_tax_registration_certificate_work_docs_url", length = 4000)
	private String professionalTaxRegistrationCertificateWorkdocsUrl;
	
	@Column(name = "section_11_registration_work_docs_url", length = 4000)
	private String section11RegistrationWorkdocsUrl;
	
	@Column(name = "share_certificate_work_docs_url", length = 4000)
	private String shareCertificateWorkdocsUrl;
	
	@Column(name = "csr_policy_file_name")
	private String CSRPolicyFileName;
	
	@Column(name = "csr_policy_url")
	private String CSRPolicyUrl;
	
	@Column(name = "csr_policy_work_docs_url", length = 4000)
	private String CSRPolicyWorkDocsUrl;
	
	@Column(name = "cin_file_name")
	private String CINFileName;
	
	@Column(name = "cin_url")
	private String CINUrl;
	
	@Column(name = "cin_work_docs_url", length = 4000)
	private String CINWorkDocsUrl;
	
	@Column(name = "llpin_file_name")
	private String LLPINFileName;
	
	@Column(name = "llpin_url")
	private String LLPINFileUrl;
	
	@Column(name = "llpin_work_docs_url", length = 4000)
	private String LLPINWorkDocsUrl;
	
	@Column(name = "register_certificate_file_name")
	private String registerCertificateFileName;
	
	@Column(name = "register_certificate_url")
	private String registerCertificateUrl;
	
	@Column(name = "register_certificate_work_docs_url", length = 4000)
	private String registerCertificateWorkDocsUrl;
	
	@Column(name = "rbi_file_name")
	private String RBIFileName;
	
	@Column(name = "rbi_url")
	private String RBIFileUrl;
	
	@Column(name = "rbi_register_work_docs_url", length = 4000)
	private String RBIFileWorkDocsUrl;
	
	@Column(name = "sebi_file_name")
	private String SEBIFileName;
	
	@Column(name = "sebi_file_url")
	private String SEBIFileUrl;
	
	@Column(name = "sebi_work_docs_url", length = 4000)
	private String SEBIWorkDocsUrl;
	
	@Column(name = "bank_statement_file_name")
	private String bankStatementFileName;
	
	@Column(name = "bank_statement_url")
	private String bankStatementUrl;
	
	@Column(name = "bank_statement_work_docs_url", length = 4000)
	private String bankStatementWorkDocsUrl;
	
	@Column(name = "audited_balance_sheet_file_name")
	private String auditedBalanceSheetFileName;
	
	@Column(name = "audited_balance_sheet_url")
	private String auditedBalanceSheetUrl;
	
	@Column(name = "audited_balance_sheet_work_docs_url", length = 4000)
	private String auditedBalanceSheetWorkDocsUrl;
	
	@Column(name = "statement_of_cash_file_name")
	private String statementOfCashFileName;
	
	@Column(name = "statement_of_cash_url")
	private String statementOfCashUrl;
	
	@Column(name = "statement_of_cash_work_docs_url", length = 4000)
	private String statementOfCashWorkDocsUrl;
	
	@Column(name = "statement_of_functional_expenses_file_name")
	private String StatementOfFunctionalExpensesFileName;
	
	@Column(name = "statement_of_functional_expenses_url")
	private String statementOfFunctionalExpensesUrl;
	
	@Column(name = "statement_of_functional_expenses_work_docs_url", length = 4000)
	private String statementOfFunctionalExpensesWorkDocsUrl;
	
	
}
