package com.good.platform.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationMembers;
import com.good.platform.enums.IdProof;
import com.good.platform.enums.OrgMemberType;
import com.good.platform.model.OrganisationMembersModel;
import com.good.platform.model.OrganisationMembersModelRequest;
import com.good.platform.request.dto.OrgBasicDetailsRequest;
import com.good.platform.response.dto.AmlCheckResponse;
import com.good.platform.response.dto.OrgLeadershipDetailsResponse;

/**
 * OrganisationMembersMapper is used for mapping functionalities respect to Organisation member details
 * @author Mohamedsuhail S
 *
 */
@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationMembersMapper {
	
	/**
	 * getOrganisationMapper is to map the request Dto to Entity for insertion
	 * @param dto
	 * @return
	 */
	@Mapping(source = "organisation", target = "organisation")
	@Mapping(source = "request.fullName" , target = "name")
	@Mapping(source = "request.memberType" , target = "type")
	@Mapping(source = "request.otherMemberType" , target = "otherType")
	@Mapping(source = "request.memberEmailId" , target = "email")
	@Mapping(source = "request.mobile" , target = "phone")
	@Mapping(source = "request.idProof", target = "idProof")
	OrganisationMembers getOrganisationMapper(Organisation organisation, OrgBasicDetailsRequest request);
	
	/**
	 * getOrganisationUpdateMapper is to map the request Dto to Entity for update
	 * @param dto
	 * @return
	 */
	@Mapping(source = "dto.creatorMemberId", target = "id")
	@Mapping(source = "organisation", target = "organisation")
	@Mapping(source = "dto.fullName" , target = "name")
	@Mapping(source = "dto.memberType" , target = "type")
	@Mapping(source = "dto.otherMemberType" , target = "otherType")
	@Mapping(source = "dto.emailId" , target = "email")
	@Mapping(source = "dto.mobile" , target = "phone")
	@Mapping(source = "dto.idProof", target = "idProof")
	OrganisationMembers getOrganisationUpdateMapper(Organisation organisation, OrgBasicDetailsRequest dto);
	
	@Mapping(source = "organisation", target = "organisation" )
	@Mapping(source = "organisationMembersModel.memberId", target = "id")
	@Mapping(source = "organisationMembersModel.name", target = "name")
	@Mapping(source = "organisationMembersModel.type", target = "type")
	@Mapping(source = "organisationMembersModel.otherType", target = "otherType")
	@Mapping(source = "organisationMembersModel.email", target = "email")
	@Mapping(source = "organisationMembersModel.phone", target = "phone")
	@Mapping(source = "organisationMembersModel.idProof", target = "idProof")
	@Mapping(source = "organisationMembersModel.otherIdProof", target = "otherIdProof")
	@Mapping(source = "organisationMembersModel.authorisedSignatory", target = "authorisedSignatory")
	@Mapping(source = "organisationMembersModel.permanentEmployee", target = "permanentEmployee")
	@Mapping(source = "organisationMembersModel.amlCheck", target = "amlChecked")
	@Mapping(source = "organisationMembersModel.sortOrder", target = "sortOrder")
	OrganisationMembers organisationMembersMapper(OrganisationMembersModelRequest organisationMembersModel, Organisation organisation);
	
	@Mapping(source = "organisationMembers.id", target = "memberId")
	@Mapping(source = "organisationMembers.name", target = "name")
	@Mapping(source = "organisationMembers.type", target = "type")
	@Mapping(source = "organisationMembers.otherType", target = "otherType")
	@Mapping(source = "organisationMembers.email", target = "email")
	@Mapping(source = "organisationMembers.phone", target = "phone")
	@Mapping(source = "organisationMembers.idProof", target = "idProof")
	@Mapping(source = "organisationMembers.otherIdProof", target = "otherIdProof")
	@Mapping(source = "organisationMembers.authorisedSignatory", target = "authorisedSignatory")
	@Mapping(source = "organisationMembers.permanentEmployee", target = "permanentEmployee")
	@Mapping(source = "organisationMembers.amlChecked", target = "amlCheck")
	@Mapping(source = "organisationMembers.sortOrder", target = "sortOrder")
	OrganisationMembersModel organisationMembersMapper(OrganisationMembers organisationMembers);
	
	@Mapping(source = "organisationId", target = "organisationId")
	@Mapping(source = "organisationMembersModelList", target = "organisationMembersList")
	OrgLeadershipDetailsResponse organisationLeadershipResponseMapper(String organisationId,
			List<OrganisationMembersModel> organisationMembersModelList);
	
	@Mappings({
		@Mapping(source = "organisationId", target = "organisationId"),
		@Mapping(source = "organisationMembers.amlChecked", target = "amlChecked"),
		@Mapping(source = "organisationMembers.id", target = "memberId")
	})
	AmlCheckResponse mapAmlCheckResponse(OrganisationMembers organisationMembers, String organisationId);
	
	/**
	 * getCompanyType is to get the country type from the requested String
	 * @param companyType
	 * @return
	 */
	@Named("getMemberType")
	default OrgMemberType getMemberType(String memberType) {
		OrgMemberType enumValue = OrgMemberType.getByName(memberType);
		return enumValue;
	}
	
	/**
	 * getMemberTypeString is to get the country type from the requested String
	 * @param companyType
	 * @return String
	 */
	@Named("getMemberTypeString")
	default String getMemberTypeString(OrgMemberType orgMemberType) {
		return orgMemberType.toString();
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
	
}
