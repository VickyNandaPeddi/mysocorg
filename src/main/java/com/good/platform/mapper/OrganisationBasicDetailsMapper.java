package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationFCRADetails;
import com.good.platform.entity.OrganisationMembers;
import com.good.platform.enums.ApprovalStatus;
import com.good.platform.enums.IdProof;
import com.good.platform.enums.OrgMemberType;
import com.good.platform.enums.OrganisationsTypeEnum;
import com.good.platform.request.dto.OrgBasicDetailsRequest;
import com.good.platform.response.dto.OrgBasicDetailsResponse;
import com.good.platform.service.CountriesService;
import com.good.platform.service.impl.CountriesServiceImpl;

/**
 * OrganisationBasicDetailsMapper is used for mapping functionalities respect to Organisation basic details
 * @author Mohamedsuhail S
 *
 */
@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationBasicDetailsMapper {
	
	/**
	 * getOrganisationMapper is to map the request Dto to Entity for insertion
	 * @param dto
	 * @return
	 */
	@Mapping(source = "type", target = "type")
	@Mapping(source = "headquarterCountry", target = "headquarterCountry", qualifiedByName = "getHeadquarterCountry")
	@Mapping(source = "approvalStatus", target = "approvalStatus")
	@Mapping(source = "idProof", target = "idProof")
	Organisation getOrganisationMapper(OrgBasicDetailsRequest dto);
	
	/**
	 * getOrganisationMapper is to map the request Dto to Entity for update
	 * @param dto
	 * @return
	 */
	@Mapping(source = "organisationId", target = "id")
	@Mapping(source = "type", target = "type")
	@Mapping(source = "headquarterCountry", target = "headquarterCountry", qualifiedByName = "getHeadquarterCountry")
	@Mapping(source = "approvalStatus", target = "approvalStatus")
	@Mapping(source = "idProof", target = "idProof")
	Organisation getOrganisationUpdateMapper(OrgBasicDetailsRequest dto);
	
	/**
	 * getOrganisationMapper is to map the Entity to request Dto
	 * @param dto
	 * @return
	 */
	@Mapping(source = "organisationEntity.name", target = "name")
	@Mapping(source = "organisationEntity.type",target = "type")
	@Mapping(source = "organisationEntity.yearFounded", target = "yearFounded")
	@Mapping(source = "organisationEntity.noOfEmployees", target = "noOfEmployees")
	@Mapping(source = "organisationEntity.emailId", target = "emailId")
	@Mapping(source = "organisationEntity.website", target = "website")
	@Mapping(source = "organisationEntity.headquarterCountry", target = "headquarterCountry", qualifiedByName = "getCountryIdByName")
	@Mapping(source = "organisationEntity.about", target = "about")
	@Mapping(source = "organisationEntity.gstNumber", target = "gstNumber")
	@Mapping(source = "organisationEntity.id", target = "id")
	@Mapping(source = "organisationEntity.active", target = "active")
	@Mapping(source = "organisationEntity.deleted", target = "deleted")
	@Mapping(source = "organisationEntity.createdAt", target = "createdAt")
	@Mapping(source = "organisationEntity.modifiedAt", target = "modifiedAt")
	@Mapping(source = "organisationEntity.createdBy", target = "createdBy")
	@Mapping(source = "organisationEntity.lastModifiedBy", target = "lastModifiedBy")
	@Mapping(source = "organisationMembers.id", target = "creatorMemberId")
	@Mapping(source = "organisationMembers.name", target = "fullName")
	@Mapping(source = "organisationMembers.type", target = "memberType")
	@Mapping(source = "organisationMembers.otherType", target = "otherMemberType")
	@Mapping(source = "organisationMembers.email", target = "memberEmailId")
	@Mapping(source = "organisationMembers.phone", target = "mobile")
	@Mapping(source = "organisationMembers.idProof", target = "idProof")
	@Mapping(source = "organisationMembers.otherIdProof", target = "otherIdProof")
	@Mapping(source = "organisationMembers.idProofNumber", target = "idProofNumber")
	@Mapping(source = "organisationEntity.signedAgreementUrl", target = "signedAgreementUrl")
	@Mapping(source = "organisationMembers.amlChecked", target = "amlChecked")
	OrgBasicDetailsResponse getOrganisationMapper(Organisation organisationEntity, OrganisationMembers organisationMembers);
	
	/**
	 * getMemberTypeString is to get the member type from the requested String
	 * @param memberType
	 * @return
	 */
	@Named("getMemberTypeString")
	default String getMemberTypeString(OrgMemberType memberType) {
		return memberType.toString();
	}
	
	/**
	 * getHeadquarterCountry is to find the country from the list with requested country ID
	 * @param headquarterCountry
	 * @return
	 */
	@Named("getHeadquarterCountry")
	default String getHeadquarterCountry(int headquarterCountry) {
		CountriesService countriesService = new CountriesServiceImpl();
		return countriesService.getCountry(headquarterCountry);
	}
	
	@Named("getCountryIdByName")
	default int getCountryIdByName(String countryName) {
		CountriesService countriesService = new CountriesServiceImpl();
		return Integer.parseInt(countriesService.getCountryByName(countryName));
	}
	
	/**
	 * getApprovalStatus is to get the approval status
	 * @param approvalStatus
	 * @return
	 */
//	@Named("getApprovalStatus")
//	default ApprovalStatus getApprovalStatus(String approvalStatus) {
//		ApprovalStatus enumValue = ApprovalStatus.getByName(approvalStatus);
//		return enumValue;
//	}
	
	/**
	 * getApprovalStatus is to get the approval status
	 * @param approvalStatus
	 * @return
	 */
	@Named("getApprovalStatusString")
	default String getApprovalStatusString(ApprovalStatus approvalStatus) {
		return approvalStatus.toString();
	}
	
	/**
	 * getCompanyType is to get the country type from the requested String
	 * @param companyType
	 * @return
	 */
	@Named("getCompanyType")
	default OrganisationsTypeEnum getCompanyType(String companyType) {
		OrganisationsTypeEnum enumValue = OrganisationsTypeEnum.getByName(companyType);
		return enumValue;
	}
	
	/**
	 * getCompanyTypeString is to get the country type from the requested String
	 * @param companyType
	 * @return String
	 */
	@Named("getCompanyTypeString")
	default String getCompanyTypeString(OrganisationsTypeEnum companyType) {
		return companyType.toString();
	}
	
	/**
	 * getIdProof is to get the ID proof data by using the requested String
	 * @param idProof
	 * @return
	 */
	@Named("getIdProof")
	default IdProof getIdProof(String idProof) {
		IdProof enumValue = IdProof.getByName(idProof);
		return enumValue;
	}
	
	/**
	 * getIdProofString is to get the ID proof data by using the requested String
	 * @param idProof
	 * @return String
	 */
	@Named("getIdProofString")
	default String getIdProofString(IdProof idProof) {
		return idProof.toString();
	}

	OrgBasicDetailsResponse getFCRADetails(OrganisationFCRADetails organisationFCRADetails);
	
}
