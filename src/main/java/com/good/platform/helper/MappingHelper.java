package com.good.platform.helper;

import com.good.platform.config.StaticContextAccessor;
import com.good.platform.entity.FinancialYears;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationBeneficiaryHistory;
import com.good.platform.entity.OrganisationFinancialDetails;
import com.good.platform.entity.OrganisationLegalDetails;
import com.good.platform.entity.Sectors;
import com.good.platform.entity.User;
import com.good.platform.entity.UserRoles;
import com.good.platform.enums.ApprovalStatus;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.SectorsRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.repository.UserRoleRepository;

public class MappingHelper {

	public static Organisation mapOrganisationEntity(String organisationId) {

		return new Organisation(organisationId);

	}

	public static OrganisationBeneficiaryHistory mapOrganisationBeneficiaryHistoryEntity(String beneficiaryHistoryId) {

		return new OrganisationBeneficiaryHistory(beneficiaryHistoryId);
	}

	public static  FinancialYears mapFinancialYearsEntity(String financialYearsId) {

		return new  FinancialYears(financialYearsId);
	}
	
	public static User getUserObject(String userId) {
		return StaticContextAccessor.getBean(UserRepository.class).findById(userId).get();
	}
	
	public static UserRoles getUserRoleObject(String userRoleId) {
		return StaticContextAccessor.getBean(UserRoleRepository.class).findById(userRoleId).get();
	}

	public static Organisation getOrganisationObject(String organisationId) {
		return StaticContextAccessor.getBean(OrganisationRepository.class).findById(organisationId).get();
	}
	public static Sectors mapSectors(Long sectorsId) {

		if (sectorsId != null) {
			Sectors response = StaticContextAccessor.getBean(SectorsRepository.class).findById(sectorsId).get();
			return response;
		} else {
			return null;
		}
	}
	public static String mapSectorsName(Long sectorsId) {
		if (sectorsId != null) {
			Sectors response = StaticContextAccessor.getBean(SectorsRepository.class).findById(sectorsId).get();
			response.getTitle();
			return response.getTitle();
		} else {
			return null;
		}
		
	}
	
	public static Long mapSectorsId(Long sectorsId) {
		if (sectorsId != null) {
		
			return sectorsId;
		} else {
			return null;
		}
		
	}
	
	public static String getChequeUrlBasedOnApprovalStatus(String approvalStatus, OrganisationFinancialDetails finance) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return finance.getCancelledChequeWorkdocsUrl();
		}else {
			return finance.getCancelledChequeUrl();
		}
	}
	
	public static String getPanCardUrlBasedOnApprovalStatus(String approvalStatus, OrganisationFinancialDetails finance) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return finance.getCompanyPanCardWorkdocsUrl();
		}else {
			return finance.getCompanyPanCardUrl();
		}
	}
	
	public static String getCertificationOfIncorporationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCertificateOfIncorporationWorkdocsUrl();
		}else {
			return legal.getCertificateOfIncorporationUrl();
		}
	}
	
	public static String getTrustDeedUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getTrustDeedWorkdocsUrl();
		}else {
			return legal.getTrustDeedUrl();
		}
	}
	
	public static String getMemorandumOfAssociationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getMemorandumOfAssociationWorkdocsUrl();
		}else {
			return legal.getMemorandumOfAssociationUrl();
		}
	}
	
	public static String getArticlesOfAssociationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getArticlesOfAssociationWorkdocsUrl();
		}else {
			return legal.getArticlesOfAssociationUrl();
		}
	}
	
	public static String getFormCSR1UrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getFormCSR1WorkdocsUrl();
		}else {
			return legal.getFormCSR1Url();
		}
	}
	
	public static String getByeLawsUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getByeLawsWorkdocsUrl();
		}else {
			return legal.getByeLawsUrl();
		}
	}
	
	public static String getLLPDeedUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getLLPDeedWorkdocsUrl();
		}else {
			return legal.getLLPDeedUrl();
		}
	}
	
	public static String getPartnershipDeedUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getPartnershipDeedWorkdocsUrl();
		}else {
			return legal.getPartnershipDeedUrl();
		}
	}
	
	public static String getCertificateOfRegistrationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCertificateOfRegistrationWorkdocsUrl();
		}else {
			return legal.getCertificateOfRegistrationUrl();
		}
	}
	
	public static String getShareCertificateUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getShareCertificateWorkdocsUrl();
		}else {
			return legal.getShareCertificateUrl();
		}
	}
	
	public static String getCommencementCertificateUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCommencementCertificateWorkdocsUrl();
		}else {
			return legal.getCommencementCertificateUrl();
		}
	}
	
	public static String getLicenseUnderSection8UrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getLicenseUnderSection8WorkdocsUrl();
		}else {
			return legal.getLicenseUnderSection8Url();
		}
	}
	
	public static String getBombayShopEstablishmentCertificateUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getBombayShopEstablishmentCertificateWorkdocsUrl();
		}else {
			return legal.getBombayShopEstablishmentCertificateUrl();
		}
	}
	
	public static String getProfessionalTaxEnrollmentCertificateUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getProfessionalTaxEnrollmentCertificateWorkdocsUrl();
		}else {
			return legal.getProfessionalTaxEnrollmentCertificateUrl();
		}
	}
	
	public static String getProfessionalTaxRegistrationCertificateUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getProfessionalTaxRegistrationCertificateWorkdocsUrl();
		}else {
			return legal.getProfessionalTaxRegistrationCertificateUrl();
		}
	}
	
	public static String getESIRegistrationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getESIRegistrationWorkdocsUrl();
		}else {
			return legal.getESIRegistrationUrl();
		}
	}
	
	public static String getPFRegistrationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getPFRegistrationWorkdocsUrl();
		}else {
			return legal.getPFRegistrationUrl();
		}
	}
	
	public static String getCertificateByDoeUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCertificateByDoeWorkdocsUrl();
		}else {
			return legal.getCertificateByDoeUrl();
		}
	}
	
	public static String getTANUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getTANWorkdocsUrl();
		}else {
			return legal.getTANUrl();
		}
	}
	
	public static String getSection11RegistrationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getSection11RegistrationWorkdocsUrl();
		}else {
			return legal.getSection11RegistrationUrl();
		}
	}
	
	public static String getCertificateOfAffiliationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCertificateOfAffiliationWorkdocsUrl();
		}else {
			return legal.getCertificateOfAffiliationUrl();
		}
	}
	
	public static String getEntityProofUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getEntityProofWorkdocsUrl();
		}else {
			return legal.getEntityProofUrl();
		}
	}
	
	public static String getCompanyAppilcationUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCompanyAppilcationWorkdocsUrl();
		}else {
			return legal.getCompanyAppilcationUrl();
		}
	}
	
	public static String getSEBIRegistrationCertificateUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getSEBIRegistrationCertificateWorkdocsUrl();
		}else {
			return legal.getSEBIRegistrationCertificateUrl();
		}
	}
	
	public static String getFcraApprovalLetterUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getFcraApprovalLetterWorkdocsUrl();
		}else {
			return legal.getFcraApprovalLetter();
		}
	}
	
	public static String getUrlOf80g12a12aaCertificateBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getWorkdocsUrlOf80G12A12AACertificate();
		}else {
			return legal.getUrlOf80G12ACertificate();
		}
	}
	
	public static String getCSRPolicyUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCSRPolicyWorkDocsUrl();
		}else {
			return legal.getCSRPolicyUrl();
		}
	}
	
	public static String getCINUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getCINWorkDocsUrl();
		}else {
			return legal.getCINUrl();
		}
	}
	
	public static String getLLPINFileUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getLLPINWorkDocsUrl();
		}else {
			return legal.getLLPINFileUrl();
		}
	}
	
	public static String getRegisterCertificateUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getRegisterCertificateWorkDocsUrl();
		}else {
			return legal.getRegisterCertificateUrl();
		}
	}
	
	public static String getRBIFileUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getRBIFileWorkDocsUrl();
		}else {
			return legal.getRBIFileUrl();
		}
	}
	
	public static String getSEBIFileUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getSEBIWorkDocsUrl();
		}else {
			return legal.getSEBIFileUrl();
		}
	}
	
	public static String getBankStatementUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getBankStatementWorkDocsUrl();
		}else {
			return legal.getBankStatementUrl();
		}
	}
	
	public static String getAuditedBalanceSheetUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getAuditedBalanceSheetWorkDocsUrl();
		}else {
			return legal.getAuditedBalanceSheetUrl();
		}
	}
	
	public static String getStatementOfCashUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getStatementOfCashWorkDocsUrl();
		}else {
			return legal.getStatementOfCashUrl();
		}
	}
	
	public static String getStatementOfFunctionalExpensesUrlBasedOnApprovalStatus(String approvalStatus, OrganisationLegalDetails legal) {
		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
			return legal.getStatementOfFunctionalExpensesWorkDocsUrl();
		}else {
			return legal.getStatementOfFunctionalExpensesUrl();
		}
	}
	
//	public static String getAuditedStatementsUrlBasedOnApprovalStatus(String approvalStatus, LastAuditedStatements lastAudit) {
//		if(approvalStatus.equals(ApprovalStatus.APPROVED.toString())) {
//			return lastAudit.getAuditedStatementWorkdocsUrl();
//		}else {
//			return lastAudit.getAuditedStatementUrl();
//		}
//	}
	
}
