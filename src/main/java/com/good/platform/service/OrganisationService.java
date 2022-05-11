package com.good.platform.service;

import java.util.List;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationMembersRoles;
import com.good.platform.entity.OrganisationTypes;
import com.good.platform.exception.SOException;
import com.good.platform.request.OrganisationMembersRolesRequest;
import com.good.platform.request.OrganisationTypesRequest;
import com.good.platform.request.dto.OrgBasicDetailsRequest;
import com.good.platform.request.dto.OrganisationBeneficiaryHistoryRequest;
import com.good.platform.request.dto.OrganisationFinancialDetailsRequest;
import com.good.platform.request.dto.OrganisationLegalDetailsRequest;
import com.good.platform.request.dto.OrganisationStatusRequestDto;
import com.good.platform.request.dto.put.OrganisationBeneficiaryHistoryUpdateRequest;
import com.good.platform.request.dto.put.OrganisationFinancialDetailsUpdateRequest;
import com.good.platform.request.dto.put.OrganisationLegalDetailsUpdateRequest;
import com.good.platform.request.dto.put.SignedAggrementRequest;
import com.good.platform.request.dto.validator.OrganisationReviewDTO;
import com.good.platform.response.ExistenceResponseDTO;
import com.good.platform.response.OrgByNameResponse;
import com.good.platform.response.OrganisationLegalDetailsResponse;
import com.good.platform.response.dto.OrgBasicDetailsResponse;
import com.good.platform.response.dto.OrgPercentageCompletionResponse;
import com.good.platform.response.dto.OrganisationBeneficiaryHistoryResponse;
import com.good.platform.response.dto.OrganisationFinancialDetailsResponse;
import com.good.platform.response.dto.OrganisationStatusResponseDto;
import com.good.platform.response.validator.OrganisationCountDTO;

/**
 * OrganisationService is to handle all the business functionalities of
 * organisation service
 * 
 * @author Mohamedsuhail S
 *
 */
public interface OrganisationService {

	public void searchCompany();

	/**
	 * addOrgBasicDetails is to add the basic details in the orgaisation table
	 * 
	 * @param OrgBasicDetailsRequest
	 * @throws Exception
	 */
	OrgBasicDetailsResponse addOrgBasicDetails(OrgBasicDetailsRequest request) throws Exception;

	/**
	 * updateOrgBasicDetails is to update the organisation table data
	 */
	OrgBasicDetailsResponse updateOrgBasicDetails(OrgBasicDetailsRequest request);

	/**
	 * getOrgBasicDetails is to get the organisation details
	 * 
	 * @param id, id of the registered organisation table data
	 */
	OrgBasicDetailsResponse getOrgBasicDetails(String id);

	public OrganisationBeneficiaryHistoryResponse addBeneficiaryHistory(OrganisationBeneficiaryHistoryRequest request);

	public OrganisationFinancialDetailsResponse addFinancialDoccument(OrganisationFinancialDetailsRequest request);

	OrganisationBeneficiaryHistoryResponse updateBeneficiaryHistory(String beneficiaryHistoryId,
			OrganisationBeneficiaryHistoryUpdateRequest request);

	public OrganisationFinancialDetailsResponse updateFinancialDoccument(
			OrganisationFinancialDetailsUpdateRequest request, String id);

	public OrganisationLegalDetailsResponse addLegalDocuments(OrganisationLegalDetailsRequest request);

	public OrganisationLegalDetailsResponse getLegalDocuments(String id);

	public OrganisationFinancialDetailsResponse getFinancialDocuments(String id);

	public OrganisationBeneficiaryHistoryResponse getBeneficiaryHistory(String organisationId);

	OrganisationLegalDetailsResponse updateLegalDocuments(OrganisationLegalDetailsUpdateRequest request);

	public Boolean deleteLastAuditStatement(String lastAuditStatementId, String organisationId);

	public OrgPercentageCompletionResponse getPercentage(String organisationId);

	public OrgBasicDetailsResponse updateSignedAggrement(SignedAggrementRequest request);

	public ExistenceResponseDTO existBySignedAggrement(String organisationId);

	// public void updateStatus(String organisationId, OrganisationReviewDTO
	// reviewRequestDTO);

	/**
	 * @param statusRequestDTO
	 * @return
	 * @throws SOException
	 */
	public OrganisationStatusResponseDto updateStatus(String organisationId,
			OrganisationStatusRequestDto statusRequestDTO);

	/**
	 * @param id
	 * @return
	 */
	public Organisation findOrganisation(String id);

	public OrganisationCountDTO getCountByStatus();

	public OrgBasicDetailsResponse submitApprovalStatus(String organisationId);

	List<OrgBasicDetailsResponse> getAllOrgByIds(List<String> organisationIds);

	/**
	 * @implNote This method wrote for mg-beneficiary service, for the API:
	 *           /v1/completion-detailed
	 * @author Arya C Achari
	 * @since 31-Dec-2021
	 * 
	 * @param name
	 * @return
	 */
	OrgByNameResponse getOrganisationByName(String name);

	public List<String> getOrgOrganisationTypes(String registerAs);

	public List<String> getRegisteredAs(String organisationTypes);

	public List<OrganisationTypes> addOrgansationType(List<OrganisationTypesRequest> organisationTypesRequest);

	public List<String> getOrganisationMembersRoles(String organisationTypes);

	public List<OrganisationMembersRoles> addOrganisationMembersRolesRequest(
			List<OrganisationMembersRolesRequest> organisationMembersRolesRequest);

}
