package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.BeneficiaryGmapLocations;
import com.good.platform.entity.BeneficiaryLocations;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationBeneficiaryHistory;
import com.good.platform.helper.MappingHelper;
import com.good.platform.request.dto.BeneficiaryGmapLocationsRequest;
import com.good.platform.request.dto.BeneficiaryLocationsRequest;
import com.good.platform.response.dto.BeneficiaryGmapLocationsResponse;
import com.good.platform.response.dto.BeneficiaryLocationsResponse;

@Mapper(componentModel = "spring", imports = MappingHelper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BenficiaryLocationsMapper {

	@Mappings({
		@Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
		@Mapping(target = "organisationBeneficiaryHistory", source = "beneficiaryHistoryId", qualifiedByName = "getOrganisationBeneficiaryHistory"),
		@Mapping(target = "beneficiaryLocations", source = "beneficiaryLocationsId", qualifiedByName = "getBeneficiaryLocations"),
		@Mapping(source = "request.id", target = "id"),
		@Mapping(source = "request.latitude", target = "latitude"),
		@Mapping(source = "request.longitude", target = "longitude")})
	BeneficiaryGmapLocations toGmapBeneficiaryLocation(BeneficiaryGmapLocationsRequest request, String beneficiaryLocationsId
			,String beneficiaryHistoryId,String organisationId);
	
	@Mappings({ 
		 @Mapping(target = "organisation", source = "organisationId", qualifiedByName = "getOrganisation"),
			@Mapping(target = "organisationBeneficiaryHistory", source = "beneficiaryHistoryId", qualifiedByName = "getOrganisationBeneficiaryHistory"),
			@Mapping(source = "request.id", target = "id"), 
			@Mapping(source = "request.location", target = "location"),
			@Mapping(source = "request.locationTitle", target = "locationTitle")})
	BeneficiaryLocations toBeneficiaryLocation(BeneficiaryLocationsRequest request,String beneficiaryHistoryId,String organisationId);
	
	@Named("getOrganisation")
	default Organisation mapOrganisationEntity(String organisationId) {

		return new Organisation(organisationId);

	}

	@Named("getOrganisationBeneficiaryHistory")
	default OrganisationBeneficiaryHistory mapOrganisationBeneficiaryHistoryEntity(String beneficiaryHistoryId) {

		return new OrganisationBeneficiaryHistory(beneficiaryHistoryId);
	}
	
	@Named("getBeneficiaryLocations")
	default BeneficiaryLocations mapBeneficiaryLocationsEntity(String beneficiaryLocationsId) {

		return  new BeneficiaryLocations(beneficiaryLocationsId);

	}

	BeneficiaryLocationsResponse mapEntityToResponse(BeneficiaryLocations location);

	BeneficiaryGmapLocationsResponse mapGmapLocationToResponse(BeneficiaryGmapLocations gmapLocation);

}
