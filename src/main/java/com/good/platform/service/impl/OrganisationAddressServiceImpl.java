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
import com.good.platform.entity.OrganisationAddress;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.OrganisationAddressMapper;
import com.good.platform.model.OrganisationAddressModel;
import com.good.platform.repository.OrganisationAddressRepository;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.request.dto.OrgAddressDetailsRequest;
import com.good.platform.response.dto.OrgAddressDetailsResponse;
import com.good.platform.service.CountriesService;
import com.good.platform.service.OrgAddressService;
import com.good.platform.utility.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * OrganisationAddressServiceImpl is to handle all the functionalities based on
 * the organisation address details Main Table: OrganisationAddress
 * 
 * @author Mohamedsuhail S
 *
 */
@Service
@Slf4j
public class OrganisationAddressServiceImpl implements OrgAddressService {

	@Autowired
	OrganisationRepository organisationRepo;

	@Autowired
	OrganisationAddressRepository orgAddressRepo;

	@Autowired
	OrganisationAddressMapper organisationAddressMapper;
	@Autowired
	SecurityUtil securityUtil;

	@Autowired
	CountriesService countriesService;

	/**
	 * addOrgAddressDetails is to add the address details in the OrganisationAddress
	 * table
	 * 
	 * @param OrgAddressDetailsRequest
	 * @return OrgAddressDetailsResponse
	 */
	@Override
	@Transactional
	public OrgAddressDetailsResponse addOrgAddressDetails(OrgAddressDetailsRequest request) {
		log.debug("Adding organisation address data save starts");
		if (request.getOrganisationId() == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		Organisation organisation = getOrganisationDetails(request.getOrganisationId());
		try {
			String updateUser = securityUtil.getCurrentUser();
			List<OrganisationAddressModel> organisationAddressList = new ArrayList<>();
			for (OrganisationAddressModel addresses : request.getAddressList()) {
				OrganisationAddress organisationAddressEntity = organisationAddressMapper
						.organisationAddressBodyValuesMapper(addresses, organisation);

				organisationAddressEntity.setCreatedBy(updateUser);
				OrganisationAddress savedEntity = orgAddressRepo.save(organisationAddressEntity);
				organisationAddressList.add(organisationAddressMapper.organisationAddressBodyValuesMapper(savedEntity));
			}
			String country = countriesService.getCountry(request.getCountryId());
			if (country.isEmpty() || country.isBlank()) {
				throw new Exception(Constants.INVALID_COUNTRY_ID);
			}
			Long modifiedAt = new Date().getTime();
			organisationRepo.updateHeadquater(country, request.getOrganisationId(), modifiedAt, updateUser);
			OrgAddressDetailsResponse orgAddressDetailsResponse = organisationAddressMapper
					.organisationAddressValuesMapperToResponse(request.getOrganisationId(), organisationAddressList,
							country);
			log.debug("Adding organisation address data ends");
			return orgAddressDetailsResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (Constants.INVALID_COUNTRY_ID.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_COUNTRY_ID);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_ADDRESS_DETAILS_ADD_FAIL);
			}
		}
	}

	/**
	 * updateOrgAddressDetails is to update the data of address
	 * 
	 * @param OrgAddressDetailsRequest
	 * @return OrgAddressDetailsResponse
	 */
	@Override
	@Transactional
	public OrgAddressDetailsResponse updateOrgAddressDetails(OrgAddressDetailsRequest request) {
		log.debug("Updating organisation address data update starts");
		if (request.getOrganisationId() == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		Organisation organisation = getOrganisationDetails(request.getOrganisationId());
		try {
			String updateUser = securityUtil.getCurrentUser();
			List<OrganisationAddressModel> organisationAddressList = new ArrayList<>();
			for (OrganisationAddressModel addresses : request.getAddressList()) {
				if (addresses.getId() == null || addresses.getId().isEmpty()) {
					OrganisationAddress organisationAddressEntity = organisationAddressMapper
							.organisationAddressBodyValuesMapper(addresses, organisation);
					organisationAddressEntity.setCreatedBy(updateUser);
					OrganisationAddress savedEntity = orgAddressRepo.save(organisationAddressEntity);
					organisationAddressList
							.add(organisationAddressMapper.organisationAddressBodyValuesMapper(savedEntity));
				} else {
					Optional<OrganisationAddress> organisationAddress = orgAddressRepo.findById(addresses.getId());
					if (!organisationAddress.isEmpty()) {
						OrganisationAddress organisationAddressEntity = organisationAddressMapper
								.organisationAddressBodyUpdateValuesMapper(addresses, organisation);
						organisationAddressEntity.setCreatedAt(organisationAddress.get().getCreatedAt());
						organisationAddressEntity.setCreatedBy(organisationAddress.get().getCreatedBy());
						organisationAddressEntity.setLastModifiedBy(updateUser);
						organisationAddressEntity.setModifiedAt(new Date().getTime());
						OrganisationAddress savedEntity = orgAddressRepo.save(organisationAddressEntity);
						organisationAddressList
								.add(organisationAddressMapper.organisationAddressBodyValuesMapper(savedEntity));
					}
				}
			}
			String country = countriesService.getCountry(request.getCountryId());
			if (country.isEmpty() || country.isBlank()) {
				throw new Exception(Constants.INVALID_COUNTRY_ID);
			}
			Long modifiedAt = new Date().getTime();
			organisationRepo.updateHeadquater(country, request.getOrganisationId(), modifiedAt, updateUser);
			OrgAddressDetailsResponse orgAddressDetailsResponse = organisationAddressMapper
					.organisationAddressValuesMapperToResponse(request.getOrganisationId(), organisationAddressList,
							country);
			log.debug("Updating organisation address data ends");
			return orgAddressDetailsResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_ADDRESS_DETAILS_UPDATE_FAIL);
		}
	}

	/**
	 * getOrgAddressData is to get the address data by using the ID of organisation
	 * fetches all the address details of the particular organisation
	 * 
	 * @param id
	 * @return OrgAddressDetailsResponse
	 */
	@Override
	public OrgAddressDetailsResponse getOrgAddressData(String id) {
		log.debug("Get organisation address data request starts");
		if (id == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORG_ID_NOT_FOUND);
		}
		Organisation existingOrganisationEntity = getOrganisationDetails(id);
		try {
			List<OrganisationAddressModel> modelList = new ArrayList<>();
			List<OrganisationAddress> organisationAddressList = existingOrganisationEntity.getAddress();
			if (organisationAddressList.isEmpty()) {
				throw new Exception(Constants.ORG_ADDRESS_DETAILS_NOT_FOUND);
			}
			for (OrganisationAddress organisationAddress : organisationAddressList) {
				modelList.add(organisationAddressMapper.organisationAddressBodyValuesMapper(organisationAddress));
			}
			OrgAddressDetailsResponse orgAddressDetailsResponse = organisationAddressMapper
					.organisationAddressValuesMapperToResponse(id, modelList,
							existingOrganisationEntity.getHeadquarterCountry());
			log.debug("Get organisation address data request ends");
			return orgAddressDetailsResponse;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			if (exception.getMessage().equals(Constants.ORG_ADDRESS_DETAILS_NOT_FOUND)) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ORG_ADDRESS_DETAILS_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_ADDRESS_DETAILS_FETCH_FAIL);
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
	public Boolean deleteOrgAddress(String addressId, String organisationId) {
		if (organisationId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ORGANISATION_ID_MISSING);
		}
		if (addressId == null) {
			throw new SOException(ErrorCode.BAD_REQUEST, Constants.ADDRESS_ID_MISSING);
		}
		try {
			orgAddressRepo.deleteByIdAndOrganisation_Id(addressId, organisationId);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ORG_ADDRESS_DETAILS_DELETE_FAIL);
		}
	}

}
