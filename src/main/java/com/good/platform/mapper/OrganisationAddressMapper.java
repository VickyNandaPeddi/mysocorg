package com.good.platform.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationAddress;
import com.good.platform.model.OrganisationAddressModel;
import com.good.platform.response.dto.OrgAddressDetailsResponse;
import com.good.platform.service.CountriesService;
import com.good.platform.service.impl.CountriesServiceImpl;

/**
 * OrganisationAddressMapper is used for the purpose of mapping the data between entity,
 *  models, request and response
 * @author Mohamedsuhail S
 *
 */
@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationAddressMapper {
	
	/**
	 * organisationAddressBodyValuesMapper is to map the OrganisationAddressModel -> OrganisationAddress (Entity)
	 * @param model
	 * @param organisationEntity
	 * @return OrganisationAddress
	 */
	@Mappings({
    @Mapping(source = "organisationEntity", target = "organisation"),
    @Mapping(source = "model.id", target = "id"),
	@Mapping(source = "model.title", target = "title"),
    @Mapping(source = "model.address", target = "address"),
	@Mapping(source = "model.country", target = "country", qualifiedByName = "getHeadquarterCountry"),
	@Mapping(source = "model.state", target = "state"),
	@Mapping(source = "model.city", target = "city"),
	@Mapping(source = "model.pincode", target = "pincode"),
    @Mapping(source = "model.latitude", target = "latitude"),
    @Mapping(source = "model.longitude", target = "longitude"),
    @Mapping(source = "model.district", target = "district"),
    @Mapping(source = "model.taluk", target = "taluk")})
    public OrganisationAddress organisationAddressBodyValuesMapper(OrganisationAddressModel model,
    		Organisation organisationEntity);
    
	/**
	 * organisationAddressBodyUpdateValuesMapper is to map OrganisationAddressModel -> OrganisationAddress (Entity)
	 * @param model
	 * @param organisationEntity
	 * @return OrganisationAddress
	 */
	@Mappings({
    @Mapping(source = "organisationEntity", target = "organisation"),
    @Mapping(source = "model.id", target = "id"),
    @Mapping(source = "model.title", target = "title"),
    @Mapping(source = "model.address", target = "address"),
	@Mapping(source = "model.country", target = "country", qualifiedByName = "getHeadquarterCountry"),
	@Mapping(source = "model.state", target = "state"),
	@Mapping(source = "model.city", target = "city"),
	@Mapping(source = "model.pincode", target = "pincode"),
    @Mapping(source = "model.latitude", target = "latitude"),
    @Mapping(source = "model.longitude", target = "longitude"),
    @Mapping(source = "model.district", target = "district"),
    @Mapping(source = "model.taluk", target = "taluk")})
    public OrganisationAddress organisationAddressBodyUpdateValuesMapper(OrganisationAddressModel model,
    		Organisation organisationEntity);
    
    /**
     * organisationAddressBodyValuesMapper is to map OrganisationAddress (Entity) -> OrganisationAddressModel
     * @param model
     * @return OrganisationAddressModel
     */
	@Mappings({
			@Mapping(source = "model.id", target = "id"),
			@Mapping(source = "model.title", target = "title"),
		    @Mapping(source = "model.address", target = "address"),
			@Mapping(source = "model.country", target = "country", qualifiedByName = "getCountryIdByName"),
			@Mapping(source = "model.state", target = "state"),
			@Mapping(source = "model.city", target = "city"),
			@Mapping(source = "model.pincode", target = "pincode"),
		    @Mapping(source = "model.latitude", target = "latitude"),
		    @Mapping(source = "model.longitude", target = "longitude"),
		    @Mapping(source = "model.district", target = "district"),
		    @Mapping(source = "model.taluk", target = "taluk")
	})
    public OrganisationAddressModel organisationAddressBodyValuesMapper(OrganisationAddress model);
    
    /**
     * organisationAddressValuesMapperToResponse is to map list response of data to OrgAddressDetailsResponse (Response)
     * @param organisationId
     * @param entityList
     * @return OrgAddressDetailsResponse
     */
    @Mappings({
    @Mapping(source = "organisationId", target = "organisationId"),
    @Mapping(source = "entityList", target = "addressList"),
    @Mapping(source = "country", target = "countryId", qualifiedByName = "getCountryIdByName")})
    public OrgAddressDetailsResponse organisationAddressValuesMapperToResponse(String organisationId,
    		List<OrganisationAddressModel> entityList, String country);
    
    /**
	 * getHeadquarterCountry is to find the country from the list with requested country ID
	 * @param headquarterCountry
	 * @return
	 */
	@Named("getHeadquarterCountry")
	default String getHeadquarterCountry(Integer headquarterCountry) {
		CountriesService countriesService = new CountriesServiceImpl();
		return countriesService.getCountry(headquarterCountry);
	}
	
	@Named("getCountryIdByName")
	default Integer getCountryIdByName(String countryName) {
		if(countryName == null || countryName == "-- Select Country --") {
			return 0;
		}
		CountriesService countriesService = new CountriesServiceImpl();
		return Integer.parseInt(countriesService.getCountryByName(countryName));
	}
    
}
