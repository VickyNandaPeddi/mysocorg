package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.OrganisationDMSDetails;
import com.good.platform.response.dto.OrganisationDocumentsResponse;

@Repository
public interface DocumentRepository extends JpaRepository<OrganisationDMSDetails, String> {
	
	OrganisationDMSDetails findByOrganisationId(String organisationId);

	@Query("select new com.good.platform.response.dto.OrganisationDocumentsResponse(org.name, legaldtls.certificateOfIncorporationUrl, "
			+ "legaldtls.fcraApprovalLetter, legaldtls.trustDeedUrl, legaldtls.memorandumOfAssociationUrl, legaldtls.articlesOfAssociationUrl, "
			+ "legaldtls.urlOf80G12A12AACertificate, legaldtls.formCSR1Url, "
			+ "legaldtls.byeLawsUrl, legaldtls.LLPDeedUrl, legaldtls.partnershipDeedUrl, legaldtls.certificateOfRegistrationUrl, "
			+ "legaldtls.shareCertificateUrl, legaldtls.commencementCertificateUrl, legaldtls.licenseUnderSection8Url, "
			+ "legaldtls.bombayShopEstablishmentCertificateUrl, "
			+ "legaldtls.professionalTaxEnrollmentCertificateUrl, legaldtls.professionalTaxRegistrationCertificateUrl, legaldtls.ESIRegistrationUrl, "
			+ "legaldtls.PFRegistrationUrl, legaldtls.certificateByDoeUrl, legaldtls.TANUrl, legaldtls.section11RegistrationUrl, "
			+ "legaldtls.certificateOfAffiliationUrl, legaldtls.entityProofUrl, legaldtls.companyAppilcationUrl, "
			+ "legaldtls.SEBIRegistrationCertificateUrl, financialdtls.cancelledChequeUrl, financialdtls.companyPanCardUrl) "
			+ "from com.good.platform.entity.Organisation org "
			+ "left join com.good.platform.entity.OrganisationLegalDetails legaldtls on org.id = legaldtls.organisation.id "
			+ " left join com.good.platform.entity.OrganisationFinancialDetails financialdtls on org.id = financialdtls.organisation.id "
			+ "where org.id = ?1 ")
	OrganisationDocumentsResponse getOrganisationDocuments(String organisationId);
	
}
