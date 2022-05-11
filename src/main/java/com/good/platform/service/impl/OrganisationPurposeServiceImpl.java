package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationMissionStatements;
import com.good.platform.entity.OrganisationSector;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.OrganisationMissionStatementMapper;
import com.good.platform.mapper.OrganisationPurposeDetailsMapper;
import com.good.platform.mapper.OrganisationSectorsMapper;
import com.good.platform.model.OrganisationMissionStatementsModel;
import com.good.platform.model.OrganisationSectorModel;
import com.good.platform.repository.OrganisationMissionStatementsRepository;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.OrganisationSectorRepository;
import com.good.platform.request.dto.OrgPurposeDetailsRequest;
import com.good.platform.response.dto.OrgPurposeDetailsResponse;
import com.good.platform.service.OrganisationPurposeService;
import com.good.platform.utility.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * OrganisationPurposeServiceImpl is to handle all the business functionalities of the organisation purpose data
 * @author Mohamedsuhail S
 *
 */
@Service
@Slf4j
public class OrganisationPurposeServiceImpl implements OrganisationPurposeService{
	
	@Autowired
	OrganisationRepository organisationRepo;
	
	@Autowired
	OrganisationMissionStatementsRepository organisationMissionStatementsRepo;
	
	@Autowired
	OrganisationSectorRepository organisationSectorRepo;
	
	@Autowired
	OrganisationMissionStatementMapper organisationMissionStatementMapper;
	
	@Autowired
	OrganisationSectorsMapper organisationSectorsMapper;
	
	@Autowired
	OrganisationPurposeDetailsMapper organisationPurposeDetailsMapper;
	
	@Autowired
	SecurityUtil securityUtil;
	/**
	 *addOrganisationPurposeDetails is to add the details regarding the purpose of the organisation.
	 *@param OrgPurposeDetailsRequest
	 */
	@Override
	@Transactional
	public OrgPurposeDetailsResponse addOrganisationPurposeDetails(OrgPurposeDetailsRequest orgPurposeDetailsRequest) {
		log.debug("Organisation purpose details save request starts");
		OrgPurposeDetailsResponse orgPurposeDetailsResponse = null;
		Organisation existingUserEntity = getOrganisationDetails(orgPurposeDetailsRequest.getOrganisationId());
		try {
			String user = securityUtil.getCurrentUser();
			List<OrganisationMissionStatementsModel> organisationMissionStatementsList = new ArrayList<>();
			//iterating through the multiple mission statements in the request payload and converting them into entity for saving
			for(OrganisationMissionStatementsModel organisationMissionStatements : orgPurposeDetailsRequest.getMissionStatements()) {
				OrganisationMissionStatements missionStatementEntity = organisationMissionStatementMapper
						.organisationMissionStatementsValuesMapper(organisationMissionStatements,existingUserEntity);
			
				missionStatementEntity.setCreatedBy(user);
				OrganisationMissionStatements savedEntity = organisationMissionStatementsRepo.save(missionStatementEntity);
				organisationMissionStatementsList.add(organisationMissionStatementMapper
						.organisationMissionStatementsValuesMapper(orgPurposeDetailsRequest.getOrganisationId(), savedEntity));
			}
			List<OrganisationSectorModel> sectorsList = new ArrayList<>();
			//iterating through the multiple sector types in the request payload and converting them into entity for saving
			for(OrganisationSectorModel sector : orgPurposeDetailsRequest.getSectors()) {
				OrganisationSector organisationSector = organisationSectorsMapper.organisationSectorValuesMapper(sector,
						existingUserEntity);
				organisationSector.setCreatedBy(user);
				OrganisationSector savedEntity = organisationSectorRepo.save(organisationSector);
				sectorsList.add(organisationSectorsMapper.organisationSectorValuesMapper(orgPurposeDetailsRequest.getOrganisationId(),
						savedEntity));
			}
			existingUserEntity.setAbout(orgPurposeDetailsRequest.getAbout());
			existingUserEntity.setVisionStatement(orgPurposeDetailsRequest.getVisionStatement());
			existingUserEntity.setValueStatement(orgPurposeDetailsRequest.getValueStatement());
			existingUserEntity.setCreatedAt(existingUserEntity.getCreatedAt());
			existingUserEntity.setCreatedBy(existingUserEntity.getCreatedBy());
			existingUserEntity.setModifiedAt(new Date().getTime());
			existingUserEntity.setLastModifiedBy(user);
			organisationRepo.save(existingUserEntity);
			orgPurposeDetailsResponse = organisationPurposeDetailsMapper.organisationPurposeValuesMapper(orgPurposeDetailsRequest.getOrganisationId(),
					organisationMissionStatementsList,sectorsList,orgPurposeDetailsRequest.getAbout(),orgPurposeDetailsRequest.getVisionStatement(),
					orgPurposeDetailsRequest.getValueStatement());
			log.debug("Organisation purpose details save request ends");
			return orgPurposeDetailsResponse;
		}catch(Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_PURPOSE_DETAILS_ADD_FAIL);
		}
	}
	
	/**
	 * updateOrganisationPurposeDetails is to update the organisation purpose details
	 * @param orgPurposeDetailsRequest
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public OrgPurposeDetailsResponse updateOrganisationPurposeDetails(OrgPurposeDetailsRequest orgPurposeDetailsRequest) {
		log.debug("Organisation purpose details update request starts");
		OrgPurposeDetailsResponse orgPurposeDetailsResponse = null;
		Organisation existingUserEntity = getOrganisationDetails(orgPurposeDetailsRequest.getOrganisationId());
		try {
			String user = securityUtil.getCurrentUser();
			List<OrganisationMissionStatementsModel> organisationMissionStatementsList = new ArrayList<>();
			for(OrganisationMissionStatementsModel organisationMissionStatements : orgPurposeDetailsRequest.getMissionStatements()) {
				if(organisationMissionStatements.getId() == null || organisationMissionStatements.getId().isEmpty()) {
					OrganisationMissionStatements missionStatementEntity = organisationMissionStatementMapper
							.organisationMissionStatementsValuesMapper(organisationMissionStatements,existingUserEntity);
					missionStatementEntity.setCreatedBy(user);
					OrganisationMissionStatements savedEntity = organisationMissionStatementsRepo.save(missionStatementEntity);
					organisationMissionStatementsList.add(organisationMissionStatementMapper
							.organisationMissionStatementsValuesMapper(orgPurposeDetailsRequest.getOrganisationId(), savedEntity));
				}else {
					Optional<OrganisationMissionStatements> existingMissionStatementEntity = organisationMissionStatementsRepo.findById(organisationMissionStatements.getId());
					if(!existingMissionStatementEntity.isEmpty()) {
						OrganisationMissionStatements missionStatementEntity = existingMissionStatementEntity.get();
						missionStatementEntity = organisationMissionStatementMapper.organisationMissionStatementsUpdateValuesMapper(
								organisationMissionStatements,existingUserEntity);
						missionStatementEntity.setCreatedAt( existingMissionStatementEntity.get().getCreatedAt());
						missionStatementEntity.setCreatedBy( existingMissionStatementEntity.get().getCreatedBy());
						missionStatementEntity.setModifiedAt(new Date().getTime());
						missionStatementEntity.setLastModifiedBy(user);
						OrganisationMissionStatements savedEntity = organisationMissionStatementsRepo.save(missionStatementEntity);
						organisationMissionStatementsList.add(organisationMissionStatementMapper
								.organisationMissionStatementsValuesMapper(orgPurposeDetailsRequest.getOrganisationId(), savedEntity));
					}
				}
			}

			List<OrganisationSector> orgSectors =organisationSectorRepo.findByOrganisationId( existingUserEntity.getId());
			if(orgSectors != null && !orgSectors.isEmpty()) {
			organisationSectorRepo.deleteAll(orgSectors);
			}
			List<OrganisationSectorModel> sectorsList = new ArrayList<>();
			for(OrganisationSectorModel sector : orgPurposeDetailsRequest.getSectors()) {
				
					OrganisationSector organisationSector = organisationSectorsMapper.organisationSectorValuesMapper(sector,
							existingUserEntity);
					organisationSector.setId(null);
					organisationSector.setCreatedBy(user);
					OrganisationSector savedEntity = organisationSectorRepo.save(organisationSector);
					sectorsList.add(organisationSectorsMapper.organisationSectorValuesMapper(orgPurposeDetailsRequest.getOrganisationId(),
							savedEntity));
				}
			
			existingUserEntity.setAbout(orgPurposeDetailsRequest.getAbout());
			existingUserEntity.setVisionStatement(orgPurposeDetailsRequest.getVisionStatement());
			existingUserEntity.setValueStatement(orgPurposeDetailsRequest.getValueStatement());
			existingUserEntity.setCreatedAt(existingUserEntity.getCreatedAt());
			existingUserEntity.setCreatedBy(existingUserEntity.getCreatedBy());
			existingUserEntity.setModifiedAt(new Date().getTime());
			existingUserEntity.setLastModifiedBy(user);
			organisationRepo.save(existingUserEntity);
			orgPurposeDetailsResponse = organisationPurposeDetailsMapper.organisationPurposeValuesMapper(orgPurposeDetailsRequest.getOrganisationId(),
					organisationMissionStatementsList,sectorsList,orgPurposeDetailsRequest.getAbout(),orgPurposeDetailsRequest.getVisionStatement(),
					orgPurposeDetailsRequest.getValueStatement());
			log.debug("Organisation purpose details update request ends");
			return orgPurposeDetailsResponse;
		}catch(Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_PURPOSE_DETAILS_UPDATE_FAIL);
		}
	}
	
	/**
	 * getOrganisationPurposeDetails is to get the organisation purpose details by organisation ID
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public OrgPurposeDetailsResponse getOrganisationPurposeDetails(String id) {
		log.debug("Fetch purpose details starts");
		OrgPurposeDetailsResponse orgPurposeDetailsResponse = null;
		Organisation existingOrganisationEntity = getOrganisationDetails(id);
		try {
			List<OrganisationMissionStatementsModel> organisationMissionStatementsList = new ArrayList<>();
			List<OrganisationSectorModel> sectorsList = new ArrayList<>();
			List<OrganisationMissionStatements> existingMissionStatementsList = existingOrganisationEntity.getMissionStatements();
			
			List<OrganisationSector> existingSectorList = existingOrganisationEntity.getSectors();
			if(!existingMissionStatementsList.isEmpty()) {
				for(OrganisationMissionStatements organisationMissionStatements : existingMissionStatementsList) {
					organisationMissionStatementsList.add(organisationMissionStatementMapper
							.organisationMissionStatementsValuesMapper(id, organisationMissionStatements));
				}
			}
			
			if(!existingSectorList.isEmpty() && existingSectorList != null ) {
				for(OrganisationSector organisationSector : existingSectorList) {
					
					sectorsList.add(organisationSectorsMapper.organisationSectorValuesMapper(id, organisationSector));
				}
			}
			orgPurposeDetailsResponse = organisationPurposeDetailsMapper.organisationPurposeValuesMapper(id,
					organisationMissionStatementsList,sectorsList,existingOrganisationEntity.getAbout(),existingOrganisationEntity.getVisionStatement(),
					existingOrganisationEntity.getValueStatement());
			log.debug("Organisation purpose details update request ends");
			return orgPurposeDetailsResponse;
		}catch(Exception exception){
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_PURPOSE_DETAILS_FETCH_FAIL);
		}
	}
	
	/**
	 * organisation is to get the existing organisational details
	 * @param id
	 * @return
	 */
	private Organisation getOrganisationDetails(String id) {
		log.debug("Fetching organisation basic data starts");
		Optional<Organisation> organisation = organisationRepo.findById(id);
		if(organisation.isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
		}
		log.debug("Fetching organisation basic data ends");
		return organisation.get();
	}

	@Override
	@Transactional
	public Boolean deleteMissionStatement(String missionStatementId, String organisationId) {
		if(organisationId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
	   if(missionStatementId == null) {
		   throw new SOException(ErrorCode.BAD_REQUEST, Constants.MISSION_STATEMENT_ID_MISSING);
		}
		try {
			organisationMissionStatementsRepo.deleteByIdAndOrganisation_Id(missionStatementId,organisationId);
		return true;
		}
		catch(Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_MISSION_STATEMENT_DELETE_FAIL);
		}
	}

}
