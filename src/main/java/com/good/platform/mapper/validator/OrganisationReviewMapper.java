package com.good.platform.mapper.validator;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.User;
import com.good.platform.entity.validator.OrganisationReview;
import com.good.platform.entity.validator.ValidatorDetails;
import com.good.platform.helper.MappingHelper;
import com.good.platform.helper.validator.ValidatorMappingHelper;
import com.good.platform.request.dto.validator.ValidatorDetailsDTO;
import com.good.platform.response.validator.OrganisationReviewResponseDTO;
import com.good.platform.response.validator.ValidatorResponseDTO;


@Mapper(componentModel = "spring",imports = ValidatorMappingHelper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganisationReviewMapper {
	  @Mappings({
	    	@Mapping(source = "review.organisation.id", target = "organisationId")})
	public OrganisationReviewResponseDTO toResponseDTO(OrganisationReview review);
    
  

}
