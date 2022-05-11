package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationMembers;
import com.good.platform.entity.OrganisationMembersCount;
import com.good.platform.entity.OrganisationMembersDetails;
import com.good.platform.enums.AmlVerificationStatus;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.OrganisationMembersMapper;
import com.good.platform.model.OrganisationMembersCountModel;
import com.good.platform.model.OrganisationMembersModel;
import com.good.platform.model.OrganisationMembersModelRequest;
import com.good.platform.repository.OrganisationMembersCountRepository;
import com.good.platform.repository.OrganisationMembersDetailsRepository;
import com.good.platform.repository.OrganisationMembersRepository;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.request.dto.AmlCheckRequest;
import com.good.platform.request.dto.OrgLeadershipDetailsRequest;
import com.good.platform.response.dto.AmlCheckResponse;
import com.good.platform.response.dto.OrgLeadershipDetailsResponse;
import com.good.platform.service.OrganisationLeadershipDetailsService;
import com.good.platform.utility.CommonUtility;
import com.good.platform.utility.Constants;
import com.good.platform.utility.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * OrganisationLeadershipDetailsServiceImpl is to handle all the members details
 * of the organisation
 * 
 * @author Mohamedsuhail S
 *
 */
@Service
@Slf4j
public class OrganisationLeadershipDetailsServiceImpl implements OrganisationLeadershipDetailsService {

	@Autowired
	OrganisationRepository organisationRepo;

	@Autowired
	OrganisationMembersRepository organisationMembersRepository;

	@Autowired
	OrganisationMembersMapper organisationMembersMapper;
	@Autowired
	SecurityUtil securityUtil;

	@Autowired
	OrganisationMembersDetailsRepository organisationMembersDetailsRepository;

	@Autowired
	OrganisationMembersCountRepository organisationMembersCountRepository;

	/**
	 * addOrganisationLeadershipDetails is to add the members list
	 * 
	 * @param orgLeadershipDetailsRequest
	 * @return
	 */
	@Override
	@Transactional
	public OrgLeadershipDetailsResponse addOrganisationLeadershipDetails(
			OrgLeadershipDetailsRequest orgLeadershipDetailsRequest) {
		log.debug("Organisation leadership details add operation starts");
		if (orgLeadershipDetailsRequest.getOrganisationId() == null
				|| orgLeadershipDetailsRequest.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		Organisation organisation = getOrganisationDetails(orgLeadershipDetailsRequest.getOrganisationId());
		try {
			List<OrganisationMembersModel> organisationMembersModelList = new ArrayList<>();
			for (OrganisationMembersModelRequest organisationMembersModel : orgLeadershipDetailsRequest
					.getOrganisationMembersList()) {
				if (organisationMembersModel.getIdProofNumber() == null
						|| organisationMembersModel.getIdProofNumber().isEmpty()) {
					throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_ID_PROOF_NUMBER);
				}
				if (organisationMembersModel.getAmlCheck() != null
						&& !organisationMembersModel.getAmlCheck().isEmpty()) {
					if (!organisationMembersModel.getAmlCheck()
							.equalsIgnoreCase(AmlVerificationStatus.NOT_CHECKED.toString())) {
						if (!organisationMembersModel.getAmlCheck()
								.equalsIgnoreCase(AmlVerificationStatus.PASSED.toString())) {
							if (!organisationMembersModel.getAmlCheck()
									.equalsIgnoreCase(AmlVerificationStatus.FAILED.toString())) {
								throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
							}
						}
					}

				}
				OrganisationMembers organisationMembers = organisationMembersMapper
						.organisationMembersMapper(organisationMembersModel, organisation);
				if (CommonUtility.findLengthOfString(organisationMembersModel.getIdProofNumber()) > 0) {
					organisationMembers
							.setIdProofNumber(FileUtils.encrypt(organisationMembersModel.getIdProofNumber()));
				}
				organisationMembers.setAmlChecked(organisationMembersModel.getAmlCheck() != null
						&& !organisationMembersModel.getAmlCheck().isEmpty()
								? organisationMembersModel.getAmlCheck().toUpperCase()
								: AmlVerificationStatus.NOT_CHECKED.toString());
				organisationMembers.setCreatedBy(securityUtil.getCurrentUser());
				OrganisationMembers savedEntity = organisationMembersRepository.save(organisationMembers);
				savedEntity.setIdProofNumber(savedEntity.getIdProofNumber());
				organisationMembersModelList.add(organisationMembersMapper.organisationMembersMapper(savedEntity));
			}
			OrganisationMembersDetails organisationMembersDetails = new OrganisationMembersDetails();
			organisationMembersDetails = setOrganisationMembersDetails(orgLeadershipDetailsRequest,
					organisationMembersDetails);
			organisationMembersDetails.setOrganisation(organisation);
			organisationMembersDetails.setCreatedBy(securityUtil.getCurrentUser());
			organisationMembersDetails = organisationMembersDetailsRepository.save(organisationMembersDetails);
			OrgLeadershipDetailsResponse orgLeadershipDetailsResponse = organisationMembersMapper
					.organisationLeadershipResponseMapper(orgLeadershipDetailsRequest.getOrganisationId(),
							organisationMembersModelList);
			orgLeadershipDetailsResponse = getOrganisationMembersDetails(orgLeadershipDetailsResponse,
					organisationMembersDetails);
			if (orgLeadershipDetailsRequest.getOrganisationMembersCount() != null
					&& !orgLeadershipDetailsRequest.getOrganisationMembersCount().isEmpty()) {
				List<OrganisationMembersCount> organisationMembersCountList = new ArrayList<>();
				orgLeadershipDetailsRequest.getOrganisationMembersCount().forEach(organisationMembersCountLis -> {
					OrganisationMembersCount organisationMembersCount = new OrganisationMembersCount();
					organisationMembersCount.setCount(organisationMembersCountLis.getCount());
					organisationMembersCount.setType(organisationMembersCountLis.getType());
					organisationMembersCount.setCreatedBy(securityUtil.getCurrentUser());
					organisationMembersCount.setOrganisation(organisation);
					organisationMembersCountList.add(organisationMembersCount);
				});
				List<OrganisationMembersCount> organisationMembersCountListDB = organisationMembersCountRepository
						.saveAll(organisationMembersCountList);

				orgLeadershipDetailsResponse = getOrganisationMembersCount(organisationMembersCountListDB,
						orgLeadershipDetailsResponse);
			}
			log.debug("Organisation leadership details add operation ends");
			return orgLeadershipDetailsResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.AML_CHECK_STATUS_VALUE_INVALID.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_LEADERSHIP_DETAILS_ADD_FAIL);
			}
		}
	}

	private OrgLeadershipDetailsResponse getOrganisationMembersCount(
			List<OrganisationMembersCount> organisationMembersCountListDB,
			OrgLeadershipDetailsResponse orgLeadershipDetailsResponse) {
		List<OrganisationMembersCountModel> organisationMembersCountModelList = new ArrayList<>();
		organisationMembersCountListDB.forEach(organisationMembersCountList -> {
			OrganisationMembersCountModel organisationMembersCountModel = new OrganisationMembersCountModel();
			organisationMembersCountModel.setCount(organisationMembersCountList.getCount());
			organisationMembersCountModel.setType(organisationMembersCountList.getType());
			organisationMembersCountModel.setCountId(organisationMembersCountList.getId());
			organisationMembersCountModelList.add(organisationMembersCountModel);
		});
		orgLeadershipDetailsResponse.setOrganisationMembersCount(organisationMembersCountModelList);

		return orgLeadershipDetailsResponse;
	}

	private OrgLeadershipDetailsResponse getOrganisationMembersDetails(
			OrgLeadershipDetailsResponse orgLeadershipDetailsResponse,
			OrganisationMembersDetails organisationMembersDetails) {

		orgLeadershipDetailsResponse.setBoardOfDirectorsWorkIndependently(
				organisationMembersDetails.getBoardOfDirectorsWorkIndependently());
		orgLeadershipDetailsResponse.setExternalLegalCouncilForLegalAssistance(
				organisationMembersDetails.getExternalLegalCouncilForLegalAssistance());
		orgLeadershipDetailsResponse.setLegalDepartment(organisationMembersDetails.getLegalDepartment());
		orgLeadershipDetailsResponse.setNoOfPermanentEmployees(organisationMembersDetails.getNoOfPermanentEmployees());
		orgLeadershipDetailsResponse.setMemberDetailsId(organisationMembersDetails.getId());
		return orgLeadershipDetailsResponse;
	}

	private OrganisationMembersDetails setOrganisationMembersDetails(
			OrgLeadershipDetailsRequest orgLeadershipDetailsRequest,
			OrganisationMembersDetails organisationMembersDetails) {

		organisationMembersDetails.setExternalLegalCouncilForLegalAssistance(
				orgLeadershipDetailsRequest.getExternalLegalCouncilForLegalAssistance());
		organisationMembersDetails.setBoardOfDirectorsWorkIndependently(
				orgLeadershipDetailsRequest.getBoardOfDirectorsWorkIndependently());
		organisationMembersDetails.setNoOfPermanentEmployees(orgLeadershipDetailsRequest.getNoOfPermanentEmployees());
		organisationMembersDetails.setLegalDepartment(orgLeadershipDetailsRequest.getLegalDepartment());
		organisationMembersDetails.setId(orgLeadershipDetailsRequest.getMemberDetailsId());
		return organisationMembersDetails;
	}

	/**
	 * updateOrganisationLeadershipDetails is to update the members list
	 * 
	 * @param orgLeadershipDetailsRequest
	 * @return
	 */
	@Override
	@Transactional
	public OrgLeadershipDetailsResponse updateOrganisationLeadershipDetails(
			OrgLeadershipDetailsRequest orgLeadershipDetailsRequest) {
		log.debug("Organisation leadership details update operation starts");
		if (orgLeadershipDetailsRequest.getOrganisationId() == null
				|| orgLeadershipDetailsRequest.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		Organisation organisation = getOrganisationDetails(orgLeadershipDetailsRequest.getOrganisationId());
		try {
			String updateUser = securityUtil.getCurrentUser();
			List<OrganisationMembersModel> organisationMembersModelList = new ArrayList<>();
			for (OrganisationMembersModelRequest organisationMembersModel : orgLeadershipDetailsRequest
					.getOrganisationMembersList()) {
				if (organisationMembersModel.getIdProofNumber() == null
						|| organisationMembersModel.getIdProofNumber().isEmpty()) {
					throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_ID_PROOF_NUMBER);
				}
				if (organisationMembersModel.getAmlCheck() != null
						&& !organisationMembersModel.getAmlCheck().isEmpty()) {
					if (!organisationMembersModel.getAmlCheck()
							.equalsIgnoreCase(AmlVerificationStatus.NOT_CHECKED.toString())) {
						if (!organisationMembersModel.getAmlCheck()
								.equalsIgnoreCase(AmlVerificationStatus.PASSED.toString())) {
							if (!organisationMembersModel.getAmlCheck()
									.equalsIgnoreCase(AmlVerificationStatus.FAILED.toString())) {
								throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
							}
						}
					}
				}
				String encriptedIdNo = null;
				if (CommonUtility.findLengthOfString(organisationMembersModel.getIdProofNumber()) > 0) {
					encriptedIdNo = FileUtils.encrypt(organisationMembersModel.getIdProofNumber());
				}
				if (organisationMembersModel.getMemberId() == null
						|| organisationMembersModel.getMemberId().isEmpty()) {
					OrganisationMembers organisationMembers = organisationMembersMapper
							.organisationMembersMapper(organisationMembersModel, organisation);
					organisationMembers.setAmlChecked(organisationMembersModel.getAmlCheck() != null
							&& !organisationMembersModel.getAmlCheck().isEmpty()
									? organisationMembersModel.getAmlCheck().toUpperCase()
									: AmlVerificationStatus.NOT_CHECKED.toString());
					organisationMembers.setCreatedBy(updateUser);
					organisationMembers
							.setIdProofNumber(!StringUtils.isEmpty(organisationMembersModel.getIdProofNumber())
									? FileUtils.encrypt(organisationMembersModel.getIdProofNumber())
									: null);
					OrganisationMembers savedEntity = organisationMembersRepository.save(organisationMembers);
					savedEntity.setIdProofNumber(savedEntity.getIdProofNumber());
					organisationMembersModelList.add(organisationMembersMapper.organisationMembersMapper(savedEntity));
				} else {
					Optional<OrganisationMembers> organisationMembersOptional = organisationMembersRepository
							.findById(organisationMembersModel.getMemberId());
					if (!organisationMembersOptional.isEmpty()) {
						OrganisationMembers organisationMembers = organisationMembersMapper
								.organisationMembersMapper(organisationMembersModel, organisation);
						organisationMembers.setAmlChecked(organisationMembersModel.getAmlCheck() != null
								&& !organisationMembersModel.getAmlCheck().isEmpty()
										? organisationMembersModel.getAmlCheck().toUpperCase()
										: AmlVerificationStatus.NOT_CHECKED.toString());
						organisationMembers.setCreatedAt(organisationMembersOptional.get().getCreatedAt());
						organisationMembers.setCreatedBy(organisationMembersOptional.get().getCreatedBy());
						organisationMembers.setModifiedAt(new Date().getTime());
						organisationMembers.setLastModifiedBy(updateUser);
						organisationMembers
								.setIdProofNumber(!StringUtils.isEmpty(organisationMembersModel.getIdProofNumber())
										? FileUtils.encrypt(organisationMembersModel.getIdProofNumber())
										: null);
						OrganisationMembers savedEntity2 = organisationMembersRepository.save(organisationMembers);
						savedEntity2.setIdProofNumber(savedEntity2.getIdProofNumber());
						organisationMembersModelList
								.add(organisationMembersMapper.organisationMembersMapper(savedEntity2));
					}
				}
			}
			OrgLeadershipDetailsResponse orgLeadershipDetailsResponse = organisationMembersMapper
					.organisationLeadershipResponseMapper(orgLeadershipDetailsRequest.getOrganisationId(),
							organisationMembersModelList);
			if (orgLeadershipDetailsRequest.getMemberDetailsId() != null) {
				Optional<OrganisationMembersDetails> organisationMembersDetailsOptional = organisationMembersDetailsRepository
						.findById(orgLeadershipDetailsRequest.getMemberDetailsId());

				if (organisationMembersDetailsOptional.isPresent()) {
					OrganisationMembersDetails organisationMembersDetails = organisationMembersDetailsOptional.get();

					organisationMembersDetails = setOrganisationMembersDetails(orgLeadershipDetailsRequest,
							organisationMembersDetails);
					organisationMembersDetails.setModifiedAt(new Date().getTime());
					organisationMembersDetails.setLastModifiedBy(updateUser);
					organisationMembersDetails.setOrganisation(organisation);
					organisationMembersDetails = organisationMembersDetailsRepository.save(organisationMembersDetails);
					orgLeadershipDetailsResponse = getOrganisationMembersDetails(orgLeadershipDetailsResponse,
							organisationMembersDetails);
				} 
			}else {
				OrganisationMembersDetails organisationMembersDetails = new OrganisationMembersDetails();
				organisationMembersDetails = setOrganisationMembersDetails(orgLeadershipDetailsRequest,
						organisationMembersDetails);
				organisationMembersDetails.setCreatedBy(securityUtil.getCurrentUser());
				organisationMembersDetails.setOrganisation(organisation);
				organisationMembersDetails = organisationMembersDetailsRepository.save(organisationMembersDetails);
				orgLeadershipDetailsResponse = getOrganisationMembersDetails(orgLeadershipDetailsResponse,
						organisationMembersDetails);
			}
			if (orgLeadershipDetailsRequest.getOrganisationMembersCount() != null
					&& !orgLeadershipDetailsRequest.getOrganisationMembersCount().isEmpty()) {
				List<OrganisationMembersCount> organisationMembersCountList = new ArrayList<>();
				orgLeadershipDetailsRequest.getOrganisationMembersCount().forEach(organisationMembersCount -> {
					Optional<OrganisationMembersCount> organisationMembersCountExist = organisationMembersCountRepository
							.findById(organisationMembersCount.getCountId());
					if (organisationMembersCountExist.isPresent()) {
						OrganisationMembersCount organisationMembersCountExis = organisationMembersCountExist.get();
						organisationMembersCountExis.setCount(organisationMembersCount.getCount());
						organisationMembersCountExis.setType(organisationMembersCount.getType());
						organisationMembersCountExis.setCreatedBy(securityUtil.getCurrentUser());
						organisationMembersCountExis.setOrganisation(organisation);
						organisationMembersCountList.add(organisationMembersCountExis);
					} else {
						OrganisationMembersCount organisationMembersCountNew = new OrganisationMembersCount();
						organisationMembersCountNew.setCount(organisationMembersCount.getCount());
						organisationMembersCountNew.setType(organisationMembersCount.getType());
						organisationMembersCountNew.setCreatedBy(securityUtil.getCurrentUser());
						organisationMembersCountNew.setOrganisation(organisation);
						organisationMembersCountList.add(organisationMembersCountNew);
					}
				});
				List<OrganisationMembersCount> organisationMembersCountListDB = organisationMembersCountRepository
						.saveAll(organisationMembersCountList);
				orgLeadershipDetailsResponse = getOrganisationMembersCount(organisationMembersCountListDB,
						orgLeadershipDetailsResponse);
			}

			log.debug("Organisation leadership details update operation ends");
			return orgLeadershipDetailsResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.AML_CHECK_STATUS_VALUE_INVALID.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_LEADERSHIP_DETAILS_UPDATE_FAIL);
			}
		}
	}

	/**
	 * getOrganisationLeadershipDetails is to fetch the members list
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public OrgLeadershipDetailsResponse getOrganisationLeadershipDetails(String organisationId) {
		log.debug("Organisation leadership details fetch operation starts");
		Organisation organisation = getOrganisationDetails(organisationId);
		List<OrganisationMembersModel> organisationMembersList = new ArrayList<>();
		try {
			List<OrganisationMembers> organisationMembersData = organisationMembersRepository
					.findAllByOrganisationId(organisationId);
			for (OrganisationMembers organisationMembers : organisationMembersData) {

				organisationMembersList.add(organisationMembersMapper.organisationMembersMapper(organisationMembers));
			}
			OrganisationMembersDetails organisationMembersDetails = organisationMembersDetailsRepository
					.findByOrganisationId(organisationId);
			List<OrganisationMembersCount> organisationMembersCountDtlsList = organisationMembersCountRepository
					.findByOrganisationId(organisationId);

			OrgLeadershipDetailsResponse orgLeadershipDetailsResponse = organisationMembersMapper
					.organisationLeadershipResponseMapper(organisationId, organisationMembersList);
			if (!Objects.isNull(organisationMembersDetails)) {
				orgLeadershipDetailsResponse = getOrganisationMembersDetails(orgLeadershipDetailsResponse,
						organisationMembersDetails);
			}
			if (!organisationMembersCountDtlsList.isEmpty() && organisationMembersCountDtlsList != null) {
				orgLeadershipDetailsResponse = getOrganisationMembersCount(organisationMembersCountDtlsList,
						orgLeadershipDetailsResponse);
			}

			log.debug("Organisation leadership details fetch operation starts");
			return orgLeadershipDetailsResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_LEADERSHIP_DETAILS_FETCH_FAIL);
		}
	}

	@Override
	public AmlCheckResponse updateAmlCheck(AmlCheckRequest amlCheckRequest) {
		log.debug("Update the AML check verification");
		if (amlCheckRequest.getMemberId() == null || amlCheckRequest.getMemberId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.MEMBER_ID_MISSING);
		}
		if (amlCheckRequest.getOrganisationId() == null || amlCheckRequest.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		if (amlCheckRequest.getAmlChecked() == null || amlCheckRequest.getAmlChecked().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
		}

		if (amlCheckRequest.getAmlChecked() != null && !amlCheckRequest.getAmlChecked().isEmpty()) {
			if (!amlCheckRequest.getAmlChecked().equalsIgnoreCase(AmlVerificationStatus.NOT_CHECKED.toString())) {
				if (!amlCheckRequest.getAmlChecked().equalsIgnoreCase(AmlVerificationStatus.PASSED.toString())) {
					throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
				}
			}
		}
		try {
			getOrganisationDetails(amlCheckRequest.getOrganisationId());
			Optional<OrganisationMembers> organisationMember = organisationMembersRepository
					.findById(amlCheckRequest.getMemberId());
			if (organisationMember.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_LEADERSHIP_DETAILS_NOT_FOUND);
			}
			organisationMember.get()
					.setAmlChecked(amlCheckRequest.getAmlChecked() != null && !amlCheckRequest.getAmlChecked().isEmpty()
							? amlCheckRequest.getAmlChecked().toUpperCase()
							: AmlVerificationStatus.NOT_CHECKED.toString());
			organisationMember.get().setModifiedAt(new Date().getTime());
			organisationMember.get().setLastModifiedBy(securityUtil.getCurrentUser());
			OrganisationMembers saved = organisationMembersRepository.save(organisationMember.get());
			AmlCheckResponse response = organisationMembersMapper.mapAmlCheckResponse(saved,
					saved.getOrganisation().getId());
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.ORG_BASIC_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
			} else if (Constants.ORG_LEADERSHIP_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_LEADERSHIP_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.UPDATE_AML_CHECK_STATUS_FAIL);
			}
		}
	}

	@Override
	public String getAmlCheckStatus(String memberId) {
		log.debug("Fetch AML verification status");
		if (memberId == null || memberId.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.MEMBER_ID_MISSING);
		}
		try {
			Optional<OrganisationMembers> organisationMember = organisationMembersRepository.findById(memberId);
			if (organisationMember.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_LEADERSHIP_DETAILS_NOT_FOUND);
			}
			String amlCheckStatus = organisationMember.get().getAmlChecked();
			return amlCheckStatus;
		} catch (Exception exception) {
			if (Constants.ORG_LEADERSHIP_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_LEADERSHIP_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FETCH_AML_CHECK_STATUS_FAIL);
			}
		}
	}

	/**
	 * organisation is to get the existing organisational details
	 * 
	 * @param id
	 * @return
	 */
	private Organisation getOrganisationDetails(String id) {
		log.debug("Fetching organisation basic data starts");
		Optional<Organisation> organisation = organisationRepo.findById(id);
		if (organisation.isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
		}
		log.debug("Fetching organisation basic data ends");
		return organisation.get();
	}

	@Override
	@Transactional
	public Boolean deleteLeadershipDetails(String organisationId, String memberId) {
		if (organisationId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		if (memberId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.MEMBER_ID_MISSING);
		}
		try {
			organisationMembersRepository.deleteByIdAndOrganisation_Id(memberId, organisationId);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_LEADERSHIP_DETAILS_DELETE_FAIL);
		}

	}

}
