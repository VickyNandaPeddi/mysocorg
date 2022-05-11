package com.good.platform.response.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDocumentsResponse {
	
	public OrganisationDocumentsResponse(String organisationName, String certificateOfIncorporationUrl,
			String fcraApprovalLetterUrl, String trustDeedUrl, String memorandumOfAssociationUrl,
			String articlesOfAssociationUrl, String urlOf80G12A12AACertificate, String formCSR1Url, String byeLawsUrl,
			String lLPDeedUrl, String partnershipDeedUrl, String certificateOfRegistrationUrl,
			String shareCertificateUrl, String commencementCertificateUrl, String licenseUnderSection8Url,
			String bombayShopEstablishmentCertificateUrl, String professionalTaxEnrollmentCertificateUrl,
			String professionalTaxRegistrationCertificateUrl, String eSIRegistrationUrl, String pFRegistrationUrl,
			String certificateByDoeUrl, String tANUrl, String section11RegistrationUrl,
			String certificateOfAffiliationUrl, String entityProofUrl, String companyAppilcationUrl,
			String sEBIRegistrationCertificateUrl, String cancelledChequeUrl, String companyPanCardUrl) {
		this.organisationName = organisationName;
		this.certificateOfIncorporationUrl = certificateOfIncorporationUrl;
		this.fcraApprovalLetterUrl = fcraApprovalLetterUrl;
		this.trustDeedUrl = trustDeedUrl;
		this.memorandumOfAssociationUrl = memorandumOfAssociationUrl;
		this.articlesOfAssociationUrl = articlesOfAssociationUrl;
		this.urlOf80G12A12AACertificate = urlOf80G12A12AACertificate;
		this.formCSR1Url = formCSR1Url;
		this.byeLawsUrl = byeLawsUrl;
		this.LLPDeedUrl = lLPDeedUrl;
		this.partnershipDeedUrl = partnershipDeedUrl;
		this.certificateOfRegistrationUrl = certificateOfRegistrationUrl;
		this.shareCertificateUrl = shareCertificateUrl;
		this.commencementCertificateUrl = commencementCertificateUrl;
		this.licenseUnderSection8Url = licenseUnderSection8Url;
		this.bombayShopEstablishmentCertificateUrl = bombayShopEstablishmentCertificateUrl;
		this.professionalTaxEnrollmentCertificateUrl = professionalTaxEnrollmentCertificateUrl;
		this.professionalTaxRegistrationCertificateUrl = professionalTaxRegistrationCertificateUrl;
		this.ESIRegistrationUrl = eSIRegistrationUrl;
		this.PFRegistrationUrl = pFRegistrationUrl;
		this.certificateByDoeUrl = certificateByDoeUrl;
		this.TANUrl = tANUrl;
		this.section11RegistrationUrl = section11RegistrationUrl;
		this.certificateOfAffiliationUrl = certificateOfAffiliationUrl;
		this.entityProofUrl = entityProofUrl;
		this.companyAppilcationUrl = companyAppilcationUrl;
		this.SEBIRegistrationCertificateUrl = sEBIRegistrationCertificateUrl;
		this.cancelledChequeUrl = cancelledChequeUrl;
		this.companyPanCardUrl = companyPanCardUrl;
	}

	private String organisationName;
	private String certificateOfIncorporationUrl;
	private String fcraApprovalLetterUrl;
	private String trustDeedUrl;
	private String memorandumOfAssociationUrl;
	private String articlesOfAssociationUrl;
	private String urlOf80G12A12AACertificate;
	private String formCSR1Url;
	private String byeLawsUrl;
	private String LLPDeedUrl;
	private String partnershipDeedUrl;
	private String certificateOfRegistrationUrl;
	private String shareCertificateUrl;
	private String commencementCertificateUrl;
	private String licenseUnderSection8Url;
	private String bombayShopEstablishmentCertificateUrl;
	private String professionalTaxEnrollmentCertificateUrl;
	private String professionalTaxRegistrationCertificateUrl;
	private String ESIRegistrationUrl;
	private String PFRegistrationUrl;
	private String certificateByDoeUrl;
	private String TANUrl;
	private String section11RegistrationUrl;
	private String certificateOfAffiliationUrl;
	private String entityProofUrl;
	private String companyAppilcationUrl;
	private String SEBIRegistrationCertificateUrl;
	private String cancelledChequeUrl;
	private String companyPanCardUrl;
	private List<String> auditedStatementUrl;
}
