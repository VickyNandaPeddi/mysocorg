package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;
import com.good.platform.entity.validator.OrganisationReview;
import com.good.platform.entity.validator.ValidatorDetails;
import com.good.platform.entity.validator.ValidatorOrganisationReviewMapping;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.validator.OrganisationReviewMapper;
import com.good.platform.mapper.validator.ValidatorDetailsMapper;
import com.good.platform.projection.OrganisationDetailsProjection;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.repository.validator.OrganisationReviewRepository;
import com.good.platform.repository.validator.ValidatorDetailsRepository;
import com.good.platform.repository.validator.ValidatorOrganisationReviewMappingRepository;
import com.good.platform.request.dto.validator.OrganisationReviewDTO;
import com.good.platform.request.dto.validator.ValidatorDetailsDTO;
import com.good.platform.request.dto.validator.ValidatorDoccumentsDTO;
import com.good.platform.response.PaginatedResponse;
import com.good.platform.response.dto.OrganisationNameDto;
import com.good.platform.response.validator.OrganisationDetailsDTO;
import com.good.platform.response.validator.OrganisationReviewResponseDTO;
import com.good.platform.response.validator.OrganisationStatusResponseDTO;
import com.good.platform.response.validator.ReviewMappingRequestDTO;
import com.good.platform.response.validator.ReviewMappingResponseDTO;
import com.good.platform.response.validator.ValidatorResponseDTO;
import com.good.platform.service.ValidatorDetailsService;
import com.good.platform.utility.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidatorDetailsServiceImpl implements ValidatorDetailsService {

	private final ValidatorDetailsRepository validatorRepo;
	private final UserRepository userRepo;
	private final ValidatorDetailsMapper detailsMapper;
	private final SecurityUtil securityUtil;
	private final OrganisationReviewRepository orgReviewRepo;
	private final OrganisationReviewMapper reviewMapper;
	private final OrganisationRepository orgRepo;
	private final ValidatorOrganisationReviewMappingRepository validatorOrgRepo;

	// Dont put @Transactional for this method bcoz it uses many places
	@Override
	public ValidatorResponseDTO addValidatorDetails(ValidatorDetailsDTO validatorDetailsDTO, String validatorId) {

		ValidatorDetails validatorDetails = validatorRepo.findByUser_Id(validatorId);
		if (validatorDetails == null) {
			validatorDetails = new ValidatorDetails();
			validatorDetails = detailsMapper.toEntity(validatorDetailsDTO, validatorId);
			validatorDetails.setCreatedBy(securityUtil.getCurrentUser());
		} else {
			validatorDetails.setModifiedAt(new Date().getTime());
			validatorDetails.setLastModifiedBy(securityUtil.getCurrentUser());
			validatorDetails.setOrganisation(validatorDetailsDTO.getOrganisation());
		}

		validatorDetails = validatorRepo.save(validatorDetails);
		return detailsMapper.toValidatorResponseDTO(validatorDetails, validatorDetails.getUser());
	}

	@Override
	public ValidatorResponseDTO updateValidatorDetails(String validatorId, ValidatorDetailsDTO validatorDetailsDTO) {
		if (validatorId == null || validatorId.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.USER_ID_INVALID);
		}
		User savedValidator = userRepo.findById(validatorId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND));

		try {
			savedValidator.setDetailsUpdated(validatorDetailsDTO.getDetailsUpdated());
			savedValidator.setFirstName(validatorDetailsDTO.getFirstName());
			savedValidator.setLastName(validatorDetailsDTO.getLastName());
			savedValidator.setMiddleName(validatorDetailsDTO.getMiddleName());
			savedValidator.setModifiedAt(new Date().getTime());
			savedValidator.setLastModifiedBy(securityUtil.getCurrentUser());
			User user = userRepo.save(savedValidator);
			String organisation = null;
			if (validatorDetailsDTO.getOrganisation() != null && !validatorDetailsDTO.getOrganisation().isEmpty()) {
				ValidatorResponseDTO result = addValidatorDetails(validatorDetailsDTO, validatorId);
				organisation = result.getOrganisation();
			}
			ValidatorResponseDTO response = new ValidatorResponseDTO();
			response.setId(user.getId());
			response.setFirstName(user.getFirstName());
			response.setLastName(user.getLastName());
			response.setMiddleName(user.getMiddleName());
			response.setOrganisation(organisation);
			response.setPhone(user.getPhone());
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.USER_ADD_FAIL);
		}
	}

	@Override
	@Transactional
	public ValidatorResponseDTO getValidatorDetails(String validatorId) {
		if (StringUtils.isEmpty(validatorId)) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.VALIDATOR_ID_NOT_FOUND);
		}
		try {
			ValidatorDetails validatorDetails = validatorRepo.findByUser_Id(validatorId);
			if (Objects.isNull(validatorDetails)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.VALIDATOR_DETAILS_NOT_FOUND);
			}
			return detailsMapper.toValidatorResponseDTO(validatorDetails, validatorDetails.getUser());
		} catch (Exception exception) {
			if (Constants.VALIDATOR_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.VALIDATOR_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.VALIDATOR_DETAILS_FETCH_FAIL);
			}
		}

	}

	@Override
	public ValidatorResponseDTO updateValidatorDoccuments(String validatorId,
			ValidatorDoccumentsDTO validatorDoccumentsDTO) {
		User user = userRepo.findById(validatorId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND));

		ValidatorDetails validatorDetails = validatorRepo.findByUser_Id(validatorId);

		if (validatorDetails == null) {
			validatorDetails = new ValidatorDetails();
			validatorDetails.setOrganisation(validatorDoccumentsDTO.getOrganisation());
			validatorDetails.setUser(user);
			validatorDetails.setCreatedBy(securityUtil.getCurrentUser());
		} else {
			validatorDetails.setModifiedAt(new Date().getTime());
			validatorDetails.setLastModifiedBy(securityUtil.getCurrentUser());
		}
		validatorDetails.setDocument1Name(validatorDoccumentsDTO.getDocument1Name());
		validatorDetails.setDocument1Url(validatorDoccumentsDTO.getDocument1Url());
		validatorDetails.setDocument2Name(validatorDoccumentsDTO.getDocument2Name());
		validatorDetails.setDocument2Url(validatorDoccumentsDTO.getDocument2Url());
		validatorDetails = validatorRepo.save(validatorDetails);
		return detailsMapper.toValidatorResponseDTO(validatorDetails, validatorDetails.getUser());

	}

	/**
	 * @implNote Search by organization, filter by approval status and created at.
	 * @modified Arya C Achari
	 * @since 19-20 Jan-2022
	 * @return PaginatedResponse
	 * 
	 */
	@Override
	public PaginatedResponse<OrganisationDetailsDTO> getAllOrganisations(Integer pageNumber, Integer pageSize,
			String name, String approvalStatus, String createdAt) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
		List<OrganisationDetailsDTO> response = new ArrayList<>();
		Long totalElements = 0L;
		Integer totalPages = 0;
		try {
			Page<OrganisationDetailsProjection> organisationList = this.orgRepo.findAllOrganization(pageable, name,
					approvalStatus, createdAt);
			if (organisationList.getContent() != null) {
				organisationList.getContent().forEach(item -> {
					response.add(new OrganisationDetailsDTO(item));
				});
			}
			totalElements = organisationList.getTotalElements();
			totalPages = organisationList.getTotalPages();
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORGANISATION_LIST_FETCH_FAIL);
		}
		return new PaginatedResponse(totalElements, totalPages, pageSize, pageNumber + 1, response.size(), response);
	}

	@Override
	public ReviewMappingResponseDTO startReview(ReviewMappingRequestDTO reviewMappingDTO) {

		User validator = userRepo.findById(reviewMappingDTO.getValidatorId())
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.USER_NOT_FOUND));

		Organisation savedOrg = orgRepo.findById(reviewMappingDTO.getOrganisationId())
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));

		int existingData = validatorOrgRepo.countByUserIdAndOrganisationId(reviewMappingDTO.getOrganisationId(),
				reviewMappingDTO.getValidatorId());
		if (existingData > 0) {
			ReviewMappingResponseDTO response = new ReviewMappingResponseDTO("", reviewMappingDTO.getValidatorId(),
					reviewMappingDTO.getOrganisationId());
			return response;
		}
		ValidatorOrganisationReviewMapping validatorOrgMapping = new ValidatorOrganisationReviewMapping();
		validatorOrgMapping.setOrganisation(savedOrg);
		validatorOrgMapping.setUser(validator);
		validatorOrgMapping.setCreatedAt(new Date().getTime());
		validatorOrgMapping.setCreatedBy(securityUtil.getCurrentUser());
		validatorOrgMapping = validatorOrgRepo.save(validatorOrgMapping);
		ReviewMappingResponseDTO response = new ReviewMappingResponseDTO(validatorOrgMapping.getId(),
				validatorOrgMapping.getUser().getId(), validatorOrgMapping.getOrganisation().getId());
		return response;
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateOverview(String organisationId, OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));

		OrganisationReview review = null;
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setOverviewStatus(reviewRequestDTO.getOverviewStatus());
		review.setOverviewComment(reviewRequestDTO.getOverviewComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateLeadershipReview(String organisationId,
			OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		OrganisationReview review = null;
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setLeadershipStatus(reviewRequestDTO.getLeadershipStatus());
		review.setLeadershipComment(reviewRequestDTO.getLeadershipComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateLocationReview(String organisationId,
			OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		OrganisationReview review = null;
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setLocationStatus(reviewRequestDTO.getLocationStatus());
		review.setLocationComment(reviewRequestDTO.getLocationComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updatePurposeReview(String organisationId,
			OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		OrganisationReview review = null;
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setPurposeStatus(reviewRequestDTO.getPurposeStatus());
		review.setPurposeComment(reviewRequestDTO.getPurposeComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateTrackRecordHistory(String organisationId,
			OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		OrganisationReview review = null;
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setTrackRecordHistoryStatus(reviewRequestDTO.getTrackRecordHistoryStatus());
		review.setTrackRecordHistoryComment(reviewRequestDTO.getTrackRecordHistoryComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateTrackRecordBeneficiary(String organisationId,
			OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		OrganisationReview review = null;
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setTrackRecordBeneficiaryStatus(reviewRequestDTO.getTrackRecordBeneficiaryStatus());
		review.setTrackRecordBeneficiaryComment(reviewRequestDTO.getTrackRecordBeneficiaryComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateFinancialDoc(String organisationId,
			OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		OrganisationReview review = null;
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setFinancialDocumentsStatus(reviewRequestDTO.getFinancialDocumentsStatus());
		review.setFinancialDocumentsComment(reviewRequestDTO.getFinancialDocumentsComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateLegalDoc(String organisationId, OrganisationReviewDTO reviewRequestDTO) {
		OrganisationReview review = null;
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		if (orgReviewRepo.existsByOrganisationId(organisationId)) {
			review = orgReviewRepo.findByOrganisationId(organisationId);
			review.setModifiedAt(new Date().getTime());
			review.setLastModifiedBy(securityUtil.getCurrentUser());
		} else {
			review = new OrganisationReview();
			review.setOrganisation(savedOrg);
			review.setCreatedAt(new Date().getTime());
			review.setCreatedBy(securityUtil.getCurrentUser());
		}
		review.setLegalDocumentsStatus(reviewRequestDTO.getLegalDocumentsStatus());
		review.setFinancialDocumentsComment(reviewRequestDTO.getLegalDocumentsComment());
		review = orgReviewRepo.save(review);
		return reviewMapper.toResponseDTO(review);
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO updateOrganisationStatus(String organisationId,
			OrganisationReviewDTO reviewRequestDTO) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));

		savedOrg.setModifiedAt(new Date().getTime());
		savedOrg.setLastModifiedBy(securityUtil.getCurrentUser());
		savedOrg.setApprovalStatus(reviewRequestDTO.getStatus());
		savedOrg.setApprovalStatusComment(reviewRequestDTO.getComment());
		savedOrg = orgRepo.save(savedOrg);
		OrganisationReview review = orgReviewRepo.findByOrganisation_Id(savedOrg.getId());
		OrganisationReviewResponseDTO response = reviewMapper.toResponseDTO(review);
		response.setStatus(savedOrg.getApprovalStatus());
		response.setComment(savedOrg.getApprovalStatusComment());
		return response;
	}

	@Override
	@Transactional
	public OrganisationStatusResponseDTO getOrgApprovalStatus(String organisationId) {
		Organisation savedOrg = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND));
		OrganisationStatusResponseDTO response = new OrganisationStatusResponseDTO(savedOrg.getId(),
				savedOrg.getApprovalStatus());
		return response;
	}

	@Override
	@Transactional
	public OrganisationReviewResponseDTO getOrganisationReview(String organisationId) {
		OrganisationReview review = orgReviewRepo.findByOrganisation_Id(organisationId);
		if (Objects.isNull(review)) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORGANISATION_REVIEW_NOT_FOUND);
		}
		OrganisationReviewResponseDTO response = reviewMapper.toResponseDTO(review);
		return response;
	}

	/**
	 * @implNote Return all organization's id and name, order by name in ascending.
	 *           Search given by organization name
	 * @author Arya C Achari
	 * @since 21-Jan-2022
	 * 
	 * @return
	 */
	@Override
	public List<OrganisationNameDto> getAllOrganisationByIdAndName(String name) {
		try {
			return orgRepo.findAllOrganisationByIdAndName(name);
		} catch (Exception ex) {
			log.error(ExceptionUtils.getMessage(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORGANISATION_LIST_FETCH_FAIL);
		}
	}

}
