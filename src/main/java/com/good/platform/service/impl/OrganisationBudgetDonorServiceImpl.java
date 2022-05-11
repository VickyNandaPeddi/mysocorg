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
import com.good.platform.entity.OrganisationBudgetHistory;
import com.good.platform.entity.OrganisationDonorHistory;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.OrganisationBudgetDonorHistoryMapper;
import com.good.platform.mapper.OrganisationBudgetHistoryMapper;
import com.good.platform.mapper.OrganisationDonorHistoryMapper;
import com.good.platform.model.OrganisationBudgetHistoryModel;
import com.good.platform.model.OrganisationDonorHistoryModel;
import com.good.platform.repository.FinancialYearsRepository;
import com.good.platform.repository.OrganisationBudgetHistoryRepository;
import com.good.platform.repository.OrganisationDonorHistoryRepository;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.request.dto.OrgBudgetDonorRequest;
import com.good.platform.response.dto.OrgBudgetDonorResponse;
import com.good.platform.service.OrganisationBudgetDonorService;
import com.good.platform.utility.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * OrganisationBudgetDonorServiceImpl is to handle all the business
 * functionalities of the budget and donor history details
 * 
 * @author Mohamedsuhail S
 *
 */
@Service
@Slf4j
public class OrganisationBudgetDonorServiceImpl implements OrganisationBudgetDonorService {

	@Autowired
	OrganisationRepository organisationRepo;

	@Autowired
	OrganisationBudgetHistoryRepository organisationBudgetHistoryRepository;

	@Autowired
	OrganisationDonorHistoryRepository organisationDonorHistoryRepository;

	@Autowired
	OrganisationBudgetHistoryMapper organisationBudgetHistoryMapper;

	@Autowired
	OrganisationDonorHistoryMapper organisationDonorHistoryMapper;

	@Autowired
	OrganisationBudgetDonorHistoryMapper organisationBudgetDonorHistoryMapper;

	@Autowired
	FinancialYearsRepository financialYearsRepository;

	@Autowired
	SecurityUtil securityUtil;

	/**
	 * addBudgetDonorHistoryDetails is to save the organisation budget and donor
	 * history data
	 * 
	 * @param OrgBudgetDonorRequest
	 */
	@Override
	@Transactional
	public OrgBudgetDonorResponse addBudgetDonorHistoryDetails(OrgBudgetDonorRequest orgBudgetDonorRequest) {
		log.debug("Organisation budget and donor history data save starts");
		Organisation organisation = getOrganisationData(orgBudgetDonorRequest.getOrganisationId());
		try {
			String updateUser = securityUtil.getCurrentUser();
			List<OrganisationBudgetHistoryModel> organisationBudgetHistoryList = new ArrayList<>();
			for (OrganisationBudgetHistoryModel organisationBudgetHistory : orgBudgetDonorRequest.getBudgetHistory()) {
				List<OrganisationDonorHistoryModel> organisationDonorHistoryList = new ArrayList<>();
				OrganisationBudgetHistory budgetHistoryEntity = organisationBudgetHistoryMapper
						.organisationBudgetHistoryValuesMapper(organisationBudgetHistory, organisation,
								organisationBudgetHistory.getFinancialYear());
				budgetHistoryEntity.setCreatedBy(updateUser);
				OrganisationBudgetHistory organisationBudgetHistorySavedEntity = organisationBudgetHistoryRepository
						.save(budgetHistoryEntity);
				for (OrganisationDonorHistoryModel organisationDonorHistoryIterator : organisationBudgetHistory
						.getDonorHistory()) {
					OrganisationDonorHistory organisationDonorHistory = organisationDonorHistoryMapper
							.organisationDonorHistoryValuesMapper(organisationDonorHistoryIterator,
									orgBudgetDonorRequest.getOrganisationId());
					organisationDonorHistory.setBudgetHistory(organisationBudgetHistorySavedEntity);
					organisationDonorHistory.setCreatedBy(updateUser);
					OrganisationDonorHistory savedDonorEntity = organisationDonorHistoryRepository
							.save(organisationDonorHistory);
					organisationDonorHistoryList
							.add(organisationDonorHistoryMapper.organisationDonorHistoryValuesMapper(savedDonorEntity,
									orgBudgetDonorRequest.getOrganisationId()));
				}
				OrganisationBudgetHistoryModel model = organisationBudgetHistoryMapper
						.organisationBudgetHistoryValuesMapper(organisationBudgetHistorySavedEntity,
								orgBudgetDonorRequest.getOrganisationId(), organisationDonorHistoryList);
				model.setDonorHistory(organisationDonorHistoryList);
				organisationBudgetHistoryList.add(model);
			}
			OrgBudgetDonorResponse orgBudgetDonorResponse = organisationBudgetDonorHistoryMapper
					.organisationBudgetDonorHistoryValuesMapper(orgBudgetDonorRequest.getOrganisationId(),
							organisationBudgetHistoryList);
			log.debug("Organisation budget and donor history data save ends");
			return orgBudgetDonorResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_ADD_FAIL);
		}
	}

	/**
	 * updateBudgetDonorHistoryDetails is to update the budget and donor details
	 */
	@Override
	@Transactional
	public OrgBudgetDonorResponse updateBudgetDonorHistoryDetails(OrgBudgetDonorRequest orgBudgetDonorRequest) {
		log.debug("Organisation budget and donor history data update starts");
		Organisation organisation = getOrganisationData(orgBudgetDonorRequest.getOrganisationId());
		try {
			String updateUser = securityUtil.getCurrentUser();
			List<OrganisationBudgetHistoryModel> organisationBudgetHistoryList = new ArrayList<>();
			for (OrganisationBudgetHistoryModel organisationBudgetHistory : orgBudgetDonorRequest.getBudgetHistory()) {
				List<OrganisationDonorHistoryModel> organisationDonorHistoryList = new ArrayList<>();
				
				OrganisationBudgetHistory budgetHistoryEntity = null;
				if(organisationBudgetHistory.getBudgetId() == null || organisationBudgetHistory.getBudgetId().isEmpty()) {
					budgetHistoryEntity = organisationBudgetHistoryMapper
							.organisationBudgetHistoryValuesMapper(organisationBudgetHistory, organisation,
									organisationBudgetHistory.getFinancialYear());
					budgetHistoryEntity.setCreatedBy(updateUser);
				}else {
					Optional<OrganisationBudgetHistory> budgetHistory = organisationBudgetHistoryRepository
							.findById(organisationBudgetHistory.getBudgetId());
					budgetHistoryEntity = organisationBudgetHistoryMapper
							.organisationBudgetHistoryValuesMapper(organisationBudgetHistory, organisation,
									organisationBudgetHistory.getFinancialYear());
					budgetHistoryEntity.setId(budgetHistory.get().getId());
					budgetHistoryEntity.setCreatedBy(budgetHistory.get().getCreatedBy());
					budgetHistoryEntity.setModifiedAt(new Date().getTime());
					budgetHistoryEntity.setLastModifiedBy(updateUser);
				}
				OrganisationBudgetHistory organisationBudgetHistorySavedEntity = organisationBudgetHistoryRepository
						.save(budgetHistoryEntity);
				for (OrganisationDonorHistoryModel organisationDonorHistoryIterator : organisationBudgetHistory
						.getDonorHistory()) {
					if (organisationDonorHistoryIterator.getDonorId() == null
							|| organisationDonorHistoryIterator.getDonorId().isEmpty()) {
						OrganisationDonorHistory organisationDonorHistory = organisationDonorHistoryMapper
								.organisationDonorHistoryValuesMapper(organisationDonorHistoryIterator,
										orgBudgetDonorRequest.getOrganisationId());
						organisationDonorHistory.setBudgetHistory(organisationBudgetHistorySavedEntity);
						organisationDonorHistory.setCreatedBy(updateUser);
						OrganisationDonorHistory savedDonorEntity = organisationDonorHistoryRepository
								.save(organisationDonorHistory);
						organisationDonorHistoryList.add(
								organisationDonorHistoryMapper.organisationDonorHistoryValuesMapper(savedDonorEntity,
										orgBudgetDonorRequest.getOrganisationId()));
					} else {
						Optional<OrganisationDonorHistory> donorExisting = organisationDonorHistoryRepository
								.findById(organisationDonorHistoryIterator.getDonorId());
						if (donorExisting.isEmpty()) {
							throw new Exception(Constants.ORG_DONOR_HISTORY_DETAILS_NOT_FOUND);
						}

						OrganisationDonorHistory organisationDonorHistory = organisationDonorHistoryMapper
								.organisationDonorHistoryValuesMapper(organisationDonorHistoryIterator,
										orgBudgetDonorRequest.getOrganisationId());
						organisationDonorHistory.setCreatedAt(donorExisting.get().getCreatedAt());
						organisationDonorHistory.setModifiedAt(new Date().getTime());
						organisationDonorHistory.setLastModifiedBy(updateUser);
						organisationDonorHistory.setBudgetHistory(organisationBudgetHistorySavedEntity);
						OrganisationDonorHistory savedDonorEntity = organisationDonorHistoryRepository
								.save(organisationDonorHistory);
						organisationDonorHistoryList.add(
								organisationDonorHistoryMapper.organisationDonorHistoryValuesMapper(savedDonorEntity,
										orgBudgetDonorRequest.getOrganisationId()));
					}
				}
				OrganisationBudgetHistoryModel model = organisationBudgetHistoryMapper
						.organisationBudgetHistoryValuesMapper(organisationBudgetHistorySavedEntity,
								orgBudgetDonorRequest.getOrganisationId(), organisationDonorHistoryList);
				model.setDonorHistory(organisationDonorHistoryList);
				organisationBudgetHistoryList.add(model);
			}
			OrgBudgetDonorResponse orgBudgetDonorResponse = organisationBudgetDonorHistoryMapper
					.organisationBudgetDonorHistoryValuesMapper(orgBudgetDonorRequest.getOrganisationId(),
							organisationBudgetHistoryList);
			log.debug("Organisation budget and donor history data save ends");
			return orgBudgetDonorResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.ORG_BUDGET_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BUDGET_DETAILS_NOT_FOUND);
			} else if (Constants.ORG_DONOR_HISTORY_DETAILS_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_DONOR_HISTORY_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR,
						Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_UPDATE_FAIL);
			}
		}
	}

	/**
	 * getBudgetDonorHistoryDetails is to fetch the budget and donor details
	 */
	@Override
	public OrgBudgetDonorResponse getBudgetDonorHistoryDetails(String organisationId) {
		log.debug("Organisation budget and donor history data fetch starts");
		try {
			List<OrganisationBudgetHistoryModel> model = organisationBudgetHistoryRepository
					.getBudgetDonorHistoryByOrganisation(organisationId);

			for (OrganisationBudgetHistoryModel iterate : model) {
				iterate.setDonorHistory(organisationDonorHistoryRepository.getByOrganisationAndBudgetId(organisationId,
						iterate.getBudgetId()));
			}
			log.debug("Organisation budget and donor history data save ends");
			return new OrgBudgetDonorResponse(organisationId, model);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR,
					Constants.ORG_BUDGET_DONOR_HISTORY_DETAILS_FETCH_FAIL);
		}
	}

	/**
	 * getOrganisationData is to get the organisation details
	 * 
	 * @param organisationId
	 * @return
	 */
	private Organisation getOrganisationData(String organisationId) {
		Optional<Organisation> organisation = organisationRepo.findById(organisationId);
		if (organisation.isEmpty()) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_BASIC_DETAILS_NOT_FOUND);
		}
		return organisation.get();
	}

	@Override
	@Transactional
	public Boolean deleteOrgDonorHistory(String organisationId, String donorId) {
		if (organisationId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		if (donorId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.DONOR_ID_MISSING);
		}
		try {
			organisationDonorHistoryRepository.deleteByIdAndOrganisation_Id(donorId, organisationId);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_ADDRESS_DETAILS_DELETE_FAIL);
		}
	}
}
