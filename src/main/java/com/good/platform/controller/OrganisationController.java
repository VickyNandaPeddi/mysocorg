package com.good.platform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.entity.OrganisationMembersRoles;
import com.good.platform.entity.OrganisationTypes;
import com.good.platform.request.OrganisationMembersRolesRequest;
import com.good.platform.request.OrganisationTypesRequest;
import com.good.platform.request.dto.AmlCheckRequest;
import com.good.platform.request.dto.OrgAddressDetailsRequest;
import com.good.platform.request.dto.OrgBasicDetailsRequest;
import com.good.platform.request.dto.OrgBudgetDonorRequest;
import com.good.platform.request.dto.OrgLeadershipDetailsRequest;
import com.good.platform.request.dto.OrgPurposeDetailsRequest;
import com.good.platform.request.dto.OrganisationBeneficiaryHistoryRequest;
import com.good.platform.request.dto.OrganisationFinancialDetailsRequest;
import com.good.platform.request.dto.OrganisationLegalDetailsRequest;
import com.good.platform.request.dto.put.OrganisationBeneficiaryHistoryUpdateRequest;
import com.good.platform.request.dto.put.OrganisationFinancialDetailsUpdateRequest;
import com.good.platform.request.dto.put.OrganisationLegalDetailsUpdateRequest;
import com.good.platform.request.dto.put.SignedAggrementRequest;
import com.good.platform.response.ExistenceResponseDTO;
import com.good.platform.response.OrgByNameResponse;
import com.good.platform.response.OrganisationLegalDetailsResponse;
import com.good.platform.response.dto.AmlCheckResponse;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.OrgAddressDetailsResponse;
import com.good.platform.response.dto.OrgBasicDetailsResponse;
import com.good.platform.response.dto.OrgBudgetDonorResponse;
import com.good.platform.response.dto.OrgLeadershipDetailsResponse;
import com.good.platform.response.dto.OrgPurposeDetailsResponse;
import com.good.platform.response.dto.OrganisationBeneficiaryHistoryResponse;
import com.good.platform.response.dto.OrganisationFinancialDetailsResponse;
import com.good.platform.service.OrgAddressService;
import com.good.platform.service.OrganisationBudgetDonorService;
import com.good.platform.service.OrganisationLeadershipDetailsService;
import com.good.platform.service.OrganisationPurposeService;
import com.good.platform.service.OrganisationService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OrganisationController is to route functionality requests regarding the
 * Organisation basic details
 * 
 * @author Mohamedsuhail S
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/organisation")
@RequiredArgsConstructor
@Slf4j
public class OrganisationController {

	private final OrganisationService orgService;

	private final OrgAddressService orgAddressService;

	private final OrganisationPurposeService organisationPurposeService;

	private final OrganisationLeadershipDetailsService organisationLeadershipDetailsService;

	private final OrganisationBudgetDonorService organisationBudgetDonorService;

	/**
	 * addOrgBasicDetails is to add the organization details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 * @throws Exception
	 */
	@PostMapping()
	public GoodPlatformResponseVO<OrgBasicDetailsResponse> addOrgBasicDetails(
			@RequestBody OrgBasicDetailsRequest request) throws Exception {
		OrgBasicDetailsResponse response = orgService.addOrgBasicDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBasicDetailsResponse>(), response,
				Constants.ORG_BASIC_DETAILS_ADD_SUCCESS, Constants.ORG_BASIC_DETAILS_ADD_FAIL);
	}

	/**
	 * updateOrgBasicDetails is to update the organization details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@PutMapping()
	public GoodPlatformResponseVO<OrgBasicDetailsResponse> updateOrgBasicDetails(
			@RequestBody OrgBasicDetailsRequest request) {
		OrgBasicDetailsResponse response = orgService.updateOrgBasicDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBasicDetailsResponse>(), response,
				Constants.ORG_BASIC_DETAILS_UPDATE_SUCCESS, Constants.ORG_BASIC_DETAILS_UPDATE_FAIL);
	}

	/**
	 * getOrgBasicDetails is to get the organization details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@GetMapping("/{id}")
	public GoodPlatformResponseVO<OrgBasicDetailsResponse> getOrgBasicDetails(@PathVariable("id") String id) {
		OrgBasicDetailsResponse response = orgService.getOrgBasicDetails(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBasicDetailsResponse>(), response,
				Constants.ORG_BASIC_DETAILS_FETCH_SUCCESS, Constants.ORG_BASIC_DETAILS_FETCH_FAIL);
	}

	/**
	 * getAllOrgBasicDetails is to get all of the organizations' details
	 * 
	 * @param token,     Should contain the access token
	 *                   addOrganisationAddressDetails is to add the address deails
	 *                   of the organisation
	 * @param accessInfo
	 * @param request
	 * @return
	 */
	// Location
	@PostMapping("/address/")
	public GoodPlatformResponseVO<OrgAddressDetailsResponse> addOrganisationAddressDetails(
			@RequestBody OrgAddressDetailsRequest request) {
		OrgAddressDetailsResponse orgAddressDetailsResponse = orgAddressService.addOrgAddressDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgAddressDetailsResponse>(),
				orgAddressDetailsResponse, Constants.ORG_ADDRESS_DETAILS_ADD_SUCCESS,
				Constants.ORG_ADDRESS_DETAILS_ADD_FAIL);
	}

	/**
	 * updateOrganisationAddressDetails is to update the organization address
	 * details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return Pageable OrgBasicDetailsResponse which contains the organizational
	 *         details
	 * @param request, contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@PutMapping("/address/")
	public GoodPlatformResponseVO<OrgAddressDetailsResponse> updateOrganisationAddressDetails(
			@RequestBody OrgAddressDetailsRequest request) {
		OrgAddressDetailsResponse response = orgAddressService.updateOrgAddressDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgAddressDetailsResponse>(), response,
				Constants.ORG_ADDRESS_DETAILS_UPDATE_SUCCESS, Constants.ORG_ADDRESS_DETAILS_UPDATE_FAIL);
	}

	@GetMapping("/address/{id}")
	public GoodPlatformResponseVO<OrgAddressDetailsResponse> getOrganisationAddressDetail(
			@PathVariable("id") String id) {
		OrgAddressDetailsResponse response = orgAddressService.getOrgAddressData(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgAddressDetailsResponse>(), response,
				Constants.ORG_ADDRESS_DETAILS_FETCH_SUCCESS, Constants.ORG_ADDRESS_DETAILS_FETCH_FAIL);
	}

	@DeleteMapping("/address/{addressId}")
	public GoodPlatformResponseVO<Boolean> deleteOrganisationAddressDetail(@PathVariable("addressId") String addressId,
			@RequestParam String organisationId) {
		Boolean response = orgAddressService.deleteOrgAddress(addressId, organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), response,
				Constants.ORG_ADDRESS_DETAILS_DELETE_SUCCESS, Constants.ORG_ADDRESS_DETAILS_DELETE_FAIL);
	}

//Track Record
	@PostMapping("/beneficiary-history")
	public GoodPlatformResponseVO<OrganisationBeneficiaryHistoryResponse> addBeneficiaryHistory(
			@RequestBody OrganisationBeneficiaryHistoryRequest request) {
		OrganisationBeneficiaryHistoryResponse response = orgService.addBeneficiaryHistory(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationBeneficiaryHistoryResponse>(),
				response, Constants.BENEFICIARY_HISTORY_ADD_SUCCESS, Constants.BENEFICIARY_HISTORY__ADD_FAIL);
	}

	@PutMapping("/beneficiary-history/{beneficiaryHistoryId}")
	public GoodPlatformResponseVO<OrganisationBeneficiaryHistoryResponse> updateBeneficiaryHistory(
			@PathVariable String beneficiaryHistoryId,
			@RequestBody OrganisationBeneficiaryHistoryUpdateRequest request) {
		OrganisationBeneficiaryHistoryResponse response = orgService.updateBeneficiaryHistory(beneficiaryHistoryId,
				request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationBeneficiaryHistoryResponse>(),
				response, Constants.BENEFICIARY_HISTORY_UPDATE_SUCCESS, Constants.BENEFICIARY_HISTORY_UPDATE_FAIL);
	}

	@GetMapping("{organisationId}/beneficiary-history")
	public GoodPlatformResponseVO<OrganisationBeneficiaryHistoryResponse> getBeneficiaryHistory(
			@PathVariable String organisationId) {
		OrganisationBeneficiaryHistoryResponse response = orgService.getBeneficiaryHistory(organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationBeneficiaryHistoryResponse>(),
				response, Constants.BENEFICIARY_HISTORY_FETCH_SUCCESS, Constants.BENEFICIARY_HISTORY_FETCH_FAIL);
	}

	/**
	 * getOrganisationAddressDetail is to get the organization address details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@PostMapping("/financial-documents")
	public GoodPlatformResponseVO<OrganisationFinancialDetailsResponse> addFinancialDoccument(
			@RequestBody OrganisationFinancialDetailsRequest request) {
		OrganisationFinancialDetailsResponse response = orgService.addFinancialDoccument(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBasicDetailsResponse>(), response,
				Constants.FINANCIAL_DOCUMENTS_ADD_SUCCESS, Constants.FINANCIAL_DOCUMENTS_ADD_FAIL);
	}

	@PutMapping("/financial-documents/{id}")
	public GoodPlatformResponseVO<OrganisationFinancialDetailsResponse> updateFinancialDoccument(
			@RequestBody OrganisationFinancialDetailsUpdateRequest request, @PathVariable String id) {
		OrganisationFinancialDetailsResponse response = orgService.updateFinancialDoccument(request, id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationFinancialDetailsResponse>(),
				response, Constants.FINANCIAL_DOCUMENTS_UPDATE_SUCCESS, Constants.FINANCIAL_DOCUMENTS_UPDATE_FAIL);

	}

	@GetMapping("{organisationId}/financial-documents")
	public GoodPlatformResponseVO<OrganisationFinancialDetailsResponse> getFinancialDocuments(
			@PathVariable String organisationId) {
		OrganisationFinancialDetailsResponse response = orgService.getFinancialDocuments(organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationFinancialDetailsResponse>(),
				response, Constants.FINANCIAL_DOCUMENTS_FETCH_SUCCESS, Constants.FINANCIAL_DOCUMENTS_FETCH_FAIL);
	}

	@DeleteMapping("/financial-documents/last-audit-statement/{lastAuditStatementId}")
	public GoodPlatformResponseVO<Boolean> deleteLastAuditStatement(
			@PathVariable("lastAuditStatementId") String lastAuditStatementId, @RequestParam String organisationId) {
		Boolean response = orgService.deleteLastAuditStatement(lastAuditStatementId, organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), response,
				Constants.LAST_AUDIT_STATEMENT_DELETE_SUCCESS, Constants.LAST_AUDIT_STATEMENT_DELETE_FAIL);
	}

	/**
	 * addOrganisationAddressDetails is to add the address deails of the
	 * organisation
	 * 
	 * @param accessInfo
	 * @param request
	 * @return
	 */
	@PostMapping("/purpose")
	public GoodPlatformResponseVO<OrgPurposeDetailsResponse> addOrganisationPurposeDetails(
			@RequestBody OrgPurposeDetailsRequest request) {
		OrgPurposeDetailsResponse orgAddressDetailsResponse = organisationPurposeService
				.addOrganisationPurposeDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgPurposeDetailsResponse>(),
				orgAddressDetailsResponse, Constants.ORG_PURPOSE_DETAILS_ADD_SUCCESS,
				Constants.ORG_PURPOSE_DETAILS_ADD_FAIL);
	}

	/**
	 * updateOrganisationAddressDetails is to update the organization address
	 * details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@PutMapping("/purpose")
	public GoodPlatformResponseVO<OrgPurposeDetailsResponse> updateOrganisationPurposeDetails(
			@RequestBody OrgPurposeDetailsRequest request) {
		OrgPurposeDetailsResponse response = organisationPurposeService.updateOrganisationPurposeDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgPurposeDetailsResponse>(), response,
				Constants.ORG_PURPOSE_DETAILS_UPDATE_SUCCESS, Constants.ORG_PURPOSE_DETAILS_UPDATE_FAIL);
	}

	/**
	 * getOrganisationPurposeDetails is to fetch the organisation member details
	 * 
	 * @param token
	 * @param accessInfo
	 * @param id
	 * @return
	 */
	@GetMapping("/purpose/{id}")
	public GoodPlatformResponseVO<OrgPurposeDetailsResponse> getOrganisationPurposeDetails(
			@PathVariable("id") String id) {
		OrgPurposeDetailsResponse response = organisationPurposeService.getOrganisationPurposeDetails(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgPurposeDetailsResponse>(), response,
				Constants.ORG_PURPOSE_DETAILS_FETCH_SUCCESS, Constants.ORG_PURPOSE_DETAILS_FETCH_FAIL);
	}

	@DeleteMapping("/purpose/mission/{missionStatementId}")
	public GoodPlatformResponseVO<Boolean> deleteMissionStatement(
			@PathVariable("missionStatementId") String missionStatementId, @RequestParam String organisationId) {
		Boolean response = organisationPurposeService.deleteMissionStatement(missionStatementId, organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), response,
				Constants.ORG_MISSION_STATEMENT_DELETE_SUCCESS, Constants.ORG_MISSION_STATEMENT_DELETE_FAIL);
	}

	/**
	 * addOrganisationLeadershipDetails is to add the address deails of the
	 * organisation
	 * 
	 * @param accessInfo
	 * @param request
	 * @return
	 */
	@PostMapping("/leadership")
	public GoodPlatformResponseVO<OrgLeadershipDetailsResponse> addOrganisationLeadershipDetails(
			@RequestBody OrgLeadershipDetailsRequest request) {
		OrgLeadershipDetailsResponse orgLeadershipDetailsResponse = organisationLeadershipDetailsService
				.addOrganisationLeadershipDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgLeadershipDetailsResponse>(),
				orgLeadershipDetailsResponse, Constants.ORG_LEADERSHIP_DETAILS_ADD_SUCCESS,
				Constants.ORG_LEADERSHIP_DETAILS_ADD_FAIL);
	}

	/**
	 * updateOrganisationLeadershipDetails is to update the organization address
	 * details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@PutMapping("/leadership")
	public GoodPlatformResponseVO<OrgLeadershipDetailsResponse> updateOrganisationLeadershipDetails(
			@RequestBody OrgLeadershipDetailsRequest request) {		
		OrgLeadershipDetailsResponse orgLeadershipDetailsResponse = organisationLeadershipDetailsService
				.updateOrganisationLeadershipDetails(request);		
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgLeadershipDetailsResponse>(),
				orgLeadershipDetailsResponse, Constants.ORG_LEADERSHIP_DETAILS_UPDATE_SUCCESS,
				Constants.ORG_LEADERSHIP_DETAILS_UPDATE_FAIL);
	}

	/**
	 * getOrganisationLeadershipDetails is to get the organisation leadership
	 * details
	 * 
	 * @param token
	 * @param accessInfo
	 * @param id
	 * @return
	 */
	@GetMapping("/leadership/{id}")
	public GoodPlatformResponseVO<OrgLeadershipDetailsResponse> getOrganisationLeadershipDetails(
			@PathVariable("id") String id) {
		OrgLeadershipDetailsResponse response = organisationLeadershipDetailsService
				.getOrganisationLeadershipDetails(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgLeadershipDetailsResponse>(), response,
				Constants.ORG_LEADERSHIP_DETAILS_FETCH_SUCCESS, Constants.ORG_LEADERSHIP_DETAILS_FETCH_FAIL);
	}

	@DeleteMapping("/leadership/{memberId}")
	public GoodPlatformResponseVO<Boolean> deleteOrganisationLeadershipDetails(@RequestParam String organisationId,
			@PathVariable("memberId") String memberId) {
		Boolean response = organisationLeadershipDetailsService.deleteLeadershipDetails(organisationId, memberId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), response,
				Constants.ORG_LEADERSHIP_DETAILS_DELETE_SUCCESS, Constants.ORG_LEADERSHIP_DETAILS_DELETE_FAIL);
	}
	
	@PutMapping("/leadership/aml-check")
	public GoodPlatformResponseVO<AmlCheckResponse> updateAmlCheck(@RequestBody AmlCheckRequest amlCheckRequest){
		AmlCheckResponse amlCheckResponse = organisationLeadershipDetailsService.updateAmlCheck(amlCheckRequest);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<AmlCheckResponse>(), amlCheckResponse,
				Constants.UPDATE_AML_CHECK_STATUS_SUCCESS, Constants.UPDATE_AML_CHECK_STATUS_FAIL);
	}
	
	@GetMapping("/leadership/{memberId}/aml-check")
	public GoodPlatformResponseVO<String> getAmlCheck(@PathVariable("memberId") String memberId){
		String amlCheckResponse = organisationLeadershipDetailsService.getAmlCheckStatus(memberId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), amlCheckResponse,
				Constants.FETCH_AML_CHECK_STATUS_SUCCESS, Constants.FETCH_AML_CHECK_STATUS_FAIL);
	}

	@PostMapping("/legal-documents")
	public GoodPlatformResponseVO<OrganisationLegalDetailsResponse> addLegalDocuments(
			@RequestBody OrganisationLegalDetailsRequest request) {
		OrganisationLegalDetailsResponse response = orgService.addLegalDocuments(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationLegalDetailsResponse>(), response,
				Constants.LEGAL_DOCUMENTS_ADD_SUCCESS, Constants.LEGAL_DOCUMENTS_ADD_FAIL);
	}

	@GetMapping("{organisationId}/legal-documents")
	public GoodPlatformResponseVO<OrganisationLegalDetailsResponse> getLegalDocuments(
			@PathVariable("organisationId") String organisationId) {
		OrganisationLegalDetailsResponse response = orgService.getLegalDocuments(organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationLegalDetailsResponse>(), response,
				Constants.ORG_LEGAL_DOCUMENTS_FETCH_SUCCESS, Constants.ORG_LEGAL_DOCUMENTS_FETCH_FAIL);
	}

	@PutMapping("/legal-documents")
	public GoodPlatformResponseVO<OrganisationLegalDetailsResponse> updateLegalDocuments(
			@RequestBody OrganisationLegalDetailsUpdateRequest request) {
		OrganisationLegalDetailsResponse response = orgService.updateLegalDocuments(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationLegalDetailsResponse>(), response,
				Constants.LEGAL_DOCUMENTS_UPDATE_SUCCESS, Constants.LEGAL_DOCUMENTS_UPDATE_FAIL);
	}

	/**
	 * addOrganisationBudgetDonorDetails is to add the budget donor history details
	 * of the organisation
	 * 
	 * @param accessInfo
	 * @param request
	 * @return
	 */
	@PostMapping("/budget-donor-history")
	public GoodPlatformResponseVO<OrgBudgetDonorResponse> addOrganisationBudgetDonorDetails(
			@RequestBody OrgBudgetDonorRequest request) {
		OrgBudgetDonorResponse orgBudgetDonorResponse = organisationBudgetDonorService
				.addBudgetDonorHistoryDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBudgetDonorResponse>(),
				orgBudgetDonorResponse, Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_ADD_SUCCESS,
				Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_ADD_FAIL);
	}

	/**
	 * updateOrganisationBudgetDonorDetails is to update the organization budget
	 * donor history details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@PutMapping("/budget-donor-history")
	public GoodPlatformResponseVO<OrgBudgetDonorResponse> updateOrganisationBudgetDonorDetails(
			@RequestBody OrgBudgetDonorRequest request) {
		OrgBudgetDonorResponse response = organisationBudgetDonorService.updateBudgetDonorHistoryDetails(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBudgetDonorResponse>(), response,
				Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_UPDATE_SUCCESS,
				Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_UPDATE_FAIL);
	}

	/**
	 * getOrganisationBudgetDonorDetails is to get the organization budget donor
	 * history details
	 * 
	 * @param token,     Should contain the access token
	 * @param accessInfo
	 * @param request,   contains the data to be inserted
	 * @return OrgBasicDetailsResponse which contains the organizational details
	 */
	@GetMapping("/budget-donor-history/{id}")
	public GoodPlatformResponseVO<OrgBudgetDonorResponse> getOrganisationBudgetDonorDetails(
			@PathVariable("id") String id) {
		OrgBudgetDonorResponse response = organisationBudgetDonorService.getBudgetDonorHistoryDetails(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBudgetDonorResponse>(), response,
				Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_FETCH_SUCCESS,
				Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_FETCH_FAIL);
	}

	@PutMapping("/signed-aggrement")
	public GoodPlatformResponseVO<OrgBasicDetailsResponse> updateSignedAggrement(
			@RequestBody SignedAggrementRequest request) {
		OrgBasicDetailsResponse response = orgService.updateSignedAggrement(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBasicDetailsResponse>(), response,
				Constants.ORG_BASIC_DETAILS_UPDATE_SUCCESS, Constants.ORG_BASIC_DETAILS_UPDATE_FAIL);
	}

	@GetMapping("{organisationId}/signed-agreement")
	public GoodPlatformResponseVO<ExistenceResponseDTO> existsBySignedAggrement(
			@PathVariable("organisationId") String organisationId) {
		ExistenceResponseDTO response = orgService.existBySignedAggrement(organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<ExistenceResponseDTO>(), response,
				Constants.SIGNED_AGREEMENT_FETCH_SUCCESS, Constants.SIGNED_AGREEMENT_FETCH_FAILED);
	}
    
	@PostMapping("/{organisationId}/submit")
	public GoodPlatformResponseVO<OrgBasicDetailsResponse> submitApprovalStatus(@PathVariable("organisationId") String organisationId) {
		OrgBasicDetailsResponse response = orgService.submitApprovalStatus(organisationId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgBasicDetailsResponse>(), response,
				Constants.APPROVAL_STATUS_ADD_SUCCESS, Constants.APPROVAL_STATUS_ADD_FAIL);
	}

	@PostMapping("/all")
	public GoodPlatformResponseVO<List<OrgBasicDetailsResponse>> getAllOrgByIds(@RequestBody List<String> organsationIds) {
		List<OrgBasicDetailsResponse> response = orgService.getAllOrgByIds(organsationIds);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<OrgBasicDetailsResponse>>(), response,
				Constants.ORG_BASIC_DETAILS_FETCH_SUCCESS, Constants.ORG_BASIC_DETAILS_FETCH_FAIL);
	}
	
	/**
	 * @implNote This method wrote for mg-beneficiary service, for the API:
	 *           /v1/completion-detailed
	 * @author Arya C Achari
	 * @since 31-Dec-2021
	 * @param name
	 * @return
	 */
	@GetMapping("/by-name/{name}")
	public GoodPlatformResponseVO<OrgByNameResponse> getOrgBasicByName(@PathVariable("name") String name) {
		OrgByNameResponse response = orgService.getOrganisationByName(name);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrgByNameResponse>(), response,
				Constants.ORG_BASIC_DETAILS_FETCH_SUCCESS, Constants.ORG_BASIC_DETAILS_FETCH_FAIL);
	}
	
	@DeleteMapping("{organisationId}/budget-donor-history/{donorId}")
	public GoodPlatformResponseVO<Boolean> deleteOrganisationDonorHistory(@PathVariable("organisationId") String organisationId,@PathVariable("donorId") String donorId) {
		Boolean response = organisationBudgetDonorService.deleteOrgDonorHistory(organisationId,donorId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), response,
				Constants.ORG_DONOR_DETAILS_DELETE_SUCCESS, Constants.ORG_DONOR_DETAILS_DELETE_FAIL);
	}
	
	@GetMapping("/organisation-types")
	public GoodPlatformResponseVO<List<String>>  getOrgOrganisationTypes(@RequestParam (required = false) String registerAs) {
		List<String> orgOrganisationTypes = orgService.getOrgOrganisationTypes(registerAs);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<String>>(), orgOrganisationTypes,
				Constants.ORG_TYPES_FETCH_SUCCESS, Constants.ORG_TYPES_FETCH_FAIL);
	}
	
	@GetMapping("/registered-as")
	public GoodPlatformResponseVO<List<String>> getRegisteredAs(@RequestParam (required = false) String organisationTypes) {
		List<String> registerTypes = orgService.getRegisteredAs(organisationTypes);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<String>>(), registerTypes,
				Constants.ORG_TYPES_FETCH_SUCCESS, Constants.ORG_TYPES_FETCH_FAIL);
	}
	
	@PostMapping("/organsation-types")
	public GoodPlatformResponseVO<List<OrganisationTypes>> addOrgansationType(@RequestBody List<OrganisationTypesRequest> organisationTypesRequest) {
		List<OrganisationTypes> response = orgService.addOrgansationType(organisationTypesRequest);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<OrganisationTypes>>(), response,
				Constants.ORG_BASIC_DETAILS_FETCH_SUCCESS, Constants.ORG_BASIC_DETAILS_FETCH_FAIL);
	}
	
	@GetMapping("/organisation-members-roles")
	public GoodPlatformResponseVO<List<String>> getOrganisationMembersRoles(@RequestParam (required = false) String organisationTypes) {
		List<String> registerTypes = orgService.getOrganisationMembersRoles(organisationTypes);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<String>>(), registerTypes,
				Constants.ORG_MEMBER_ROLE_FETCH_SUCCESS, Constants.ORG_MEMBER_ROLE_FETCH_FAIL);
	}
	
	@PostMapping("/organisation-members-roles")
	public GoodPlatformResponseVO<List<OrganisationMembersRoles>> addOrganisationMembersRolesRequest(@RequestBody List<OrganisationMembersRolesRequest> organisationMembersRolesRequest) {
		List<OrganisationMembersRoles> response = orgService.addOrganisationMembersRolesRequest(organisationMembersRolesRequest);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<OrganisationMembersRoles>>(), response,
				Constants.ORG_MEMBER_ROLE_ADDED_SUCCESS, Constants.ORG_MEMBER_ROLE_ADDED_FAIL);
	}
	
}
