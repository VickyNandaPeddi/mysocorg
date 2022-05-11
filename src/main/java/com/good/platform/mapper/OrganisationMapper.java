package com.good.platform.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.BeneficiaryGmapLocations;
import com.good.platform.entity.BeneficiaryImpactedHistory;
import com.good.platform.entity.BeneficiaryLocations;
import com.good.platform.entity.FinancialYears;
import com.good.platform.entity.LastAuditedStatements;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationBeneficiaryHistory;
import com.good.platform.entity.OrganisationBeneficiaryTargets;
import com.good.platform.entity.OrganisationFinancialDetails;
import com.good.platform.entity.OrganisationLegalDetails;
import com.good.platform.enums.BeneficiaryTargetCategory;
import com.good.platform.helper.MappingHelper;
import com.good.platform.request.dto.BeneficiaryGmapLocationsRequest;
import com.good.platform.request.dto.BeneficiaryImpactedHistoryRequest;
import com.good.platform.request.dto.LastAuditedStatementsRequest;
import com.good.platform.request.dto.OrganisationBeneficiaryHistoryRequest;
import com.good.platform.request.dto.OrganisationFinancialDetailsRequest;
import com.good.platform.request.dto.OrganisationLegalDetailsRequest;
import com.good.platform.request.dto.put.BeneficiaryImpactedHistoryUpdateRequest;
import com.good.platform.request.dto.put.BeneficiaryTargetCategoryUpdateRequest;
import com.good.platform.request.dto.put.LastAuditedStatementsUpdateRequest;
import com.good.platform.request.dto.put.OrganisationBeneficiaryHistoryUpdateRequest;
import com.good.platform.request.dto.put.OrganisationFinancialDetailsUpdateRequest;
import com.good.platform.request.dto.put.OrganisationLegalDetailsUpdateRequest;
import com.good.platform.response.OrganisationLegalDetailsResponse;
import com.good.platform.response.dto.BeneficiaryImpactedHistoryResponse;
import com.good.platform.response.dto.LastAuditedStatementsResponse;
import com.good.platform.response.dto.OrgBasicDetailsResponse;
import com.good.platform.response.dto.OrganisationBeneficiaryHistoryResponse;
import com.good.platform.response.dto.OrganisationBeneficiaryTargetsResponse;
import com.good.platform.response.dto.OrganisationFinancialDetailsResponse;
import com.good.platform.response.dto.OrganisationSyncDto;

@Mapper(componentModel = "spring", imports = MappingHelper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationMapper {

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(source = "request.beneficiaryType", target = "beneficiaryType"),
			@Mapping(source = "request.minAge", target = "minAge"),
			@Mapping(source = "request.maxAge", target = "maxAge"),
			@Mapping(source = "request.location", target = "location"),
			@Mapping(source = "request.latitude", target = "latitude"),
			@Mapping(source = "request.longitude", target = "longitude"),
			@Mapping(source = "request.locationTitle", target = "locationTitle") })
	OrganisationBeneficiaryHistory mapOrganisationBeneficiaryHistoryRequestToOrganisationBeneficiaryHistory(
			OrganisationBeneficiaryHistoryRequest request, String organisationId, String beneficiaryHistoryId,
			@MappingTarget OrganisationBeneficiaryHistory organisationBeneficiaryHistory);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(target = "beneficiaryHistoryId", source = "beneficiaryHistoryId", qualifiedByName = "getOrganisationBeneficiaryHistory"),
			@Mapping(source = "request.totalBeneficiaryImpacted", target = "totalBeneficiaryImpacted"),
			@Mapping(source = "financialYear", target = "financialYear") })
	BeneficiaryImpactedHistory mapBeneficiaryImpactedHistoryRequestToBeneficiaryImpactedHistory(
			BeneficiaryImpactedHistoryRequest request, String beneficiaryHistoryId, String organisationId,
			String financialYear);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(target = "organisationBeneficiaryHistory", source = "beneficiaryHistoryId", qualifiedByName = "getOrganisationBeneficiaryHistory"),
			@Mapping(source = "beneficiaryTargetCategory", target = "targetCategory") })
	OrganisationBeneficiaryTargets mapBeneficiaryTargetCategoryToOrganisationBeneficiaryTargets(String organisationId,
			String beneficiaryHistoryId, String beneficiaryTargetCategory);

	@Mappings({ @Mapping(source = "beneficiaryImpactedHistory.id", target = "beneficiaryImpactedHistoryId"),
			@Mapping(source = "beneficiaryImpactedHistory.totalBeneficiaryImpacted", target = "totalBeneficiaryImpacted"),
			@Mapping(source = "financialYears", target = "financialYear") })
	BeneficiaryImpactedHistoryResponse mapBeneficiaryImpactedHistoryToBeneficiaryImpactedHistoryResponse(
			BeneficiaryImpactedHistory beneficiaryImpactedHistory, String financialYears);

	@Mappings({ @Mapping(source = "org.id", target = "organisationId"),
			@Mapping(source = "request.beneficiaryType", target = "beneficiaryType"),
			@Mapping(source = "request.minAge", target = "minAge"),
			@Mapping(source = "request.maxAge", target = "maxAge"),
			@Mapping(source = "request.location", target = "location"),
			@Mapping(source = "request.latitude", target = "latitude"),
			@Mapping(source = "request.longitude", target = "longitude"),
			@Mapping(source = "request.id", target = "id"), @Mapping(source = "request.deleted", target = "deleted"),
			@Mapping(source = "request.createdAt", target = "createdAt"),
			@Mapping(source = "request.modifiedAt", target = "modifiedAt"),
			@Mapping(source = "request.createdBy", target = "createdBy"),
			@Mapping(source = "request.lastModifiedBy", target = "lastModifiedBy"),
			@Mapping(source = "request.locationTitle", target = "locationTitle") })
	OrganisationBeneficiaryHistoryResponse mapOrganisationBeneficiaryHistoryToOrganisationBeneficiaryHistoryResponse(
			OrganisationBeneficiaryHistory request, Organisation org);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(source = "request.ifscCode", target = "ifscCode"),
			@Mapping(source = "request.accountNumber", target = "accountNumber"),
			@Mapping(source = "request.cancelledChequeUrl", target = "cancelledChequeUrl"),
			@Mapping(source = "request.branchName", target = "branchName"),
			@Mapping(source = "request.companyPanCardUrl", target = "companyPanCardUrl") })
	OrganisationFinancialDetails mapOrganisationFinancialDetailsRequestToOrganisationFinancialDetails(
			OrganisationFinancialDetailsRequest request, String organisationId);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(target = "organisationFinancialDetails", source = "financialDetailsId", qualifiedByName = "getFinancialDetails"),
			@Mapping(source = "request.auditedYear", target = "auditedYear"),
			@Mapping(source = "request.auditor", target = "auditor"),
			@Mapping(source = "request.auditedStatementUrl", target = "auditedStatementUrl") })
	LastAuditedStatements mapLastAuditedStatementsRequestToLastAuditedStatements(LastAuditedStatementsRequest request,
			String financialDetailsId, String organisationId);

	@Mappings({ @Mapping(source = "request.auditedYear", target = "auditedYear"),
			@Mapping(source = "request.auditor", target = "auditor"),
			@Mapping(source = "request.auditedStatementUrl", target = "auditedStatementUrl") })

	LastAuditedStatementsResponse mapLastAuditedStatementsToLastAuditedStatementsResponse(
			LastAuditedStatements request);

	@Mappings({ @Mapping(source = "organisationId", target = "organisationId"),
		@Mapping(source = "request.ifscCode", target = "ifscCode"),
		@Mapping(source = "request.accountNumber", target = "accountNumber"),
		@Mapping(source = "request.cancelledChequeUrl", target = "cancelledChequeUrl"),
		@Mapping(source = "request.branchName", target = "branchName"),
		@Mapping(source = "request.companyPanCardUrl", target = "companyPanCardUrl"),
		@Mapping(source = "request.id", target = "id"), @Mapping(source = "request.active", target = "active"),
		@Mapping(source = "request.deleted", target = "deleted"),
		@Mapping(source = "request.createdAt", target = "createdAt"),
		@Mapping(source = "request.modifiedAt", target = "modifiedAt"),
		@Mapping(source = "request.createdBy", target = "createdBy"),
		@Mapping(source = "request.lastModifiedBy", target = "lastModifiedBy") })
	OrganisationFinancialDetailsResponse mapOrganisationFinancialDetailsToOrganisationFinancialDetailsResponse(
		OrganisationFinancialDetails request, String organisationId);
	
	@Mappings({ @Mapping(source = "organisationId", target = "organisationId"),
			@Mapping(source = "request.ifscCode", target = "ifscCode"),
			@Mapping(source = "request.accountNumber", target = "accountNumber"),
			@Mapping(target = "cancelledChequeUrl",
			expression = "java(MappingHelper.getChequeUrlBasedOnApprovalStatus(approvalStatus,finance))"),
			@Mapping(source = "request.branchName", target = "branchName"),
			@Mapping(target = "companyPanCardUrl",
					expression = "java(MappingHelper.getPanCardUrlBasedOnApprovalStatus(approvalStatus,finance))"),
			@Mapping(source = "request.id", target = "id"), @Mapping(source = "request.active", target = "active"),
			@Mapping(source = "request.deleted", target = "deleted"),
			@Mapping(source = "request.createdAt", target = "createdAt"),
			@Mapping(source = "request.modifiedAt", target = "modifiedAt"),
			@Mapping(source = "request.createdBy", target = "createdBy"),
			@Mapping(source = "request.lastModifiedBy", target = "lastModifiedBy"),
			@Mapping(source = "request.cancelledChequeFilename", target = "cancelledChequeFilename"),
			@Mapping(source = "request.companyPanCardFilename", target = "companyPanCardFilename")})
	OrganisationFinancialDetailsResponse mapOrganisationFinancialDetailsToOrganisationFinancialDetailsFetchResponse(
			OrganisationFinancialDetails request, String organisationId, OrganisationFinancialDetails finance,
			String approvalStatus);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(source = "request.beneficiaryType", target = "beneficiaryType"),
			@Mapping(source = "request.minAge", target = "minAge"),
			@Mapping(source = "request.maxAge", target = "maxAge"),
			@Mapping(source = "request.location", target = "location"),
			@Mapping(source = "request.latitude", target = "latitude"),
			@Mapping(source = "request.longitude", target = "longitude"),
			@Mapping(source = "request.locationTitle", target = "locationTitle") })
	OrganisationBeneficiaryHistory mapOrganisationBeneficiaryHistoryUpdateRequestToOrganisationBeneficiaryHistory(
			OrganisationBeneficiaryHistoryUpdateRequest request, String organisationId, String beneficiaryHistoryId,
			@MappingTarget OrganisationBeneficiaryHistory organisationBeneficiaryHistory);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(target = "beneficiaryHistoryId", source = "beneficiaryHistoryId", qualifiedByName = "getOrganisationBeneficiaryHistory"),
			@Mapping(source = "request.totalBeneficiaryImpacted", target = "totalBeneficiaryImpacted"),
			@Mapping(source = "financialYear", target = "financialYear"),
			@Mapping(source = "request.beneficiaryImpactedHistoryId", target = "id") })
	BeneficiaryImpactedHistory mapBeneficiaryImpactedHistoryUpdateRequestToBeneficiaryImpactedHistory(
			BeneficiaryImpactedHistoryUpdateRequest request, String beneficiaryHistoryId, String organisationId,
			String financialYear);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(target = "organisationBeneficiaryHistory", source = "beneficiaryHistoryId", qualifiedByName = "getOrganisationBeneficiaryHistory"),
			@Mapping(source = "request.category", target = "targetCategory"),
			@Mapping(source = "request.id", target = "id") })
	OrganisationBeneficiaryTargets mapBeneficiaryTargetCategoryUpdateRequestToOrganisationBeneficiaryTargets(
			String organisationId, String beneficiaryHistoryId, BeneficiaryTargetCategoryUpdateRequest request);

	@Mappings({

			@Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(source = "request.ifscCode", target = "ifscCode"),
			@Mapping(source = "request.accountNumber", target = "accountNumber"),
			@Mapping(source = "request.cancelledChequeUrl", target = "cancelledChequeUrl"),
			@Mapping(source = "request.branchName", target = "branchName"),
			@Mapping(source = "request.companyPanCardUrl", target = "companyPanCardUrl") })
	OrganisationFinancialDetails mapOrganisationFinancialDetailsUpdateRequestToOrganisationFinancialDetails(
			OrganisationFinancialDetailsUpdateRequest request, String organisationId, String financialDetailsId,
			@MappingTarget OrganisationFinancialDetails finance);

	@Mappings({ @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(target = "organisationFinancialDetails", source = "financialDetailsId", qualifiedByName = "getFinancialDetails"),
			@Mapping(source = "request.auditedYear", target = "auditedYear"),
			@Mapping(source = "request.auditor", target = "auditor"),
			@Mapping(source = "request.auditedStatementUrl", target = "auditedStatementUrl"),
			@Mapping(source = "request.id", target = "id") })
	LastAuditedStatements mapLastAuditedStatementsUpdateRequestToLastAuditedStatements(
			LastAuditedStatementsUpdateRequest request, String financialDetailsId, String organisationId);

	@Named("getOrganisation")
	default Organisation mapOrganisationEntity(String organisationId) {

		return new Organisation(organisationId);

	}

	@Named("getOrganisationBeneficiaryHistory")
	default OrganisationBeneficiaryHistory mapOrganisationBeneficiaryHistoryEntity(String beneficiaryHistoryId) {

		return new OrganisationBeneficiaryHistory(beneficiaryHistoryId);
	}

	@Named("getFinancialYears")
	default FinancialYears mapFinancialYearsEntity(String financialYearsId) {

		return new FinancialYears(financialYearsId);
	}

	@Named("getFinancialDetails")
	default OrganisationFinancialDetails mapFinancialDetailsEntity(String financialDetailsId) {

		return new OrganisationFinancialDetails(financialDetailsId);
	}

	@Mappings({ @Mapping(target = "organisation", source = "organisation"),
		@Mapping(source = "request.certificateOfIncorporationUrl", target = "certificateOfIncorporationUrl"),
		@Mapping(source = "request.fcraApprovalLetter", target = "fcraApprovalLetter"),
		@Mapping(source = "request.fcraApprovalValidity", target = "fcraApprovalValidity"),
		@Mapping(source = "request.trustDeedUrl", target = "trustDeedUrl"),
		@Mapping(source = "request.memorandumOfAssociationUrl", target = "memorandumOfAssociationUrl"),
		@Mapping(source = "request.articlesOfAssociationUrl", target = "articlesOfAssociationUrl"),
		@Mapping(source = "request.articlesOfAssociationValidity", target = "articlesOfAssociationValidity"),
		@Mapping(source = "request.urlOf80G12A12AACertificate", target = "urlOf80G12A12AACertificate"),
		@Mapping(source = "request.formCSR1Url", target = "formCSR1Url"),
		@Mapping(source = "request.fcraApprovalLetterFilename", target = "fcraApprovalLetterFilename"),
		@Mapping(source = "request.byeLawsFilename", target = "byeLawsFilename"),
		@Mapping(source = "request.byeLawsUrl", target = "byeLawsUrl"),
		@Mapping(source = "request.LLPDeedFilename", target = "LLPDeedFilename"),
		@Mapping(source = "request.LLPDeedUrl", target = "LLPDeedUrl"),
		@Mapping(source = "request.partnershipDeedFilename", target = "partnershipDeedFilename"),
		@Mapping(source = "request.partnershipDeedUrl", target = "partnershipDeedUrl"),
		@Mapping(source = "request.certificateOfRegistrationFilename", target = "certificateOfRegistrationFilename"),
		@Mapping(source = "request.certificateOfRegistrationUrl", target = "certificateOfRegistrationUrl"),
		@Mapping(source = "request.shareCertificateFilename", target = "shareCertificateFilename"),
		@Mapping(source = "request.shareCertificateUrl", target = "shareCertificateUrl"),
		@Mapping(source = "request.commencementCertificateFilename", target = "commencementCertificateFilename"),
		@Mapping(source = "request.commencementCertificateUrl", target = "commencementCertificateUrl"),
		@Mapping(source = "request.licenseUnderSection8Filename", target = "licenseUnderSection8Filename"),
		@Mapping(source = "request.licenseUnderSection8Url", target = "licenseUnderSection8Url"),
		@Mapping(source = "request.bombayShopEstablishmentCertificateFilename", target = "bombayShopEstablishmentCertificateFilename"),
		@Mapping(source = "request.bombayShopEstablishmentCertificateUrl", target = "bombayShopEstablishmentCertificateUrl"),
		@Mapping(source = "request.professionalTaxEnrollmentCertificateFilename", target = "professionalTaxEnrollmentCertificateFilename"),
		@Mapping(source = "request.professionalTaxEnrollmentCertificateUrl", target = "professionalTaxEnrollmentCertificateUrl"),
		@Mapping(source = "request.professionalTaxRegistrationCertificateFilename", target = "professionalTaxRegistrationCertificateFilename"),
		@Mapping(source = "request.professionalTaxRegistrationCertificateUrl", target = "professionalTaxRegistrationCertificateUrl"),
		@Mapping(source = "request.ESIRegistrationFilename", target = "ESIRegistrationFilename"),
		@Mapping(source = "request.ESIRegistrationUrl", target = "ESIRegistrationUrl"),
		@Mapping(source = "request.PFRegistrationFilename", target = "PFRegistrationFilename"),
		@Mapping(source = "request.PFRegistrationUrl", target = "PFRegistrationUrl"),
		@Mapping(source = "request.certificateByDoeFilename", target = "certificateByDoeFilename"),
		@Mapping(source = "request.certificateByDoeUrl", target = "certificateByDoeUrl"),
		@Mapping(source = "request.TANFilename", target = "TANFilename"),
		@Mapping(source = "request.TANUrl", target = "TANUrl"),
		@Mapping(source = "request.section11RegistrationFilename", target = "section11RegistrationFilename"),
		@Mapping(source = "request.section11RegistrationUrl", target = "section11RegistrationUrl"),
		@Mapping(source = "request.certificateOfAffiliationFilename", target = "certificateOfAffiliationFilename"),
		@Mapping(source = "request.certificateOfAffiliationUrl", target = "certificateOfAffiliationUrl"),
		@Mapping(source = "request.entityProofFilename", target = "entityProofFilename"),
		@Mapping(source = "request.entityProofUrl", target = "entityProofUrl"),
		@Mapping(source = "request.companyAppilcationFilename", target = "companyAppilcationFilename"),
		@Mapping(source = "request.companyAppilcationUrl", target = "companyAppilcationUrl"),
		@Mapping(source = "request.SEBIRegistrationCertificateFilename", target = "SEBIRegistrationCertificateFilename"),
		@Mapping(source = "request.SEBIRegistrationCertificateUrl", target = "SEBIRegistrationCertificateUrl"),
	   
		@Mapping(source = "request.CSRPolicyFileName", target = "CSRPolicyFileName"),
	@Mapping(source = "request.CSRPolicyUrl", target = "CSRPolicyUrl"),
	
	@Mapping(source = "request.CINFileName", target = "CINFileName"),
	@Mapping(source = "request.CINUrl", target = "CINUrl"),
	
	@Mapping(source = "request.LLPINFileName", target = "LLPINFileName"),
	@Mapping(source = "request.LLPINFileUrl", target = "LLPINFileUrl"),
	
	@Mapping(source = "request.registerCertificateFileName", target = "registerCertificateFileName"),
	@Mapping(source = "request.registerCertificateUrl", target = "registerCertificateUrl"),
	
	@Mapping(source = "request.RBIFileName", target = "RBIFileName"),
	@Mapping(source = "request.RBIFileUrl", target = "RBIFileUrl"),
	
	@Mapping(source = "request.SEBIFileName", target = "SEBIFileName"),
	@Mapping(source = "request.SEBIFileUrl", target = "SEBIFileUrl"),
	
	@Mapping(source = "request.bankStatementFileName", target = "bankStatementFileName"),
	@Mapping(source = "request.bankStatementUrl", target = "bankStatementUrl"),
	
	@Mapping(source = "request.auditedBalanceSheetFileName", target = "auditedBalanceSheetFileName"),
	@Mapping(source = "request.auditedBalanceSheetUrl", target = "auditedBalanceSheetUrl"),
	
	@Mapping(source = "request.statementOfCashFileName", target = "statementOfCashFileName"),
	@Mapping(source = "request.statementOfCashUrl", target = "statementOfCashUrl"),
	
	@Mapping(source = "request.statementOfFunctionalExpensesFileName", target = "statementOfFunctionalExpensesFileName"),
	@Mapping(source = "request.statementOfFunctionalExpensesUrl", target = "statementOfFunctionalExpensesUrl"),
	
	})
	OrganisationLegalDetails mapOrganisationLegalDetailsRequestToOrganisationLegalDetails(
			OrganisationLegalDetailsRequest request, Organisation organisation);

	@Mappings({ @Mapping(source = "request.id", target = "id"),
			@Mapping(target = "organisation", source = "organisation"),
			@Mapping(source = "request.certificateOfIncorporationUrl", target = "certificateOfIncorporationUrl"),
			@Mapping(source = "request.fcraApprovalLetter", target = "fcraApprovalLetter"),
			@Mapping(source = "request.fcraApprovalValidity", target = "fcraApprovalValidity"),
			@Mapping(source = "request.trustDeedUrl", target = "trustDeedUrl"),
			@Mapping(source = "request.memorandumOfAssociationUrl", target = "memorandumOfAssociationUrl"),
			@Mapping(source = "request.articlesOfAssociationUrl", target = "articlesOfAssociationUrl"),
			@Mapping(source = "request.articlesOfAssociationValidity", target = "articlesOfAssociationValidity"),
			@Mapping(source = "request.urlOf80G12A12AACertificate", target = "urlOf80G12A12AACertificate"),
			@Mapping(source = "request.formCSR1Url", target = "formCSR1Url"),
			@Mapping(source = "request.fcraApprovalLetterFilename", target = "fcraApprovalLetterFilename"),
			@Mapping(source = "request.byeLawsFilename", target = "byeLawsFilename"),
			@Mapping(source = "request.byeLawsUrl", target = "byeLawsUrl"),
			@Mapping(source = "request.LLPDeedFilename", target = "LLPDeedFilename"),
			@Mapping(source = "request.LLPDeedUrl", target = "LLPDeedUrl"),
			@Mapping(source = "request.partnershipDeedFilename", target = "partnershipDeedFilename"),
			@Mapping(source = "request.partnershipDeedUrl", target = "partnershipDeedUrl"),
			@Mapping(source = "request.certificateOfRegistrationFilename", target = "certificateOfRegistrationFilename"),
			@Mapping(source = "request.certificateOfRegistrationUrl", target = "certificateOfRegistrationUrl"),
			@Mapping(source = "request.shareCertificateFilename", target = "shareCertificateFilename"),
			@Mapping(source = "request.shareCertificateUrl", target = "shareCertificateUrl"),
			@Mapping(source = "request.commencementCertificateFilename", target = "commencementCertificateFilename"),
			@Mapping(source = "request.commencementCertificateUrl", target = "commencementCertificateUrl"),
			@Mapping(source = "request.licenseUnderSection8Filename", target = "licenseUnderSection8Filename"),
			@Mapping(source = "request.licenseUnderSection8Url", target = "licenseUnderSection8Url"),
			@Mapping(source = "request.bombayShopEstablishmentCertificateFilename", target = "bombayShopEstablishmentCertificateFilename"),
			@Mapping(source = "request.bombayShopEstablishmentCertificateUrl", target = "bombayShopEstablishmentCertificateUrl"),
			@Mapping(source = "request.professionalTaxEnrollmentCertificateFilename", target = "professionalTaxEnrollmentCertificateFilename"),
			@Mapping(source = "request.professionalTaxEnrollmentCertificateUrl", target = "professionalTaxEnrollmentCertificateUrl"),
			@Mapping(source = "request.professionalTaxRegistrationCertificateFilename", target = "professionalTaxRegistrationCertificateFilename"),
			@Mapping(source = "request.professionalTaxRegistrationCertificateUrl", target = "professionalTaxRegistrationCertificateUrl"),
			@Mapping(source = "request.ESIRegistrationFilename", target = "ESIRegistrationFilename"),
			@Mapping(source = "request.ESIRegistrationUrl", target = "ESIRegistrationUrl"),
			@Mapping(source = "request.PFRegistrationFilename", target = "PFRegistrationFilename"),
			@Mapping(source = "request.PFRegistrationUrl", target = "PFRegistrationUrl"),
			@Mapping(source = "request.certificateByDoeFilename", target = "certificateByDoeFilename"),
			@Mapping(source = "request.certificateByDoeUrl", target = "certificateByDoeUrl"),
			@Mapping(source = "request.TANFilename", target = "TANFilename"),
			@Mapping(source = "request.TANUrl", target = "TANUrl"),
			@Mapping(source = "request.section11RegistrationFilename", target = "section11RegistrationFilename"),
			@Mapping(source = "request.section11RegistrationUrl", target = "section11RegistrationUrl"),
			@Mapping(source = "request.certificateOfAffiliationFilename", target = "certificateOfAffiliationFilename"),
			@Mapping(source = "request.certificateOfAffiliationUrl", target = "certificateOfAffiliationUrl"),
			@Mapping(source = "request.entityProofFilename", target = "entityProofFilename"),
			@Mapping(source = "request.entityProofUrl", target = "entityProofUrl"),
			@Mapping(source = "request.companyAppilcationFilename", target = "companyAppilcationFilename"),
			@Mapping(source = "request.companyAppilcationUrl", target = "companyAppilcationUrl"),
			@Mapping(source = "request.SEBIRegistrationCertificateFilename", target = "SEBIRegistrationCertificateFilename"),
			@Mapping(source = "request.SEBIRegistrationCertificateUrl", target = "SEBIRegistrationCertificateUrl")})
	OrganisationLegalDetails mapOrganisationLegalDetailsRequestToOrganisationLegalDetails(
			OrganisationLegalDetailsUpdateRequest request, Organisation organisation);

	@Mappings({ @Mapping(source = "request.certificateOfIncorporationUrl", target = "certificateOfIncorporationUrl"),
			@Mapping(source = "request.fcraApprovalLetter", target = "fcraApprovalLetter"),
			@Mapping(source = "request.fcraApprovalValidity", target = "fcraApprovalValidity"),
			@Mapping(source = "request.trustDeedUrl", target = "trustDeedUrl"),
			@Mapping(source = "request.memorandumOfAssociationUrl", target = "memorandumOfAssociationUrl"),
			@Mapping(source = "request.articlesOfAssociationUrl", target = "articlesOfAssociationUrl"),
			@Mapping(source = "request.articlesOfAssociationValidity", target = "articlesOfAssociationValidity"),
			@Mapping(source = "request.urlOf80G12A12AACertificate", target = "urlOf80G12A12AACertificate"),
			@Mapping(source = "request.formCSR1Url", target = "formCSR1Url"),
			@Mapping(source = "request.id", target = "id"), @Mapping(source = "request.active", target = "active"),
			@Mapping(source = "request.deleted", target = "deleted"),
			@Mapping(source = "request.createdAt", target = "createdAt"),
			@Mapping(source = "request.modifiedAt", target = "modifiedAt"),
			@Mapping(source = "request.createdBy", target = "createdBy"),
			@Mapping(source = "request.lastModifiedBy", target = "lastModifiedBy"),
			@Mapping(source = "request.fcraApprovalLetterFilename", target = "fcraApprovalLetterFilename"),
			@Mapping(source = "request.byeLawsFilename", target = "byeLawsFilename"),
			@Mapping(source = "request.byeLawsUrl", target = "byeLawsUrl"),
			@Mapping(source = "request.LLPDeedFilename", target = "LLPDeedFilename"),
			@Mapping(source = "request.LLPDeedUrl", target = "LLPDeedUrl"),
			@Mapping(source = "request.partnershipDeedFilename", target = "partnershipDeedFilename"),
			@Mapping(source = "request.partnershipDeedUrl", target = "partnershipDeedUrl"),
			@Mapping(source = "request.certificateOfRegistrationFilename", target = "certificateOfRegistrationFilename"),
			@Mapping(source = "request.certificateOfRegistrationUrl", target = "certificateOfRegistrationUrl"),
			@Mapping(source = "request.shareCertificateFilename", target = "shareCertificateFilename"),
			@Mapping(source = "request.shareCertificateUrl", target = "shareCertificateUrl"),
			@Mapping(source = "request.commencementCertificateFilename", target = "commencementCertificateFilename"),
			@Mapping(source = "request.commencementCertificateUrl", target = "commencementCertificateUrl"),
			@Mapping(source = "request.licenseUnderSection8Filename", target = "licenseUnderSection8Filename"),
			@Mapping(source = "request.licenseUnderSection8Url", target = "licenseUnderSection8Url"),
			@Mapping(source = "request.bombayShopEstablishmentCertificateFilename", target = "bombayShopEstablishmentCertificateFilename"),
			@Mapping(source = "request.bombayShopEstablishmentCertificateUrl", target = "bombayShopEstablishmentCertificateUrl"),
			@Mapping(source = "request.professionalTaxEnrollmentCertificateFilename", target = "professionalTaxEnrollmentCertificateFilename"),
			@Mapping(source = "request.professionalTaxEnrollmentCertificateUrl", target = "professionalTaxEnrollmentCertificateUrl"),
			@Mapping(source = "request.professionalTaxRegistrationCertificateFilename", target = "professionalTaxRegistrationCertificateFilename"),
			@Mapping(source = "request.professionalTaxRegistrationCertificateUrl", target = "professionalTaxRegistrationCertificateUrl"),
			@Mapping(source = "request.ESIRegistrationFilename", target = "ESIRegistrationFilename"),
			@Mapping(source = "request.ESIRegistrationUrl", target = "ESIRegistrationUrl"),
			@Mapping(source = "request.PFRegistrationFilename", target = "PFRegistrationFilename"),
			@Mapping(source = "request.PFRegistrationUrl", target = "PFRegistrationUrl"),
			@Mapping(source = "request.certificateByDoeFilename", target = "certificateByDoeFilename"),
			@Mapping(source = "request.certificateByDoeUrl", target = "certificateByDoeUrl"),
			@Mapping(source = "request.TANFilename", target = "TANFilename"),
			@Mapping(source = "request.TANUrl", target = "TANUrl"),
			@Mapping(source = "request.section11RegistrationFilename", target = "section11RegistrationFilename"),
			@Mapping(source = "request.section11RegistrationUrl", target = "section11RegistrationUrl"),
			@Mapping(source = "request.certificateOfAffiliationFilename", target = "certificateOfAffiliationFilename"),
			@Mapping(source = "request.certificateOfAffiliationUrl", target = "certificateOfAffiliationUrl"),
			@Mapping(source = "request.entityProofFilename", target = "entityProofFilename"),
			@Mapping(source = "request.entityProofUrl", target = "entityProofUrl"),
			@Mapping(source = "request.companyAppilcationFilename", target = "companyAppilcationFilename"),
			@Mapping(source = "request.companyAppilcationUrl", target = "companyAppilcationUrl"),
			@Mapping(source = "request.SEBIRegistrationCertificateFilename", target = "SEBIRegistrationCertificateFilename"),
			@Mapping(source = "request.SEBIRegistrationCertificateUrl", target = "SEBIRegistrationCertificateUrl"),
	
	@Mapping(source = "request.CSRPolicyFileName", target = "CSRPolicyFileName"),
	@Mapping(source = "request.CSRPolicyUrl", target = "CSRPolicyUrl"),
	
	@Mapping(source = "request.CINFileName", target = "CINFileName"),
	@Mapping(source = "request.CINUrl", target = "CINUrl"),
	
	@Mapping(source = "request.LLPINFileName", target = "LLPINFileName"),
	@Mapping(source = "request.LLPINFileUrl", target = "LLPINFileUrl"),
	
	@Mapping(source = "request.registerCertificateFileName", target = "registerCertificateFileName"),
	@Mapping(source = "request.registerCertificateUrl", target = "registerCertificateUrl"),
	
	@Mapping(source = "request.RBIFileName", target = "RBIFileName"),
	@Mapping(source = "request.RBIFileUrl", target = "RBIFileUrl"),
	
	@Mapping(source = "request.SEBIFileName", target = "SEBIFileName"),
	@Mapping(source = "request.SEBIFileUrl", target = "SEBIFileUrl"),
	
	@Mapping(source = "request.bankStatementFileName", target = "bankStatementFileName"),
	@Mapping(source = "request.bankStatementUrl", target = "bankStatementUrl"),
	
	@Mapping(source = "request.auditedBalanceSheetFileName", target = "auditedBalanceSheetFileName"),
	@Mapping(source = "request.auditedBalanceSheetUrl", target = "auditedBalanceSheetUrl"),
	
	@Mapping(source = "request.statementOfCashFileName", target = "statementOfCashFileName"),
	@Mapping(source = "request.statementOfCashUrl", target = "statementOfCashUrl"),
	
	@Mapping(source = "request.statementOfFunctionalExpensesFileName", target = "statementOfFunctionalExpensesFileName"),
	@Mapping(source = "request.statementOfFunctionalExpensesUrl", target = "statementOfFunctionalExpensesUrl"),
	
	})
	OrganisationLegalDetailsResponse mapOrganisationLegalDetailsToOrganisationLegalDetailsResponse(
			OrganisationLegalDetails request);
	
	@Mappings({ @Mapping(target = "certificateOfIncorporationUrl",
			expression = "java(MappingHelper.getCertificationOfIncorporationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(target = "fcraApprovalLetter",
				expression = "java(MappingHelper.getFcraApprovalLetterUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.fcraApprovalValidity", target = "fcraApprovalValidity"),
		@Mapping(target = "trustDeedUrl",
			expression = "java(MappingHelper.getTrustDeedUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(target = "memorandumOfAssociationUrl", 
			expression = "java(MappingHelper.getMemorandumOfAssociationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(target = "articlesOfAssociationUrl",
			expression = "java(MappingHelper.getArticlesOfAssociationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.articlesOfAssociationValidity", target = "articlesOfAssociationValidity"),
		@Mapping(target = "urlOf80G12A12AACertificate",
				expression = "java(MappingHelper.getUrlOf80g12a12aaCertificateBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(target = "formCSR1Url",
			expression = "java(MappingHelper.getFormCSR1UrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.id", target = "id"), @Mapping(source = "request.active", target = "active"),
		@Mapping(source = "request.deleted", target = "deleted"),
		@Mapping(source = "request.createdAt", target = "createdAt"),
		@Mapping(source = "request.modifiedAt", target = "modifiedAt"),
		@Mapping(source = "request.createdBy", target = "createdBy"),
		@Mapping(source = "request.lastModifiedBy", target = "lastModifiedBy"),
		@Mapping(source = "request.fcraApprovalLetterFilename", target = "fcraApprovalLetterFilename"),
		@Mapping(source = "request.byeLawsFilename", target = "byeLawsFilename"),
		@Mapping(target = "byeLawsUrl",
			expression = "java(MappingHelper.getByeLawsUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.LLPDeedFilename", target = "LLPDeedFilename"),
		@Mapping(target = "LLPDeedUrl",
			expression = "java(MappingHelper.getLLPDeedUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.partnershipDeedFilename", target = "partnershipDeedFilename"),
		@Mapping(target = "partnershipDeedUrl",
			expression = "java(MappingHelper.getPartnershipDeedUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.certificateOfRegistrationFilename", target = "certificateOfRegistrationFilename"),
		@Mapping(target = "certificateOfRegistrationUrl",
			expression = "java(MappingHelper.getCertificateOfRegistrationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.shareCertificateFilename", target = "shareCertificateFilename"),
		@Mapping(target = "shareCertificateUrl",
			expression = "java(MappingHelper.getShareCertificateUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.commencementCertificateFilename", target = "commencementCertificateFilename"),
		@Mapping(target = "commencementCertificateUrl",
			expression = "java(MappingHelper.getCommencementCertificateUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.licenseUnderSection8Filename", target = "licenseUnderSection8Filename"),
		@Mapping(target = "licenseUnderSection8Url",
			expression = "java(MappingHelper.getLicenseUnderSection8UrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.bombayShopEstablishmentCertificateFilename", target = "bombayShopEstablishmentCertificateFilename"),
		@Mapping(target = "bombayShopEstablishmentCertificateUrl",
			expression = "java(MappingHelper.getBombayShopEstablishmentCertificateUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.professionalTaxEnrollmentCertificateFilename", target = "professionalTaxEnrollmentCertificateFilename"),
		@Mapping(target = "professionalTaxEnrollmentCertificateUrl",
			expression = "java(MappingHelper.getProfessionalTaxEnrollmentCertificateUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.professionalTaxRegistrationCertificateFilename", target = "professionalTaxRegistrationCertificateFilename"),
		@Mapping(target = "professionalTaxRegistrationCertificateUrl",
			expression = "java(MappingHelper.getProfessionalTaxRegistrationCertificateUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.ESIRegistrationFilename", target = "ESIRegistrationFilename"),
		@Mapping(target = "ESIRegistrationUrl",
			expression = "java(MappingHelper.getESIRegistrationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.PFRegistrationFilename", target = "PFRegistrationFilename"),
		@Mapping(target = "PFRegistrationUrl",
			expression = "java(MappingHelper.getPFRegistrationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.certificateByDoeFilename", target = "certificateByDoeFilename"),
		@Mapping(target = "certificateByDoeUrl",
			expression = "java(MappingHelper.getCertificateByDoeUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.TANFilename", target = "TANFilename"),
		@Mapping(target = "TANUrl",
			expression = "java(MappingHelper.getTANUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.section11RegistrationFilename", target = "section11RegistrationFilename"),
		@Mapping(target = "section11RegistrationUrl",
			expression = "java(MappingHelper.getSection11RegistrationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.certificateOfAffiliationFilename", target = "certificateOfAffiliationFilename"),
		@Mapping(target = "certificateOfAffiliationUrl",
			expression = "java(MappingHelper.getCertificateOfAffiliationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.entityProofFilename", target = "entityProofFilename"),
		@Mapping(target = "entityProofUrl",
			expression = "java(MappingHelper.getEntityProofUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.companyAppilcationFilename", target = "companyAppilcationFilename"),
		@Mapping(target = "companyAppilcationUrl",
			expression = "java(MappingHelper.getCompanyAppilcationUrlBasedOnApprovalStatus(approvalStatus,request))"),
		@Mapping(source = "request.SEBIRegistrationCertificateFilename", target = "SEBIRegistrationCertificateFilename"),
		@Mapping(target = "SEBIRegistrationCertificateUrl",
			expression = "java(MappingHelper.getSEBIRegistrationCertificateUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.CSRPolicyFileName", target = "CSRPolicyFileName"),
		@Mapping( target = "CSRPolicyUrl",
		expression = "java(MappingHelper.getCSRPolicyUrlBasedOnApprovalStatus(approvalStatus,request))"),
				
		
		@Mapping(source = "request.CINFileName", target = "CINFileName"),
		@Mapping( target = "CINUrl",
				expression = "java(MappingHelper.getCINUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.LLPINFileName", target = "LLPINFileName"),
		@Mapping(target = "LLPINFileUrl",
				expression = "java(MappingHelper.getLLPINFileUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.registerCertificateFileName", target = "registerCertificateFileName"),
		@Mapping( target = "registerCertificateUrl",
				expression = "java(MappingHelper.getRegisterCertificateUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.RBIFileName", target = "RBIFileName"),
		@Mapping( target = "RBIFileUrl",
				expression = "java(MappingHelper.getRBIFileUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.SEBIFileName", target = "SEBIFileName"),
		@Mapping( target = "SEBIFileUrl",
				expression = "java(MappingHelper.getSEBIFileUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.bankStatementFileName", target = "bankStatementFileName"),
		@Mapping( target = "bankStatementUrl",
				expression = "java(MappingHelper.getBankStatementUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.auditedBalanceSheetFileName", target = "auditedBalanceSheetFileName"),
		@Mapping(target = "auditedBalanceSheetUrl",
				expression = "java(MappingHelper.getAuditedBalanceSheetUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.statementOfCashFileName", target = "statementOfCashFileName"),
		@Mapping( target = "statementOfCashUrl",
				expression = "java(MappingHelper.getStatementOfCashUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
		@Mapping(source = "request.statementOfFunctionalExpensesFileName", target = "statementOfFunctionalExpensesFileName"),
		@Mapping(target = "statementOfFunctionalExpensesUrl",
				expression = "java(MappingHelper.getStatementOfFunctionalExpensesUrlBasedOnApprovalStatus(approvalStatus,request))"),
		
	})
	OrganisationLegalDetailsResponse mapOrganisationLegalDetailsToOrganisationLegalDetailsFetchResponse(
		OrganisationLegalDetails request, String approvalStatus);

	@Mappings({ @Mapping(source = "request.id", target = "organisationBeneficiaryTargetsId"),
			@Mapping(source = "request.targetCategory", target = "targetCategory") })
	OrganisationBeneficiaryTargetsResponse mapOrganisationBeneficiaryTargetsToOrganisationBeneficiaryTargetsResponse(
			OrganisationBeneficiaryTargets request);

	List<OrganisationSyncDto> toDto(List<Organisation> findAll);

	@Mappings({ @Mapping(source = "org.id", target = "id"), @Mapping(source = "org.name", target = "name") })
	OrgBasicDetailsResponse toBasicResponse(Organisation org);

	List<OrgBasicDetailsResponse> toBasicResponse(List<Organisation> org);

}
