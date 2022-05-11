package com.good.platform.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.good.platform.entity.BeneficiaryGmapLocations;
import com.good.platform.entity.BeneficiaryImpactedHistory;
import com.good.platform.entity.BeneficiaryLocations;
import com.good.platform.entity.LastAuditedStatements;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.Organisation12A80G;
import com.good.platform.entity.OrganisationAddress;
import com.good.platform.entity.OrganisationBeneficiaryHistory;
import com.good.platform.entity.OrganisationBeneficiaryTargets;
import com.good.platform.entity.OrganisationBudgetHistory;
import com.good.platform.entity.OrganisationDonorHistory;
import com.good.platform.entity.OrganisationFCRADetails;
import com.good.platform.entity.OrganisationFinancialDetails;
import com.good.platform.entity.OrganisationLegalDetails;
import com.good.platform.entity.OrganisationMembers;
import com.good.platform.entity.OrganisationMembersRoles;
import com.good.platform.entity.OrganisationMissionStatements;
import com.good.platform.entity.OrganisationSector;
import com.good.platform.entity.OrganisationTypes;
import com.good.platform.entity.User;
import com.good.platform.entity.UserOrganisationMapping;
import com.good.platform.enums.AmlVerificationStatus;
import com.good.platform.enums.SectorType;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.BenficiaryLocationsMapper;
import com.good.platform.mapper.OrganisationBasicDetailsMapper;
import com.good.platform.mapper.OrganisationMapper;
import com.good.platform.mapper.OrganisationMembersMapper;
import com.good.platform.mapper.UserDataMapper;
import com.good.platform.repository.BeneficiaryGmapLocationsRepository;
import com.good.platform.repository.BeneficiaryImpactedHistoryRepository;
import com.good.platform.repository.BeneficiaryLocationsRepository;
import com.good.platform.repository.FinancialYearsRepository;
import com.good.platform.repository.LastAuditedStatementsRepository;
import com.good.platform.repository.Organisation12A80GRepository;
import com.good.platform.repository.OrganisationAddressRepository;
import com.good.platform.repository.OrganisationBeneficiaryHistoryRepository;
import com.good.platform.repository.OrganisationBeneficiaryTargetsRepository;
import com.good.platform.repository.OrganisationBudgetHistoryRepository;
import com.good.platform.repository.OrganisationDonorHistoryRepository;
import com.good.platform.repository.OrganisationFCRADetailsRepository;
import com.good.platform.repository.OrganisationFinancialDetailsRepository;
import com.good.platform.repository.OrganisationLegalDetailsRepository;
import com.good.platform.repository.OrganisationMembersRepository;
import com.good.platform.repository.OrganisationMembersRolesRepository;
import com.good.platform.repository.OrganisationMissionStatementsRepository;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.OrganisationSectorRepository;
import com.good.platform.repository.OrganisationTypeRepository;
import com.good.platform.repository.UserOrganisationMappingRespository;
import com.good.platform.repository.UserRepository;
import com.good.platform.request.OrganisationMembersRolesRequest;
import com.good.platform.request.OrganisationTypesRequest;
import com.good.platform.request.dto.BeneficiaryGmapLocationsRequest;
import com.good.platform.request.dto.BeneficiaryImpactedHistoryRequest;
import com.good.platform.request.dto.BeneficiaryLocationsRequest;
import com.good.platform.request.dto.LastAuditedStatementsRequest;
import com.good.platform.request.dto.OrgBasicDetailsRequest;
import com.good.platform.request.dto.OrganisationBeneficiaryHistoryRequest;
import com.good.platform.request.dto.OrganisationFinancialDetailsRequest;
import com.good.platform.request.dto.OrganisationLegalDetailsRequest;
import com.good.platform.request.dto.OrganisationStatusRequestDto;
import com.good.platform.request.dto.put.BeneficiaryImpactedHistoryUpdateRequest;
import com.good.platform.request.dto.put.LastAuditedStatementsUpdateRequest;
import com.good.platform.request.dto.put.OrganisationBeneficiaryHistoryUpdateRequest;
import com.good.platform.request.dto.put.OrganisationFinancialDetailsUpdateRequest;
import com.good.platform.request.dto.put.OrganisationLegalDetailsUpdateRequest;
import com.good.platform.request.dto.put.SignedAggrementRequest;
import com.good.platform.response.ExistenceResponseDTO;
import com.good.platform.response.OrgByNameResponse;
import com.good.platform.response.OrganisationLegalDetailsResponse;
import com.good.platform.response.dto.BeneficiaryGmapLocationsResponse;
import com.good.platform.response.dto.BeneficiaryImpactedHistoryResponse;
import com.good.platform.response.dto.BeneficiaryLocationsResponse;
import com.good.platform.response.dto.LastAuditedStatementsResponse;
import com.good.platform.response.dto.OrgBasicDetailsResponse;
import com.good.platform.response.dto.OrgPercentageCompletionResponse;
import com.good.platform.response.dto.OrganisationBeneficiaryHistoryResponse;
import com.good.platform.response.dto.OrganisationFinancialDetailsResponse;
import com.good.platform.response.dto.OrganisationStatusResponseDto;
import com.good.platform.response.validator.OrganisationCountDTO;
import com.good.platform.service.CountriesService;
import com.good.platform.service.DocumentService;
import com.good.platform.service.OrganisationService;
import com.good.platform.utility.CommonUtility;
import com.good.platform.utility.Constants;
import com.good.platform.utility.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * OrganisationServiceImpl is to handle the Data persistence in organisation
 * table
 * 
 * @author Mohamedsuhail S
 *
 */
@Service
@Slf4j
public class OrganisationServiceImpl implements OrganisationService {
	@Autowired
	OrganisationRepository orgRepo;
	@Autowired
	OrganisationMapper organisationMapper;
	@Autowired
	OrganisationBeneficiaryHistoryRepository beneficiaryHistoryRepo;
	@Autowired
	BeneficiaryImpactedHistoryRepository beneficiaryImpactedHistoryRepo;
	@Autowired
	FinancialYearsRepository financialYearsRepo;
	@Autowired
	OrganisationBeneficiaryTargetsRepository organisationBeneficiaryTargetsRepo;
	@Autowired
	OrganisationFinancialDetailsRepository organisationFinancialDetailsRepo;
	@Autowired
	LastAuditedStatementsRepository lastAuditedStatementsRepo;
	@Autowired
	OrganisationMembersRepository organisationMembersRepo;

	@Autowired
	CountriesService countriesService;

	@Autowired
	OrganisationBasicDetailsMapper organisationBasicDetailsMapper;

	@Autowired
	OrganisationMembersMapper organisationMembersMapper;

	@Autowired
	OrganisationLegalDetailsRepository organisationLegalDetailsRepo;

	@Autowired
	SecurityUtil securityUtil;
	@Autowired

	OrganisationAddressRepository orgAddressRepo;

	@Autowired
	OrganisationMissionStatementsRepository missionRepo;
	@Autowired
	OrganisationDonorHistoryRepository donorRepo;
	@Autowired
	OrganisationBudgetHistoryRepository budgetRepo;
	@Autowired
	OrganisationBeneficiaryHistoryRepository historyRepo;

	@Autowired
	OrganisationFinancialDetailsRepository financialRepo;
	@Autowired
	LastAuditedStatementsRepository lastAuiditedStatementsRepo;

	@Autowired
	OrganisationLegalDetailsRepository legalRepo;
	@Autowired
	BenficiaryLocationsMapper locationsMapper;
	@Autowired
	BeneficiaryLocationsRepository locationRepo;
	@Autowired
	BeneficiaryGmapLocationsRepository gmapLocationsRepo;

	@Autowired
	OrganisationSectorRepository sectorRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDataMapper userDataMapper;

	@Autowired
	UserOrganisationMappingRespository userOrganisationMappingRepo;

	@Autowired
	DocumentService documentService;
	@Autowired
	OrganisationTypeRepository organisationTypeRepository;
	@Autowired
	OrganisationFCRADetailsRepository organisationFCRADetailsRepository;
	@Autowired
	Organisation12A80GRepository organisation12A80GRepository;
	@Autowired
	OrganisationMembersRolesRepository organisationMembersRolesRepository;

	@Override
	public void searchCompany() {
		// to be implmented
	}

	/**
	 * addOrgBasicDetails is to add the basic details in the orgaisation table
	 * 
	 * @param OrgBasicDetailsRequest
	 * @throws Exception
	 */
	@Override
	@Transactional
	public OrgBasicDetailsResponse addOrgBasicDetails(OrgBasicDetailsRequest request) throws Exception {
		log.debug("Adding organisation data save starts");
		if (request.getEmailId() == null || request.getEmailId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL);
		}
		if (request.getAmlCheck() != null && !request.getAmlCheck().isEmpty()) {
			if (!request.getAmlCheck().equalsIgnoreCase(AmlVerificationStatus.NOT_CHECKED.toString())) {
				if (!request.getAmlCheck().equalsIgnoreCase(AmlVerificationStatus.PASSED.toString())) {
					throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
				}
			}
		}
		OrgBasicDetailsResponse orgBasicDetailsResponse = null;
		try {
			String updateUser = securityUtil.getCurrentUser();
			if (getOrganisationBasicData(request.getEmailId())) {
				throw new Exception(Constants.ORG_BASIC_DETAILS_ALREADY_EXISTS);
			}
			Organisation entity = organisationBasicDetailsMapper.getOrganisationMapper(request);
			entity.setCreatedAt(new Date().getTime());
			entity.setCreatedBy(updateUser);
			Organisation savedEntity = orgRepo.save(entity);

			OrganisationMembers organisationMembers = organisationMembersMapper.getOrganisationMapper(savedEntity,
					request);
			if (request.getIdProofNumber() != null && !request.getIdProofNumber().isEmpty()
					&& CommonUtility.findLengthOfString(request.getIdProofNumber()) > 0) {
				organisationMembers.setIdProofNumber(FileUtils.encrypt(request.getIdProofNumber()));
			}
			organisationMembers.setOrganisation(savedEntity);
			organisationMembers.setAmlChecked(request.getAmlCheck() != null && !request.getAmlCheck().isEmpty()
					? request.getAmlCheck().toUpperCase()
					: AmlVerificationStatus.NOT_CHECKED.toString());
			organisationMembers.setCreatedAt(new Date().getTime());
			organisationMembers.setCreatedBy(updateUser);
			OrganisationMembers savedOrganisationMembersEntity = organisationMembersRepo.save(organisationMembers);

			savedEntity.setCreatorMemberId(savedOrganisationMembersEntity.getId());
			Organisation organisationEntity = orgRepo.save(savedEntity);

			User user = userRepository.findByUserIdpId(updateUser);
			System.out.println(user.getId() + " " + organisationEntity.getId());
			UserOrganisationMapping userOrganisationMapping = userDataMapper.mapAgentPartnerOrganization(user.getId(),
					organisationEntity.getId());
			userOrganisationMapping.setCreatedBy(updateUser);
			UserOrganisationMapping userOrganisationMappingSaved = userOrganisationMappingRepo
					.save(userOrganisationMapping);
			OrganisationFCRADetails organisationFCRADetails = new OrganisationFCRADetails();
			if(!StringUtils.isEmpty(request.getFcraNumber())) {
				
				organisationFCRADetails.setFcraNumber(request.getFcraNumber());	
				organisationFCRADetails.setFcraExpiryDate(request.getFcraExpiryDate());
				organisationFCRADetails.setFcraIssueDate(request.getFcraIssueDate());
				organisationFCRADetails.setOrganisation(savedEntity);
				organisationFCRADetails.setCreatedBy(updateUser);
				organisationFCRADetailsRepository.save(organisationFCRADetails);
				
				}
			Organisation12A80G organisation12A80GDetails = new Organisation12A80G();
				if (!StringUtils.isEmpty(request.getOrg12a80gCertificateNumber())) {

					organisation12A80GDetails.setOrg12a80gCertificateNumber(request.getOrg12a80gCertificateNumber());
					organisation12A80GDetails.setOrg12a80gValidityDate(request.getOrg12a80gValidityDate());
					organisation12A80GDetails.setOrg12a80gIssueDate(request.getOrg12a80gIssueDate());
					organisation12A80GDetails.setOrganisation(savedEntity);
					organisation12A80GDetails.setCreatedBy(updateUser);
					organisation12A80GRepository.save(organisation12A80GDetails);
					
				}
			orgBasicDetailsResponse = organisationBasicDetailsMapper.getOrganisationMapper(organisationEntity,
					savedOrganisationMembersEntity);
			orgBasicDetailsResponse = setFCRADetails(organisationFCRADetails,orgBasicDetailsResponse);
			orgBasicDetailsResponse =setCertificateDetails(organisation12A80GDetails,orgBasicDetailsResponse);
					orgBasicDetailsResponse.setIdProofNumber(!StringUtils.isEmpty(orgBasicDetailsResponse.getIdProofNumber())
					? FileUtils.decrypt(orgBasicDetailsResponse.getIdProofNumber())
					: null);
			
			
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));

			if (exception.getMessage().equals(Constants.ORG_BASIC_DETAILS_ALREADY_EXISTS)) {
				throw new SOException(ErrorCode.CONFLICT, Constants.ORG_BASIC_DETAILS_ALREADY_EXISTS);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_BASIC_DETAILS_ADD_FAIL);
			}

		}
		log.debug("Adding organisation ends");
		return orgBasicDetailsResponse;
	}

	

	private OrgBasicDetailsResponse setCertificateDetails(Organisation12A80G organisation12a80gDetails,
			OrgBasicDetailsResponse orgBasicDetailsResponse) {
		if(!StringUtils.isEmpty(organisation12a80gDetails.getOrg12a80gCertificateNumber()) ) {
		orgBasicDetailsResponse.setOrg12a80gCertificateNumber(organisation12a80gDetails.getOrg12a80gCertificateNumber());
		}
		if(organisation12a80gDetails.getOrg12a80gIssueDate() !=null) {
			orgBasicDetailsResponse.setOrg12a80gIssueDate(organisation12a80gDetails.getOrg12a80gIssueDate());
		}
		if(organisation12a80gDetails.getOrg12a80gValidityDate()!=null) {
			orgBasicDetailsResponse.setOrg12a80gValidityDate(organisation12a80gDetails.getOrg12a80gValidityDate());
		}
		
		return orgBasicDetailsResponse;
	}

	private OrgBasicDetailsResponse setFCRADetails(OrganisationFCRADetails organisationFCRADetails,OrgBasicDetailsResponse orgBasicDetailsResponse) {

		if(organisationFCRADetails.getFcraExpiryDate()!=null) {
			orgBasicDetailsResponse.setFcraExpiryDate(organisationFCRADetails.getFcraExpiryDate());	
		}
		if((organisationFCRADetails.getFcraIssueDate()!=null)) {
			orgBasicDetailsResponse.setFcraIssueDate(organisationFCRADetails.getFcraIssueDate());
		}
		if(!StringUtils.isEmpty(organisationFCRADetails.getFcraNumber())) {
			orgBasicDetailsResponse.setFcraNumber(organisationFCRADetails.getFcraNumber());

		}
		
		return orgBasicDetailsResponse;
	}

	/**
	 * updateOrgBasicDetails is to update the organisation table data
	 */
	@Transactional
	@Override
	public OrgBasicDetailsResponse updateOrgBasicDetails(OrgBasicDetailsRequest request) {
		log.debug("Updating organisation data save starts");
		if (request.getEmailId() == null || request.getEmailId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.INVALID_EMAIL);
		}
		if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		if (request.getAmlCheck() != null && !request.getAmlCheck().isEmpty()) {
			if (!request.getAmlCheck().equalsIgnoreCase(AmlVerificationStatus.NOT_CHECKED.toString())) {
				if (!request.getAmlCheck().equalsIgnoreCase(AmlVerificationStatus.PASSED.toString())) {
					throw new SOException(ErrorCode.BAD_REQUEST, Constants.AML_CHECK_STATUS_VALUE_INVALID);
				}
			}
		}
		OrgBasicDetailsResponse orgBasicDetailsResponse = null;
		try {
			String updateUser = securityUtil.getCurrentUser();
			if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
				throw new Exception(Constants.ORG_ID_NOT_FOUND);
			}
			if (request.getCreatorMemberId() == null || request.getCreatorMemberId().isEmpty()) {
				throw new Exception(Constants.USER_ID_INVALID);
			}
			Optional<Organisation> existingEntity = orgRepo.findById(request.getOrganisationId());
			if (existingEntity.isEmpty()) {
				throw new Exception(Constants.ORG_BASIC_DETAILS_NOT_FOUND);
			}
			Organisation entity = organisationBasicDetailsMapper.getOrganisationUpdateMapper(request);
			entity.setAbout(existingEntity.get().getAbout());
			entity.setHeadquarterCountry(existingEntity.get().getHeadquarterCountry());
			entity.setCreatedAt(existingEntity.get().getCreatedAt());
			entity.setCreatedBy(existingEntity.get().getCreatedBy());
			entity.setModifiedAt(new Date().getTime());
			entity.setLastModifiedBy(updateUser);
			entity.setSignedAgreementUrl(existingEntity.get().getSignedAgreementUrl());
			entity.setMissionStatements(existingEntity.get().getMissionStatements());
			entity.setValueStatement(existingEntity.get().getValueStatement());
			entity.setVisionStatement(existingEntity.get().getVisionStatement());
			Organisation savedEntity = orgRepo.save(entity);

			OrganisationMembers savedOrganisationMembersEntity = null;
			Optional<OrganisationMembers> existingMembersEntity = organisationMembersRepo
					.findById(request.getCreatorMemberId());
			if (!existingMembersEntity.isEmpty()) {
				OrganisationMembers organisationMembers = organisationMembersMapper
						.getOrganisationUpdateMapper(savedEntity, request);
				if (CommonUtility.findLengthOfString(request.getIdProofNumber()) > 0)
					organisationMembers.setIdProofNumber(FileUtils.encrypt(request.getIdProofNumber()));
				organisationMembers.setAuthorisedSignatory(existingMembersEntity.get().getAuthorisedSignatory());
				organisationMembers.setAmlChecked(request.getAmlCheck() != null && !request.getAmlCheck().isEmpty()
						? request.getAmlCheck().toUpperCase()
						: AmlVerificationStatus.NOT_CHECKED.toString());
				organisationMembers.setCreatedAt(existingMembersEntity.get().getCreatedAt());
				organisationMembers.setCreatedBy(existingMembersEntity.get().getCreatedBy());
				organisationMembers.setModifiedAt(new Date().getTime());
				organisationMembers.setLastModifiedBy(updateUser);
				savedOrganisationMembersEntity = organisationMembersRepo.save(organisationMembers);

				savedEntity.setCreatorMemberId(savedOrganisationMembersEntity.getId());
				savedEntity.setModifiedAt(new Date().getTime());
				savedEntity.setLastModifiedBy(updateUser);
				orgRepo.save(savedEntity);
			} else {
				throw new Exception(Constants.ORG_MEMBER_NOT_FOUND);
			}
			orgBasicDetailsResponse = organisationBasicDetailsMapper.getOrganisationMapper(savedEntity,
					savedOrganisationMembersEntity);
			OrganisationFCRADetails organisationFCRADetails = organisationFCRADetailsRepository.findByOrganisationId(request.getOrganisationId());
			
			if (!Objects.isNull(organisationFCRADetails)) {
				organisationFCRADetails.setFcraNumber(request.getFcraNumber());
				organisationFCRADetails.setFcraExpiryDate(request.getFcraExpiryDate());
				organisationFCRADetails.setFcraIssueDate(request.getFcraIssueDate());
				organisationFCRADetails.setOrganisation(savedEntity);
				organisationFCRADetails.setModifiedAt(new Date().getTime());
				organisationFCRADetails.setLastModifiedBy(updateUser);
				organisationFCRADetailsRepository.save(organisationFCRADetails);
				orgBasicDetailsResponse = setFCRADetails(organisationFCRADetails,orgBasicDetailsResponse);
				
			}else {
				OrganisationFCRADetails organisationFCRADetailsNew = new OrganisationFCRADetails();
				organisationFCRADetailsNew.setFcraNumber(request.getFcraNumber());
				organisationFCRADetailsNew.setFcraExpiryDate(request.getFcraExpiryDate());
				organisationFCRADetailsNew.setFcraIssueDate(request.getFcraIssueDate());
				organisationFCRADetailsNew.setOrganisation(savedEntity);
				organisationFCRADetailsNew.setCreatedBy(updateUser);
				organisationFCRADetailsRepository.save(organisationFCRADetailsNew);
				orgBasicDetailsResponse = setFCRADetails(organisationFCRADetailsNew,orgBasicDetailsResponse);
				
			}
			Organisation12A80G organisation12A80GDetails = organisation12A80GRepository.findByOrganisationId(request.getOrganisationId());
			if (!Objects.isNull(organisation12A80GDetails)) {

				organisation12A80GDetails.setOrg12a80gCertificateNumber(request.getOrg12a80gCertificateNumber());
				organisation12A80GDetails.setOrg12a80gValidityDate(request.getOrg12a80gValidityDate());
				organisation12A80GDetails.setOrg12a80gIssueDate(request.getOrg12a80gIssueDate());
				organisation12A80GDetails.setOrganisation(savedEntity);
				organisation12A80GDetails.setModifiedAt(new Date().getTime());
				organisation12A80GDetails.setLastModifiedBy(updateUser);
				organisation12A80GRepository.save(organisation12A80GDetails);
				orgBasicDetailsResponse = setCertificateDetails(organisation12A80GDetails, orgBasicDetailsResponse);
			}else {
				Organisation12A80G organisation12A80GDetailsNew = new Organisation12A80G(); 
				organisation12A80GDetailsNew.setOrg12a80gCertificateNumber(request.getOrg12a80gCertificateNumber());
				organisation12A80GDetailsNew.setOrg12a80gValidityDate(request.getOrg12a80gValidityDate());
				organisation12A80GDetailsNew.setOrg12a80gIssueDate(request.getOrg12a80gIssueDate());
				organisation12A80GDetailsNew.setOrganisation(savedEntity);
				organisation12A80GDetailsNew.setCreatedBy(updateUser);
				organisation12A80GRepository.save(organisation12A80GDetailsNew);
				orgBasicDetailsResponse = setCertificateDetails(organisation12A80GDetailsNew, orgBasicDetailsResponse);
			}
		
			orgBasicDetailsResponse.setIdProofNumber(!StringUtils.isEmpty(orgBasicDetailsResponse.getIdProofNumber())
					? FileUtils.decrypt(orgBasicDetailsResponse.getIdProofNumber())
					: null);
			
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (exception.getMessage().equals(Constants.ORG_BASIC_DETAILS_NOT_FOUND)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
			} else if (Constants.ORG_ID_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
			} else if (Constants.USER_ID_INVALID.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_ID_INVALID);
			} else if (Constants.ORG_MEMBER_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_MEMBER_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_BASIC_DETAILS_UPDATE_FAIL);
			}
		}
		log.debug("Updating organisation data ends");
		return orgBasicDetailsResponse;
	}

	/**
	 * getOrgBasicDetails is to get the organisation details
	 * 
	 * @param id, id of the registered organisation table data
	 */
	@Override
	@Transactional
	public OrgBasicDetailsResponse getOrgBasicDetails(String id) {
		log.debug("Get organisation data request starts");
		if (id == null || id.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		OrgBasicDetailsResponse orgBasicDetailsResponse = null;
		try {
			Optional<Organisation> existingEntity = orgRepo.findById(id);
			if (existingEntity.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
			}
			Optional<OrganisationMembers> organisationMembers = organisationMembersRepo
					.findById(existingEntity.get().getCreatorMemberId());
			OrganisationFCRADetails organisationFCRADetails =organisationFCRADetailsRepository.findByOrganisationId(id);
			Organisation12A80G organisation12A80GDetails = organisation12A80GRepository.findByOrganisationId(id);
			
			orgBasicDetailsResponse = organisationBasicDetailsMapper.getOrganisationMapper(existingEntity.get(),
					organisationMembers.get());
			if(!Objects.isNull(organisationFCRADetails)) {
				orgBasicDetailsResponse = setFCRADetails(organisationFCRADetails,orgBasicDetailsResponse);
			}
			if(!Objects.isNull(organisation12A80GDetails)) {
				orgBasicDetailsResponse = setCertificateDetails(organisation12A80GDetails, orgBasicDetailsResponse);
			}
			
			orgBasicDetailsResponse.setIdProofNumber(!StringUtils.isEmpty(orgBasicDetailsResponse.getIdProofNumber())
					? FileUtils.decrypt(orgBasicDetailsResponse.getIdProofNumber())
					: null);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.ORG_BASIC_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_BASIC_DETAILS_FETCH_FAIL);
			}
		}
		log.debug("Get organisation data request ends");
		return orgBasicDetailsResponse;
	}

	/**
	 * getOrganisationBasicData is to check the existence of the organisation data
	 * 
	 * @param emailId
	 * @return
	 */
	private boolean getOrganisationBasicData(String emailId) {
		Organisation organisation = orgRepo.findByEmailIdIgnoreCase(emailId);
		if (Objects.isNull(organisation)) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public OrganisationBeneficiaryHistoryResponse addBeneficiaryHistory(OrganisationBeneficiaryHistoryRequest request) {
		if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		Optional<Organisation> existingEntity = orgRepo.findById(request.getOrganisationId());
		if (existingEntity.isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
		}
		Boolean isBeneficiaryHistoryExist = beneficiaryHistoryRepo.existsByOrganisationId(request.getOrganisationId());

		if (isBeneficiaryHistoryExist == true) {
			throw new SOException(ErrorCode.CONFLICT, Constants.BENEFICIARY_HISTORY_EXISTS);
		}

		try {
			String updateUser = securityUtil.getCurrentUser();
			List<BeneficiaryLocationsResponse> locationsResponse = new ArrayList<>();
			List<BeneficiaryImpactedHistoryResponse> beneficiaryImpactedHistoryResponse = new ArrayList<>();
			List<String> beneficiaryTargetCategoryResponse = new ArrayList<>();

			Organisation org = orgRepo.getOne(request.getOrganisationId());

			OrganisationBeneficiaryHistory history = new OrganisationBeneficiaryHistory();
			OrganisationBeneficiaryHistory entity = organisationMapper
					.mapOrganisationBeneficiaryHistoryRequestToOrganisationBeneficiaryHistory(request, org.getId(),
							history.getId(), history);

			entity.setCreatedBy(updateUser);

			OrganisationBeneficiaryHistory beneficiaryHistory = beneficiaryHistoryRepo.save(entity);

			for (BeneficiaryImpactedHistoryRequest beneficiaryImpactedHistoryRequest : request
					.getBeneficiaryImpactedHistory()) {

				BeneficiaryImpactedHistory beneficiaryImpactedHistoryEntity = organisationMapper
						.mapBeneficiaryImpactedHistoryRequestToBeneficiaryImpactedHistory(
								beneficiaryImpactedHistoryRequest, beneficiaryHistory.getId(), org.getId(),
								beneficiaryImpactedHistoryRequest.getFinancialYear());

				beneficiaryImpactedHistoryEntity.setCreatedBy(updateUser);

				BeneficiaryImpactedHistory beneficiaryImpactedHistory = beneficiaryImpactedHistoryRepo
						.save(beneficiaryImpactedHistoryEntity);

				beneficiaryImpactedHistoryResponse
						.add(organisationMapper.mapBeneficiaryImpactedHistoryToBeneficiaryImpactedHistoryResponse(
								beneficiaryImpactedHistory, beneficiaryImpactedHistoryRequest.getFinancialYear()));

			}
			for (String beneficiaryTargetCategory : request.getTargetCategory()) {

				OrganisationBeneficiaryTargets organisationBeneficiaryTargetEntity = organisationMapper
						.mapBeneficiaryTargetCategoryToOrganisationBeneficiaryTargets(org.getId(),
								beneficiaryHistory.getId(), beneficiaryTargetCategory);
				organisationBeneficiaryTargetEntity.setCreatedBy(updateUser);
				OrganisationBeneficiaryTargets organisationBeneficiaryTarget = organisationBeneficiaryTargetsRepo
						.save(organisationBeneficiaryTargetEntity);

				beneficiaryTargetCategoryResponse.add(organisationBeneficiaryTarget.getTargetCategory());

			}

			if (request.getBeneficiaryLocations() != null) {
				if (!request.getBeneficiaryLocations().isEmpty()) {
					locationsResponse = addBeneficiaryLocations(request.getBeneficiaryLocations(), beneficiaryHistory,
							org);
				}

			}
			OrganisationBeneficiaryHistoryResponse response = organisationMapper
					.mapOrganisationBeneficiaryHistoryToOrganisationBeneficiaryHistoryResponse(beneficiaryHistory, org);

			response.setBeneficiaryImpactedHistory(beneficiaryImpactedHistoryResponse);
			response.setTargetCategory(beneficiaryTargetCategoryResponse);
			response.setBeneficiaryLocations(locationsResponse);
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.BENEFICIARY_HISTORY__ADD_FAIL);
		}

	}

	@Override
	@Transactional
	public OrganisationFinancialDetailsResponse addFinancialDoccument(OrganisationFinancialDetailsRequest request) {
		if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		try {
			String updateUser = securityUtil.getCurrentUser();
			Optional<Organisation> existingEntity = orgRepo.findById(request.getOrganisationId());
			if (existingEntity.isEmpty()) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
			}
			Boolean isFinancialDetailExist = organisationFinancialDetailsRepo
					.existsByOrganisationId(request.getOrganisationId());

			if (isFinancialDetailExist == true) {
				throw new SOException(ErrorCode.CONFLICT, Constants.FINANCIAL_DOCUMENTS_ALREADY_EXISTS);
			}
			List<LastAuditedStatementsResponse> lastAuditedStatementsResponse = new ArrayList<>();

			OrganisationFinancialDetails financialObj = organisationMapper
					.mapOrganisationFinancialDetailsRequestToOrganisationFinancialDetails(request,
							request.getOrganisationId());

			financialObj.setCreatedBy(updateUser);
			OrganisationFinancialDetails financialDetails = organisationFinancialDetailsRepo.save(financialObj);

			for (LastAuditedStatementsRequest lastAuditedStatementsRequest : request.getLastAuditedStatements()) {
				LastAuditedStatements lastAuditedStatements = organisationMapper
						.mapLastAuditedStatementsRequestToLastAuditedStatements(lastAuditedStatementsRequest,
								financialDetails.getId(), request.getOrganisationId());
				lastAuditedStatements.setCreatedBy(updateUser);
				lastAuditedStatements = lastAuditedStatementsRepo.save(lastAuditedStatements);

				lastAuditedStatementsResponse.add(organisationMapper
						.mapLastAuditedStatementsToLastAuditedStatementsResponse(lastAuditedStatements));
			}

			OrganisationFinancialDetailsResponse response = organisationMapper
					.mapOrganisationFinancialDetailsToOrganisationFinancialDetailsResponse(financialDetails,
							request.getOrganisationId());
			response.setLastAuditedStatements(lastAuditedStatementsResponse);
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.ORG_BASIC_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
			} else if (Constants.FINANCIAL_DOCUMENTS_ALREADY_EXISTS.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.CONFLICT, Constants.FINANCIAL_DOCUMENTS_ALREADY_EXISTS);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FINANCIAL_DOCUMENTS_ADD_FAIL);
			}
		}
	}

	@Override
	@Transactional
	public OrganisationFinancialDetailsResponse updateFinancialDoccument(
			OrganisationFinancialDetailsUpdateRequest request, String id) {
		if (id == null || id.isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.FINANCIAL_DOCUMENT_ID_MISSING);
		}
		if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		Optional<Organisation> existingEntity = orgRepo.findById(request.getOrganisationId());
		if (existingEntity.isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
		}
		Boolean isFinancialDetailExist = organisationFinancialDetailsRepo.existsById(id);
		if (isFinancialDetailExist == false) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.FINANCIAL_DOCUMENTS_NOT_FOUND);
		}
		try {
			String updateUser = securityUtil.getCurrentUser();
			List<LastAuditedStatementsResponse> lastAuditedStatementsResponse = new ArrayList<>();
			Organisation org = orgRepo.getOne(request.getOrganisationId());

			OrganisationFinancialDetails finance = organisationFinancialDetailsRepo.getOne(id);

			OrganisationFinancialDetails financialDetails = organisationMapper
					.mapOrganisationFinancialDetailsUpdateRequestToOrganisationFinancialDetails(request, org.getId(),
							finance.getId(), finance);
			financialDetails.setCreatedAt(finance.getCreatedAt());
			financialDetails.setCreatedBy(finance.getCreatedBy());
			financialDetails.setModifiedAt(new Date().getTime());
			financialDetails.setLastModifiedBy(updateUser);

			financialDetails = organisationFinancialDetailsRepo.save(financialDetails);

			for (LastAuditedStatementsUpdateRequest lastAuditedStatementsRequest : request.getLastAuditedStatements()) {
				if (lastAuditedStatementsRequest.getId() == null || lastAuditedStatementsRequest.getId().isEmpty()) {
					LastAuditedStatements lastAuditedStatements = organisationMapper
							.mapLastAuditedStatementsUpdateRequestToLastAuditedStatements(lastAuditedStatementsRequest,
									financialDetails.getId(), request.getOrganisationId());
					lastAuditedStatements.setCreatedBy(updateUser);
					lastAuditedStatements = lastAuditedStatementsRepo.save(lastAuditedStatements);
					lastAuditedStatementsResponse.add(organisationMapper
							.mapLastAuditedStatementsToLastAuditedStatementsResponse(lastAuditedStatements));
				} else {
					LastAuditedStatements existingAuditStatement = lastAuditedStatementsRepo
							.getOne(lastAuditedStatementsRequest.getId());
					if (Objects.isNull(existingAuditStatement)) {
						throw new Exception(Constants.BENEFICIARY_HISTORY_NOT_FOUND);
					}
					LastAuditedStatements lastAuditedStatements = organisationMapper
							.mapLastAuditedStatementsUpdateRequestToLastAuditedStatements(lastAuditedStatementsRequest,
									financialDetails.getId(), org.getId());
					lastAuditedStatements.setCreatedAt(existingAuditStatement.getCreatedAt());
					lastAuditedStatements.setCreatedBy(existingAuditStatement.getCreatedBy());
					lastAuditedStatements.setModifiedAt(new Date().getTime());
					lastAuditedStatements.setLastModifiedBy(updateUser);
					lastAuditedStatements = lastAuditedStatementsRepo.save(lastAuditedStatements);
					lastAuditedStatementsResponse.add(organisationMapper
							.mapLastAuditedStatementsToLastAuditedStatementsResponse(lastAuditedStatements));
				}
			}
			OrganisationFinancialDetailsResponse response = organisationMapper
					.mapOrganisationFinancialDetailsToOrganisationFinancialDetailsResponse(financialDetails,
							org.getId());
			response.setLastAuditedStatements(lastAuditedStatementsResponse);
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.BENEFICIARY_HISTORY_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.BENEFICIARY_HISTORY_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FINANCIAL_DOCUMENTS_UPDATE_FAIL);
			}
		}

	}

	@Override
	@Transactional
	public OrganisationLegalDetailsResponse addLegalDocuments(OrganisationLegalDetailsRequest request) {
		if (request.getOrganisationId() == null || request.getOrganisationId().isEmpty()) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		Optional<Organisation> existingEntity = orgRepo.findById(request.getOrganisationId());
		if (existingEntity.isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
		}
		Boolean isLegalDetailExist = organisationLegalDetailsRepo.existsByOrganisationId(request.getOrganisationId());
		if (isLegalDetailExist == true) {
			throw new SOException(ErrorCode.CONFLICT, Constants.LEGAL_DOCUMENTS_EXIST);
		}
		try {
			Optional<Organisation> organisation = orgRepo.findById(request.getOrganisationId());

			OrganisationLegalDetails entity = organisationMapper
					.mapOrganisationLegalDetailsRequestToOrganisationLegalDetails(request, organisation.get());
			entity.setCreatedBy(securityUtil.getCurrentUser());

			OrganisationLegalDetails legalDetails = organisationLegalDetailsRepo.save(entity);

			return organisationMapper.mapOrganisationLegalDetailsToOrganisationLegalDetailsResponse(legalDetails);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.LEGAL_DOCUMENTS_ADD_SUCCESS);
		}
	}

	@Override
	@Transactional
	public OrganisationLegalDetailsResponse updateLegalDocuments(OrganisationLegalDetailsUpdateRequest request) {
		try {
			Optional<Organisation> organisation = orgRepo.findById(request.getOrganisationId());

			Optional<OrganisationLegalDetails> existingEntity = organisationLegalDetailsRepo.findById(request.getId());
			if (existingEntity.isEmpty()) {
				throw new Exception(Constants.ORG_LEGAL_DOCUMENTS_NOT_FOUND);
			}
			OrganisationLegalDetails entity = organisationMapper
					.mapOrganisationLegalDetailsRequestToOrganisationLegalDetails(request, organisation.get());
			entity.setCreatedBy(existingEntity.get().getCreatedBy());
			entity.setCreatedAt(existingEntity.get().getCreatedAt());
			entity.setModifiedAt(new Date().getTime());
			entity.setLastModifiedBy(securityUtil.getCurrentUser());
			OrganisationLegalDetails legalDetails = organisationLegalDetailsRepo.save(entity);

			return organisationMapper.mapOrganisationLegalDetailsToOrganisationLegalDetailsResponse(legalDetails);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.ORG_LEGAL_DOCUMENTS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_LEGAL_DOCUMENTS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.LEGAL_DOCUMENTS_UPDATE_FAIL);
			}
		}
	}

	@Override
	@Transactional
	public OrganisationLegalDetailsResponse getLegalDocuments(String organisationId) {
		log.debug("Get legal-documents starts");
		if (StringUtils.isEmpty(organisationId)) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		OrganisationLegalDetailsResponse orgLegalDetailsResponse = null;
		try {
			Optional<Organisation> organisationData = orgRepo.findById(organisationId);
			if (organisationData.isEmpty()) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_DATA_NOT_FOUND);
			}
			OrganisationLegalDetails existingEntity = organisationLegalDetailsRepo.findByOrganisationId(organisationId);
			if (existingEntity == null) {
				return new OrganisationLegalDetailsResponse();
			}
			orgLegalDetailsResponse = organisationMapper
					.mapOrganisationLegalDetailsToOrganisationLegalDetailsFetchResponse(existingEntity,
							organisationData.get().getApprovalStatus());

			return orgLegalDetailsResponse;
		} catch (Exception exception) {
			log.error(exception.toString());
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_LEGAL_DOCUMENTS_FETCH_FAIL);
		}

	}

	@Override
	@Transactional
	public OrganisationFinancialDetailsResponse getFinancialDocuments(String organisationId) {
		if (StringUtils.isEmpty(organisationId)) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		try {
			Optional<Organisation> organisationData = orgRepo.findById(organisationId);
			if (organisationData.isEmpty()) {
				throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_DATA_NOT_FOUND);
			}

			OrganisationFinancialDetails existingEntity = organisationFinancialDetailsRepo
					.findByOrganisationId(organisationId);
			if (existingEntity == null) {
				return new OrganisationFinancialDetailsResponse();
			}
			String orgId = existingEntity.getOrganisation().getId();
			OrganisationFinancialDetailsResponse response = organisationMapper
					.mapOrganisationFinancialDetailsToOrganisationFinancialDetailsFetchResponse(existingEntity, orgId,
							existingEntity, organisationData.get().getApprovalStatus());
			List<LastAuditedStatementsResponse> lastAuditedStatementsResponse = lastAuditedStatementsRepo
					.getByBeneficiaryHistory(existingEntity.getId(), orgId);
			response.setLastAuditedStatements(lastAuditedStatementsResponse);
			return response;
		} catch (Exception exception) {
			log.error(exception.toString());
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FINANCIAL_DOCUMENTS_FETCH_FAIL);
		}

	}

	@Override
	@Transactional
	public OrganisationBeneficiaryHistoryResponse getBeneficiaryHistory(String organisationId) {
		OrganisationBeneficiaryHistory existingEntity = beneficiaryHistoryRepo.findByOrganisationId(organisationId);
		if (existingEntity == null) {
			return new OrganisationBeneficiaryHistoryResponse();
		}
		try {

			OrganisationBeneficiaryHistoryResponse response = organisationMapper
					.mapOrganisationBeneficiaryHistoryToOrganisationBeneficiaryHistoryResponse(existingEntity,
							existingEntity.getOrganisation());

			List<BeneficiaryImpactedHistoryResponse> beneficiaryImpactList = beneficiaryImpactedHistoryRepo
					.getByBeneficiaryHistory(existingEntity.getId(), existingEntity.getOrganisation().getId());

			List<String> beneficiaryTargetsList = organisationBeneficiaryTargetsRepo
					.getByBeneficiaryHistory(existingEntity.getId(), existingEntity.getOrganisation().getId());

			response.setBeneficiaryImpactedHistory(beneficiaryImpactList);
			response.setTargetCategory(beneficiaryTargetsList);

			List<BeneficiaryLocationsResponse> locationList = locationRepo.getByBeneficiaryHistoryAndOrganisation(
					existingEntity.getId(), existingEntity.getOrganisation().getId());
			for (BeneficiaryLocationsResponse location : locationList) {
				location.setBeneficiaryGmapLocations(
						gmapLocationsRepo.getByLocationAndBeneficiaryHistoryAndOrganisation(location.getId(),
								existingEntity.getId(), existingEntity.getOrganisation().getId()));
			}

			response.setBeneficiaryLocations(locationList);
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.BENEFICIARY_HISTORY_FETCH_FAIL);
		}
	}

	/**
	 * @modified Arya C Achari
	 * @since 19-Jan-2022
	 *
	 */
	@Override
	@Transactional
	public OrganisationBeneficiaryHistoryResponse updateBeneficiaryHistory(String beneficiaryHistoryId,
			OrganisationBeneficiaryHistoryUpdateRequest request) {

		try {
			String updateUser = securityUtil.getCurrentUser();
			List<BeneficiaryLocationsResponse> locationsResponse = new ArrayList<>();
			List<BeneficiaryImpactedHistoryResponse> beneficiaryImpactedHistoryResponse = new ArrayList<>();
			List<String> beneficiaryTargetCategoryResponse = new ArrayList<>();
			BeneficiaryImpactedHistory beneficiaryImpactedHistory = null;

			Organisation org = orgRepo.getOne(request.getOrganisationId());

			OrganisationBeneficiaryHistory history = beneficiaryHistoryRepo.getOne(beneficiaryHistoryId);

			OrganisationBeneficiaryHistory entity = organisationMapper
					.mapOrganisationBeneficiaryHistoryUpdateRequestToOrganisationBeneficiaryHistory(request,
							org.getId(), beneficiaryHistoryId, history);
			entity.setCreatedAt(history.getCreatedAt());
			entity.setCreatedBy(history.getCreatedBy());
			entity.setLastModifiedBy(updateUser);
			entity.setModifiedAt(new Date().getTime());

			OrganisationBeneficiaryHistory beneficiaryHistory = beneficiaryHistoryRepo.save(entity);

			for (BeneficiaryImpactedHistoryUpdateRequest beneficiaryImpactedHistoryRequest : request
					.getBeneficiaryImpactedHistory()) {

				// update the BeneficiaryImpactedHistory
				if (!StringUtils.isEmpty(beneficiaryImpactedHistoryRequest.getBeneficiaryImpactedHistoryId())) {
					BeneficiaryImpactedHistory existingImpactHistory = beneficiaryImpactedHistoryRepo
							.getOne(beneficiaryImpactedHistoryRequest.getBeneficiaryImpactedHistoryId());
					beneficiaryImpactedHistory = organisationMapper
							.mapBeneficiaryImpactedHistoryUpdateRequestToBeneficiaryImpactedHistory(
									beneficiaryImpactedHistoryRequest, beneficiaryHistory.getId(), org.getId(),
									beneficiaryImpactedHistoryRequest.getFinancialYear());
					beneficiaryImpactedHistory.setLastModifiedBy(updateUser);
					beneficiaryImpactedHistory.setModifiedAt(new Date().getTime());
					beneficiaryImpactedHistory.setCreatedAt(existingImpactHistory.getCreatedAt());
					beneficiaryImpactedHistory.setCreatedBy(existingImpactHistory.getCreatedBy());
					beneficiaryImpactedHistory = beneficiaryImpactedHistoryRepo.save(beneficiaryImpactedHistory);
				} else { // create the BeneficiaryImpactedHistory
					BeneficiaryImpactedHistoryRequest biHistoryAddRequest = new BeneficiaryImpactedHistoryRequest(
							beneficiaryImpactedHistoryRequest.getTotalBeneficiaryImpacted(),
							beneficiaryImpactedHistoryRequest.getFinancialYear());
					beneficiaryImpactedHistory = organisationMapper
							.mapBeneficiaryImpactedHistoryRequestToBeneficiaryImpactedHistory(biHistoryAddRequest,
									beneficiaryHistory.getId(), org.getId(),
									beneficiaryImpactedHistoryRequest.getFinancialYear());
					beneficiaryImpactedHistory.setCreatedAt(new Date().getTime());
					beneficiaryImpactedHistory.setCreatedBy(updateUser);
					beneficiaryImpactedHistory = beneficiaryImpactedHistoryRepo.save(beneficiaryImpactedHistory);

				}

				beneficiaryImpactedHistoryResponse
						.add(organisationMapper.mapBeneficiaryImpactedHistoryToBeneficiaryImpactedHistoryResponse(
								beneficiaryImpactedHistory, beneficiaryImpactedHistoryRequest.getFinancialYear()));

			}

			organisationBeneficiaryTargetsRepo.deleteTargetCategories(request.getOrganisationId());

			for (String beneficiaryTargetCategory : request.getTargetCategory()) {
				OrganisationBeneficiaryTargets organisationBeneficiaryTargetEntity = organisationMapper
						.mapBeneficiaryTargetCategoryToOrganisationBeneficiaryTargets(org.getId(),
								beneficiaryHistory.getId(), beneficiaryTargetCategory);
				organisationBeneficiaryTargetEntity.setCreatedBy(updateUser);
				OrganisationBeneficiaryTargets organisationBeneficiaryTarget = organisationBeneficiaryTargetsRepo
						.save(organisationBeneficiaryTargetEntity);

				beneficiaryTargetCategoryResponse.add(organisationBeneficiaryTarget.getTargetCategory());
			}

			if (request.getBeneficiaryLocations() != null && !request.getBeneficiaryLocations().isEmpty()) {
				locationsResponse = addBeneficiaryLocations(request.getBeneficiaryLocations(), beneficiaryHistory, org);
			}

			OrganisationBeneficiaryHistoryResponse response = organisationMapper
					.mapOrganisationBeneficiaryHistoryToOrganisationBeneficiaryHistoryResponse(beneficiaryHistory, org);

			response.setBeneficiaryImpactedHistory(beneficiaryImpactedHistoryResponse);
			response.setTargetCategory(beneficiaryTargetCategoryResponse);
			response.setBeneficiaryLocations(locationsResponse);
			return response;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.BENEFICIARY_HISTORY_UPDATE_FAIL);
		}

	}

	@Override
	@Transactional
	public Boolean deleteLastAuditStatement(String lastAuditStatementId, String organisationId) {
		if (organisationId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		if (lastAuditStatementId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.LAST_AUDIT_STATEMENT_ID_MISSING);
		}
		try {
			lastAuditedStatementsRepo.deleteByIdAndOrganisation_Id(lastAuditStatementId, organisationId);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.LAST_AUDIT_STATEMENT_DELETE_FAIL);
		}
	}

	@Override
	public OrgPercentageCompletionResponse getPercentage(String organisationId) {
		try {
			OrgPercentageCompletionResponse orgPercentageCompletionResponse = new OrgPercentageCompletionResponse();
			// overview
			Organisation organisation = orgRepo.getOne(organisationId);

			OrganisationMembers founderDetails = organisationMembersRepo.findByOrganisationIdAndType(organisationId,
					"Founder");
			BigDecimal totalCount = new BigDecimal(12);
			BigDecimal fieldCount = BigDecimal.ZERO;
			if (!StringUtils.isEmpty(organisation.getName()))
				fieldCount = fieldCount.add(BigDecimal.ONE);
			if (!StringUtils.isEmpty(organisation.getType()))
				fieldCount = fieldCount.add(BigDecimal.ONE);
			if (!StringUtils.isEmpty(organisation.getGstNumber()))
				fieldCount = fieldCount.add(BigDecimal.ONE);
			if (!StringUtils.isEmpty(organisation.getYearFounded()))
				fieldCount = fieldCount.add(BigDecimal.ONE);
			if (!StringUtils.isEmpty(organisation.getNoOfEmployees()))
				fieldCount = fieldCount.add(BigDecimal.ONE);
			if (!StringUtils.isEmpty(organisation.getEmailId()))
				fieldCount = fieldCount.add(BigDecimal.ONE);
			if (!StringUtils.isEmpty(organisation.getWebsite()))
				fieldCount = fieldCount.add(BigDecimal.ONE);

			// only checking first founder added details is filled or not
			if (founderDetails != null) {
				if (!StringUtils.isEmpty(founderDetails.getIdProof()))
					fieldCount = fieldCount.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(founderDetails.getIdProofNumber()))
					fieldCount = fieldCount.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(founderDetails.getName()))
					fieldCount = fieldCount.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(founderDetails.getEmail()))
					fieldCount = fieldCount.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(founderDetails.getPhone()))
					fieldCount = fieldCount.add(BigDecimal.ONE);
			}

			BigDecimal percentage = fieldCount.multiply(new BigDecimal(100)).divide(totalCount, 0, RoundingMode.FLOOR);

			// leadership
			OrganisationMembers organisationMembersList = organisationMembersRepo.findByOrganisationId(organisationId);

			BigDecimal totalCountLeadership = new BigDecimal(5);
			BigDecimal fieldCountLeadership = BigDecimal.ZERO;

			if (!Objects.isNull(organisationMembersList)) {
				if (!StringUtils.isEmpty(organisationMembersList.getIdProof()))
					fieldCountLeadership = fieldCountLeadership.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(organisationMembersList.getIdProofNumber()))
					fieldCountLeadership = fieldCountLeadership.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(organisationMembersList.getName()))
					fieldCountLeadership = fieldCountLeadership.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(organisationMembersList.getEmail()))
					fieldCountLeadership = fieldCountLeadership.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(organisationMembersList.getPhone()))
					fieldCountLeadership = fieldCountLeadership.add(BigDecimal.ONE);
			}
			BigDecimal percentageLeadership = fieldCountLeadership.multiply(new BigDecimal(100))
					.divide(totalCountLeadership, 0, RoundingMode.FLOOR);
			// location

			OrganisationAddress organisationAddress = orgAddressRepo.findByOrganisationId(organisationId);
			BigDecimal totalLocation = new BigDecimal(7);
			BigDecimal fieldCountLocation = BigDecimal.ZERO;
			if (organisation.getHeadquarterCountry() != null
					&& !organisation.getHeadquarterCountry().contains("Select Country")) {
				fieldCountLocation = fieldCountLocation.add(BigDecimal.ONE);
			}
			if (organisationAddress != null) {

				if (!StringUtils.isEmpty(organisationAddress.getTitle()))
					fieldCountLocation = fieldCountLocation.add(BigDecimal.ONE);

				if (!StringUtils.isEmpty(organisationAddress.getAddress()))
					fieldCountLocation = fieldCountLocation.add(BigDecimal.ONE);

				if (!StringUtils.isEmpty(organisationAddress.getCity()))
					fieldCountLocation = fieldCountLocation.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(organisationAddress.getState()))
					fieldCountLocation = fieldCountLocation.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(organisationAddress.getCountry()))
					fieldCountLocation = fieldCountLocation.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(organisationAddress.getPincode()))
					fieldCountLocation = fieldCountLocation.add(BigDecimal.ONE);

			}
			BigDecimal percentageLocation = fieldCountLocation.multiply(new BigDecimal(100)).divide(totalLocation, 0,
					RoundingMode.FLOOR);

			// purpose
			OrganisationMissionStatements mission = missionRepo.findByOrganisationId(organisationId);
			BigDecimal totalPurpose = new BigDecimal(4);
			BigDecimal fieldCountPurpose = BigDecimal.ZERO;

			OrganisationSector primary = sectorRepo.findBysecodarySectors(organisationId,
					SectorType.PRIMARY.toString());
			OrganisationSector secodary = sectorRepo.findBysecodarySectors(organisationId,
					SectorType.SECONDARY.toString());

			if (primary != null) {
				if (primary.getSector() != null)
					fieldCountPurpose = fieldCountPurpose.add(BigDecimal.ONE);
			}
			if (secodary != null) {
				if (secodary.getSector() != null)
					fieldCountPurpose = fieldCountPurpose.add(BigDecimal.ONE);
			}
			if (mission != null) {

				if (!StringUtils.isEmpty(mission.getStatement()))
					fieldCountPurpose = fieldCountPurpose.add(BigDecimal.ONE);

			}
			if (!StringUtils.isEmpty(organisation.getAbout()))
				fieldCountPurpose = fieldCountPurpose.add(BigDecimal.ONE);

			BigDecimal percentagePurpose = fieldCountPurpose.multiply(new BigDecimal(100)).divide(totalPurpose, 0,
					RoundingMode.FLOOR);

			// Track Record
			BigDecimal totaltrack = new BigDecimal(14);
			BigDecimal fieldCounttrack = BigDecimal.ZERO;
			OrganisationBudgetHistory budget = budgetRepo.findByOrganisationId(organisationId);
			if (budget != null) {
				if (!StringUtils.isEmpty(budget.getFinancialYear()))
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (budget.getOverallBudget() != null)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
			}
			OrganisationDonorHistory donor = donorRepo.findByOrganisationId(organisationId);

			if (donor != null) {
				if (!StringUtils.isEmpty(donor.getDonorName()))
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(donor.getCountry()))
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (donor.getAmountFunded() != null)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (donor.getPercentageContributed() != null)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
			}
			OrganisationBeneficiaryHistory beneficiaryHistory = historyRepo.findByOrganisationId(organisationId);

			if (beneficiaryHistory != null) {
				if (!StringUtils.isEmpty(beneficiaryHistory.getBeneficiaryType()))
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				/*
				 * if (!StringUtils.isEmpty(beneficiaryHistory.getLocation())) fieldCounttrack =
				 * fieldCounttrack.add(BigDecimal.ONE);
				 */
				if (beneficiaryHistory.getLatitude() != null)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (beneficiaryHistory.getLongitude() != null)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (beneficiaryHistory.getMaxAge() != null)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (beneficiaryHistory.getMinAge() != null)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (beneficiaryHistory.getOrganisationBeneficiaryTargets().size() != 0)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
				if (beneficiaryHistory.getTotalBeneficiaryImpacted().size() != 0)
					fieldCounttrack = fieldCounttrack.add(BigDecimal.ONE);
			}

			BigDecimal percentageTrack = fieldCounttrack.multiply(new BigDecimal(100)).divide(totaltrack, 0,
					RoundingMode.FLOOR);

			// documents

			BigDecimal totaldocuments = new BigDecimal(15);
			BigDecimal fieldCountDocuments = BigDecimal.ZERO;
			OrganisationFinancialDetails financialDetails = financialRepo.findByOrganisationId(organisationId);

			if (lastAuditedStatementsRepo.existsByOrganisationFinancialDetails(financialDetails)) {
				fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
			}

			if (financialDetails != null) {
				if (!StringUtils.isEmpty(financialDetails.getIfscCode()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);

				if (!StringUtils.isEmpty(financialDetails.getAccountNumber()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);

				if (!StringUtils.isEmpty(financialDetails.getBranchName()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);

				if (!StringUtils.isEmpty(financialDetails.getCancelledChequeUrl()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);

				if (!StringUtils.isEmpty(financialDetails.getCompanyPanCardUrl()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);

			}
			OrganisationLegalDetails legalDetails = legalRepo.findByOrganisationId(organisationId);
			if (legalDetails != null) {

				if (!StringUtils.isEmpty(legalDetails.getCertificateOfIncorporationUrl()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(legalDetails.getFcraApprovalLetter()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (legalDetails.getFcraApprovalValidity() != null)
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(legalDetails.getFcraApprovalLetter()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(legalDetails.getTrustDeedUrl()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(legalDetails.getMemorandumOfAssociationUrl()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(legalDetails.getArticlesOfAssociationUrl()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (legalDetails.getArticlesOfAssociationValidity() != null)
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);
				if (!StringUtils.isEmpty(legalDetails.getUrlOf80G12ACertificate()))
					fieldCountDocuments = fieldCountDocuments.add(BigDecimal.ONE);

			}
			BigDecimal percentagelegalDetails = fieldCountDocuments.multiply(new BigDecimal(100)).divide(totaldocuments,
					0, RoundingMode.FLOOR);

			orgPercentageCompletionResponse.setOverview(percentage.longValue());
			orgPercentageCompletionResponse.setLeadership(percentageLeadership.longValue());
			orgPercentageCompletionResponse.setLocation(percentageLocation.longValue());
			orgPercentageCompletionResponse.setPurpose(percentagePurpose.longValue());
			orgPercentageCompletionResponse.setTrackRecord(percentageTrack.longValue());
			orgPercentageCompletionResponse.setDocuments(percentagelegalDetails.longValue());
			return orgPercentageCompletionResponse;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PERCENTAGE_CALC_FAIL);
		}

	}

	private List<BeneficiaryLocationsResponse> addBeneficiaryLocations(
			List<BeneficiaryLocationsRequest> locationRequestList, OrganisationBeneficiaryHistory beneficiaryHistory,
			Organisation organisation) {
		String userUpdate = securityUtil.getCurrentUser();
		List<BeneficiaryLocationsResponse> responseList = new ArrayList<>();
		for (BeneficiaryLocationsRequest locationRequest : locationRequestList) {
			BeneficiaryLocations location = locationsMapper.toBeneficiaryLocation(locationRequest,
					beneficiaryHistory.getId(), organisation.getId());
			if (StringUtils.isEmpty(location.getId())) {
				location.setCreatedBy(userUpdate);
			} else {
				BeneficiaryLocations existingLocation = locationRepo.findById(location.getId()).orElseThrow(
						() -> new SOException(ErrorCode.NOT_FOUND, Constants.BENEFICIARY_LOCATION_NOT_FOUND));
				location.setCreatedAt(existingLocation.getCreatedAt());
				location.setCreatedBy(existingLocation.getCreatedBy());
				location.setModifiedAt(new Date().getTime());
				location.setLastModifiedBy(userUpdate);
			}

			location = locationRepo.save(location);
			BeneficiaryLocationsResponse locationResponse = locationsMapper.mapEntityToResponse(location);
			if (locationRequest.getBeneficiaryGmapLocations() != null
					&& !locationRequest.getBeneficiaryGmapLocations().isEmpty()) {
				List<BeneficiaryGmapLocationsResponse> gmapLocationResponse = new ArrayList<>();
				for (BeneficiaryGmapLocationsRequest gmapLocationRequest : locationRequest
						.getBeneficiaryGmapLocations()) {
					BeneficiaryGmapLocations gmapLocation = locationsMapper.toGmapBeneficiaryLocation(
							gmapLocationRequest, location.getId(), beneficiaryHistory.getId(), organisation.getId());
					if (StringUtils.isEmpty(gmapLocation.getId())) {
						gmapLocation.setCreatedBy(userUpdate);
					} else {
						BeneficiaryGmapLocations existingGmapLocation = gmapLocationsRepo.findById(gmapLocation.getId())
								.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND,
										Constants.BENEFICIARY_GMAP_LOCATION_NOT_FOUND));
						gmapLocation.setCreatedAt(existingGmapLocation.getCreatedAt());
						gmapLocation.setCreatedBy(existingGmapLocation.getCreatedBy());
						gmapLocation.setModifiedAt(new Date().getTime());
						gmapLocation.setLastModifiedBy(userUpdate);
					}
					gmapLocation.setCreatedBy(userUpdate);
					gmapLocation = gmapLocationsRepo.save(gmapLocation);
					gmapLocationResponse.add(locationsMapper.mapGmapLocationToResponse(gmapLocation));
				}
				locationResponse.setBeneficiaryGmapLocations(gmapLocationResponse);
			}

			responseList.add(locationResponse);

		}
		return responseList;

	}

	@Override
	public OrgBasicDetailsResponse updateSignedAggrement(SignedAggrementRequest request) {
		Organisation organisation = orgRepo.findById(request.getOrganisationId())
				.orElseThrow(() -> new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND));
		if (request.getSignedAgreementUrl() == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.SIGNED_AGGREMENT_URL_MISSING);
		}
		organisation.setSignedAgreementUrl(request.getSignedAgreementUrl());
		organisation.setModifiedAt(new Date().getTime());
		organisation.setLastModifiedBy(securityUtil.getCurrentUser());
		organisation = orgRepo.save(organisation);
		return getOrgBasicDetails(organisation.getId());
	}

	@Override
	public ExistenceResponseDTO existBySignedAggrement(String organisationId) {
		Organisation organisation = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND));
		ExistenceResponseDTO response = new ExistenceResponseDTO();
		if (organisation.getSignedAgreementUrl() != null && !organisation.getSignedAgreementUrl().isEmpty()) {
			response.setSignedAgreement(true);
		} else {
			response.setSignedAgreement(false);
		}
		return response;
	}

	@Override
	public OrganisationCountDTO getCountByStatus() {
		OrganisationCountDTO organisationCountDTO = new OrganisationCountDTO();
		List<Long> countList = this.orgRepo.findStatusCount();
		if (countList.size() > 0) {
			organisationCountDTO.setReviewCount(countList.get(0));
			organisationCountDTO.setApprovedCount(countList.get(1));
			organisationCountDTO.setRejectedCount(countList.get(2));
		}
		return organisationCountDTO;
	}

	/**
	 * Update the organizations status and add comments
	 * 
	 * @author Arya C Achari
	 * @since 03-Nov-2021
	 *
	 */
	@Override
	public OrganisationStatusResponseDto updateStatus(String organisationId,
			OrganisationStatusRequestDto statusRequestDTO) {
		OrganisationStatusResponseDto response = new OrganisationStatusResponseDto();
		Organisation organisation = findOrganisation(organisationId);
		checkApprovalStatus(statusRequestDTO.getStatus());
		try {
			organisation.setApprovalStatus(statusRequestDTO.getStatus());
			organisation.setApprovalStatusComment(statusRequestDTO.getComment());
			organisation.setModifiedAt(new Date().getTime());
			organisation.setLastModifiedBy(securityUtil.getCurrentUser());
			Organisation updatedOrg = orgRepo.save(organisation);
			response.setOrganisationId(updatedOrg.getId());
			response.setStatus(updatedOrg.getApprovalStatus());
			response.setComment(updatedOrg.getApprovalStatusComment());

			if (("APPROVED").equals(statusRequestDTO.getStatus())) {
				documentService.uploadDocsFromS3ToWorkDocs(organisationId);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_REVIEW_ADD_FAIL);
		}
		return response;
	}

	/**
	 * Find the organization by Id(String)
	 * 
	 * @author Arya C Achari
	 * @since 03-Nov-2021
	 * 
	 */
	@Override
	public Organisation findOrganisation(String id) {
		return this.orgRepo.findById(id)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_ID_NOT_FOUND));
	}

	@Override
	public OrgBasicDetailsResponse submitApprovalStatus(String organisationId) {
		Organisation organisation = orgRepo.findById(organisationId)
				.orElseThrow(() -> new SOException(ErrorCode.NOT_FOUND, Constants.ORG_ID_NOT_FOUND));
		try {
			organisation.setApprovalStatus("REVIEW");
			organisation.setModifiedAt(new Date().getTime());
			organisation.setLastModifiedBy(securityUtil.getCurrentUser());
			organisation = orgRepo.save(organisation);
			return getOrgBasicDetails(organisation.getId());
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.APPROVAL_STATUS_ADD_FAIL);
		}
	}

	private void checkApprovalStatus(String status) {
		ArrayList s = new ArrayList<>();
		s.add("NONE");
		s.add("REVIEW");
		s.add("IN_REVIEW");
		s.add("REWORK");
		s.add("APPROVED");
		s.add("NOT_RECOMMENDED");
		s.add("REJECTED");

		if (!s.contains(status)) {
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.APPROVAL_STATUS_INVALID);
		}

	}

	@Override
	public List<OrgBasicDetailsResponse> getAllOrgByIds(List<String> organisationIds) {
		List<Organisation> organisationList = orgRepo.findAllById(organisationIds);
		// organisationMapper.toBasicResponse(organisationList);
		return organisationMapper.toBasicResponse(organisationList);
	}

	/**
	 * @implNote This method wrote for mg-beneficiary service, for the API:
	 *           /v1/completion-detailed
	 * @author Arya C Achari
	 * @since 31-Dec-2021
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public OrgByNameResponse getOrganisationByName(String name) {
		try {
			return orgRepo.findOrganisationByName(name);
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_DATA_NOT_FOUND);
		}
	}

	@Override
	public List<String> getOrgOrganisationTypes(String registerAs) {
		try {
			List<String> organizationTypeList = new ArrayList<>();
			if (!StringUtils.isEmpty(registerAs)) {
				organizationTypeList = organisationTypeRepository.findByRegisterAs(registerAs.trim().toLowerCase());

			} else {
				organizationTypeList = organisationTypeRepository.findOrganisationTypes();
			}
			return organizationTypeList;
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_TYPES_FETCH_FAIL);
		}

	}

	@Override
	public List<String> getRegisteredAs(String organisationTypes) {

		try {
			List<String> organizationTypeList = new ArrayList<>();
			if (!StringUtils.isEmpty(organisationTypes)) {
				organizationTypeList = organisationTypeRepository
						.findByOrganisationTypes(organisationTypes.trim().toLowerCase());
			} else {
				organizationTypeList = organisationTypeRepository.findRegisterAs();
			}
			return organizationTypeList;
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_TYPES_FETCH_FAIL);
		}
	}

	@Override
	public List<OrganisationTypes> addOrgansationType(List<OrganisationTypesRequest> organisationTypesRequest) {

		List<OrganisationTypes> organisationTypesRequestList = new ArrayList<>();
		if (!organisationTypesRequest.isEmpty() && organisationTypesRequest != null) {
			organisationTypesRequest.forEach(organisationTypes -> {
				OrganisationTypes organisationTypesReq = new OrganisationTypes();
				organisationTypesReq.setRegisteredAs(organisationTypes.getRegisteredAs());
				organisationTypesReq.setType(organisationTypes.getType());
				organisationTypesReq.setDeleted(false);
				organisationTypesReq.setActive(true);
				organisationTypesReq.setCreatedAt(new Date().getTime());
				organisationTypesReq.setCreatedBy(securityUtil.getCurrentUser());
				
				organisationTypesRequestList.add(organisationTypesReq);
			});
			List<OrganisationTypes> organisationTypesRequestDB = organisationTypeRepository
					.saveAll(organisationTypesRequestList);
			return organisationTypesRequestDB;
		} else {
			return null;
		}

	}

	@Override
	public List<String> getOrganisationMembersRoles(String organisationTypes) {
		try {
			List<String> organisationMembersRoles = new ArrayList<>();
			if (!StringUtils.isEmpty(organisationTypes)) {
				organisationMembersRoles = organisationMembersRolesRepository
						.findByOrganisationTypes(organisationTypes.trim().toLowerCase());
			} else {
				organisationMembersRoles = organisationMembersRolesRepository.findRole();
			}
			return organisationMembersRoles;
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_MEMBER_ROLE_FETCH_FAIL);
		}
	}

	@Override
	public List<OrganisationMembersRoles> addOrganisationMembersRolesRequest(
			List<OrganisationMembersRolesRequest> organisationMembersRolesRequest) {
		List<OrganisationMembersRoles> organisationMembersRolesList = new ArrayList<>();
		if (!organisationMembersRolesRequest.isEmpty() && organisationMembersRolesRequest != null) {
			organisationMembersRolesRequest.forEach(organisationMembersRole -> {
				OrganisationMembersRoles organisationMembersRoles = new OrganisationMembersRoles();
				organisationMembersRoles.setOrganisationType(organisationMembersRole.getOrganisationType());
				organisationMembersRoles.setSortOrder(organisationMembersRole.getSortOrder());
				organisationMembersRoles.setRole(organisationMembersRole.getRole());
				organisationMembersRoles.setDeleted(false);
				organisationMembersRoles.setActive(true);
				organisationMembersRoles.setCreatedAt(new Date().getTime());
				organisationMembersRoles.setCreatedBy(securityUtil.getCurrentUser());

				organisationMembersRolesList.add(organisationMembersRoles);
			});
			List<OrganisationMembersRoles> organisationMembersRolesDB = organisationMembersRolesRepository
					.saveAll(organisationMembersRolesList);
			return organisationMembersRolesDB;
		} else {
			return null;
		}
	}

}
