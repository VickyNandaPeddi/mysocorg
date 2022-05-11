package com.good.platform.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.request.dto.OrganisationStatusRequestDto;
import com.good.platform.request.dto.validator.OrganisationReviewDTO;
import com.good.platform.request.dto.validator.ValidatorDetailsDTO;
import com.good.platform.request.dto.validator.ValidatorDoccumentsDTO;
import com.good.platform.response.PaginatedResponse;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.OrganisationNameDto;
import com.good.platform.response.dto.OrganisationStatusResponseDto;
import com.good.platform.response.validator.OrganisationCountDTO;
import com.good.platform.response.validator.OrganisationDetailsDTO;
import com.good.platform.response.validator.OrganisationReviewResponseDTO;
import com.good.platform.response.validator.OrganisationStatusResponseDTO;
import com.good.platform.response.validator.ReviewMappingRequestDTO;
import com.good.platform.response.validator.ReviewMappingResponseDTO;
import com.good.platform.response.validator.ValidatorResponseDTO;
import com.good.platform.service.OrganisationService;
import com.good.platform.service.ValidatorDetailsService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;
import com.thoughtworks.xstream.mapper.Mapper.Null;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/validator")
@RequiredArgsConstructor
@Slf4j
public class ValidatorController {

	private final ValidatorDetailsService validatorService;
	
	private final OrganisationService orgService;

	@PutMapping("/{validatorId}")
	public GoodPlatformResponseVO<ValidatorResponseDTO> updateValidatorDetails(@PathVariable String validatorId,
			@RequestBody ValidatorDetailsDTO validatorDetailsDTO) {
		ValidatorResponseDTO response = validatorService.updateValidatorDetails(validatorId, validatorDetailsDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<ValidatorResponseDTO>(), response,
				Constants.VALIDATOR_DETAILS_ADD_SUCCESS, Constants.VALIDATOR_DETAILS_ADD_FAIL);
	}

	@PutMapping("/{validatorId}/documents")
	public GoodPlatformResponseVO<ValidatorResponseDTO> updateValidatorDoccuments(@PathVariable String validatorId,
			@RequestBody ValidatorDoccumentsDTO validatorDoccumentsDTO) {
		ValidatorResponseDTO response = validatorService.updateValidatorDoccuments(validatorId, validatorDoccumentsDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<ValidatorResponseDTO>(), response,
				Constants.VALIDATOR_DETAILS_ADD_SUCCESS, Constants.VALIDATOR_DETAILS_ADD_FAIL);
	}

	@GetMapping("/{validatorId}")
	public GoodPlatformResponseVO<ValidatorResponseDTO> getValidatorDetails(@PathVariable String validatorId) {
		ValidatorResponseDTO response = validatorService.getValidatorDetails(validatorId);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<ValidatorResponseDTO>(), response,
				Constants.VALIDATOR_DETAILS_ADD_SUCCESS, Constants.VALIDATOR_DETAILS_ADD_FAIL);
	}

	@GetMapping("/status/count")
	public GoodPlatformResponseVO<OrganisationCountDTO> getCountsByStatus() {
		//OrganisationCountDTO response = validatorService.getCountByStatus();
		OrganisationCountDTO response = orgService.getCountByStatus();
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationCountDTO>(), response,
				Constants.ORGANISATION_COUNT_FETCH_SUCCESS, Constants.ORGANISATION_COUNT_FETCH_FAIL);
	}

	@GetMapping("/organisations")
	public GoodPlatformResponseVO<Page<OrganisationDetailsDTO>> getAllOrganisations(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize, 
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "approvalStatus", required = false) String approvalStatus,
			@RequestParam(value = "createdAt", required = false) String createdAt) {
		PaginatedResponse<OrganisationDetailsDTO> response = validatorService.getAllOrganisations(pageNumber, pageSize,
				name, approvalStatus, createdAt);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Page<OrganisationDetailsDTO>>(), response,
				Constants.ORGANISATION_LIST_FETCH_SUCCESS, Constants.ORGANISATION_LIST_FETCH_FAIL);
	}

	@PostMapping("/start/review")
	public GoodPlatformResponseVO<ReviewMappingResponseDTO> startReview(
			@RequestBody ReviewMappingRequestDTO reviewMappingDTO) {
		ReviewMappingResponseDTO response = validatorService.startReview(reviewMappingDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<ReviewMappingResponseDTO>(), response,
				Constants.START_REVIEW_SUCCESS, Constants.START_REVIEW_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/overview")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateOverviewReview(
			@PathVariable String organisationId, @RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updateOverview(organisationId, reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_OVERVIEW_REVIEW_ADD_SUCCESS, Constants.ORGANISATION_OVERVIEW_REVIEW_ADD_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/leadership")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateLeadershipReview(
			@PathVariable String organisationId, @RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updateLeadershipReview(organisationId,
				reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_LEADERSHIP_REVIEW_ADD_SUCCESS,
				Constants.ORGANISATION_LEADERSHIP_REVIEW_ADD_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/location")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateLocationReview(
			@PathVariable String organisationId, @RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updateLocationReview(organisationId,
				reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_LOCATION_REVIEW_ADD_SUCCESS, Constants.ORGANISATION_LOCATION_REVIEW_ADD_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/purpose")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updatePurposeReview(
			@PathVariable String organisationId, @RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updatePurposeReview(organisationId, reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_PURPOSE_REVIEW_ADD_SUCCESS, Constants.ORGANISATION_PURPOSE_REVIEW_ADD_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/track-record-history")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateTrackRecordHistory(
			@PathVariable String organisationId, @RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updateTrackRecordHistory(organisationId,
				reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_TRACK_RECORD_HISTORY_REVIEW_ADD_SUCCESS,
				Constants.ORGANISATION_TRACK_RECORD_HISTORY_REVIEW_ADD_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/track-record-beneficiary")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateTrackRecordBeneficiary(
			@PathVariable String organisationId, @RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updateTrackRecordBeneficiary(organisationId,
				reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_TRACK_RECORD_BENEFICIARY_REVIEW_ADD_SUCCESS,
				Constants.ORGANISATION_TRACK_RECORD_BENEFICIARY_REVIEW_ADD_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/financial-documents")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateFinancialDoc(@PathVariable String organisationId,
			@RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updateFinancialDoc(organisationId, reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_FINANCIAL_DOCCUMENT_REVIEW_ADD_SUCCESS,
				Constants.ORGANISATION_FINANCIAL_DOCCUMENT_REVIEW_ADD_FAIL);
	}

	@PutMapping("/organisation/{organisationId}/legal-doc")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateLegalDoc(@PathVariable String organisationId,
			@RequestBody OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReviewResponseDTO response = validatorService.updateLegalDoc(organisationId, reviewRequestDTO);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_LEGAL_DOC_ADD_SUCCESS, Constants.ORGANISATION_LEGAL_DOC_REVIEW_ADD_FAIL);
	}
	

	
	@SuppressWarnings("unchecked")
	@PutMapping("/organisation/{organisationId}")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> updateOrganisationStatus(@PathVariable String organisationId,
				@RequestBody OrganisationStatusRequestDto statusRequestDTO) {
		OrganisationStatusResponseDto response = orgService.updateStatus(organisationId, statusRequestDTO);
			return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORG_REVIEW_ADD_SUCCESS, Constants.ORG_REVIEW_ADD_FAIL);
	}
	
	@GetMapping("/organisation/{organisationId}/status")
	public GoodPlatformResponseVO<OrganisationStatusResponseDTO> getOrganisationStatus(@PathVariable String organisationId) {
		OrganisationStatusResponseDTO response =  validatorService.getOrgApprovalStatus(organisationId);
			return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_APPROVAL_STATUS_FETCH_SUCCESS, Constants.ORGANISATION_APPROVAL_STATUS_FETCH_FAIL);
	}
	@GetMapping("/organisation/{organisationId}/review")
	public GoodPlatformResponseVO<OrganisationReviewResponseDTO> getOrganisationReview(@PathVariable String organisationId) {
		OrganisationReviewResponseDTO response =  validatorService.getOrganisationReview(organisationId);
			return ResponseHelper.createResponse(new GoodPlatformResponseVO<OrganisationReviewResponseDTO>(), response,
				Constants.ORGANISATION_REVIEW_FETCH_SUCCESS, Constants.ORGANISATION_REVIEW_FETCH_FAIL);
	}
	
	/**
	 * @apiNote Return all organization's id and name, order by name in ascending.
	 *          Search given by organization name
	 * @author Arya C Achari
	 * @since 21-Jan-2022
	 * @return
	 */
	@GetMapping("/list/organisations")
	public GoodPlatformResponseVO<List<OrganisationNameDto>> getAllOrganisationByIdAndName(
			@RequestParam(value = "name", required = false) String name) {
		List<OrganisationNameDto> response = validatorService.getAllOrganisationByIdAndName(name);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<OrganisationNameDto>>(), response,
				Constants.ORGANISATION_COUNT_FETCH_SUCCESS, Constants.ORGANISATION_COUNT_FETCH_FAIL);
	}
}
