package com.good.platform.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.good.platform.request.dto.validator.OrganisationReviewDTO;
import com.good.platform.request.dto.validator.ValidatorDetailsDTO;
import com.good.platform.request.dto.validator.ValidatorDoccumentsDTO;
import com.good.platform.response.PaginatedResponse;
import com.good.platform.response.dto.OrganisationNameDto;
import com.good.platform.response.validator.OrganisationCountDTO;
import com.good.platform.response.validator.OrganisationDetailsDTO;
import com.good.platform.response.validator.OrganisationReviewResponseDTO;
import com.good.platform.response.validator.OrganisationStatusResponseDTO;
import com.good.platform.response.validator.ReviewMappingRequestDTO;
import com.good.platform.response.validator.ReviewMappingResponseDTO;
import com.good.platform.response.validator.ValidatorResponseDTO;

public interface ValidatorDetailsService {

	ValidatorResponseDTO updateValidatorDetails(String validatorId, ValidatorDetailsDTO validatorDetailsDTO);

	ValidatorResponseDTO getValidatorDetails(String validatorId);

	ValidatorResponseDTO updateValidatorDoccuments(String validatorId, ValidatorDoccumentsDTO validatorDoccumentsDTO);

	//OrganisationCountDTO getCountByStatus();
	
	ReviewMappingResponseDTO startReview(ReviewMappingRequestDTO reviewMappingDTO);

	ValidatorResponseDTO addValidatorDetails(ValidatorDetailsDTO validatorDetailsDTO, String validatorId);

	OrganisationReviewResponseDTO updateOverview(String organisationId, OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updateLeadershipReview(String organisationId, OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updateLocationReview(String organisationId, OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updatePurposeReview(String organisationId, OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updateTrackRecordHistory(String organisationId,
			OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updateTrackRecordBeneficiary(String organisationId,
			OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updateFinancialDoc(String organisationId, OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updateLegalDoc(String organisationId, OrganisationReviewDTO reviewRequestDTO);

	OrganisationReviewResponseDTO updateOrganisationStatus(String organisationId,
			OrganisationReviewDTO reviewRequestDTO);

	OrganisationStatusResponseDTO getOrgApprovalStatus(String organisationId);

	OrganisationReviewResponseDTO getOrganisationReview(String organisationId);

	/**
	 * @implNote Search by organization, filter by approval status and created at.
	 * @modified Arya C Achari
	 * @since 19-20 Jan-2022
	 * @return PaginatedResponse
	 * 
	 */
	PaginatedResponse<OrganisationDetailsDTO> getAllOrganisations(Integer pageNumber, Integer pageSize, String name,
			String approvalStatus, String createdAt);

	/**
	 * @implNote Return all organization's id and name, order by name in ascending.
	 *           Search given by organization name
	 * @author Arya C Achari
	 * @since 21-Jan-2022
	 * 
	 * @return
	 */
	List<OrganisationNameDto> getAllOrganisationByIdAndName(String name);

}
