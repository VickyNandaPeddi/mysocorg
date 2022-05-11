package com.good.platform.mapper.validator;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.User;
import com.good.platform.entity.validator.ValidatorDetails;
import com.good.platform.helper.MappingHelper;
import com.good.platform.helper.validator.ValidatorMappingHelper;
import com.good.platform.request.dto.validator.ValidatorDetailsDTO;
import com.good.platform.response.validator.ValidatorResponseDTO;


@Mapper(componentModel = "spring",imports = ValidatorMappingHelper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ValidatorDetailsMapper {
	
    @Mappings({
    	@Mapping(source = "dto.organisation", target = "organisation"),
    	@Mapping(target = "user", expression = "java(ValidatorMappingHelper.getUserObject(validatorId))")})
    public ValidatorDetails toEntity(ValidatorDetailsDTO dto,String validatorId);

    
    @Mappings({
    	@Mapping(source = "user.id", target = "id"),
    	@Mapping(source = "user.firstName", target = "firstName"),
    	@Mapping(source = "user.lastName", target = "lastName"),
    	@Mapping(source = "user.middleName", target = "middleName"),
    	@Mapping(source = "user.phone", target = "phone"),
    	@Mapping(source = "validatorDetails.organisation", target = "organisation"),
    	@Mapping(source = "validatorDetails.document1Url", target = "document1Url"),
    	@Mapping(source = "validatorDetails.document2Url", target = "document2Url"),
    	@Mapping(source = "validatorDetails.document1Name", target = "document1Name"),
    	@Mapping(source = "validatorDetails.document2Name", target = "document2Name")})
	public ValidatorResponseDTO toValidatorResponseDTO(ValidatorDetails validatorDetails, User user);
    
  

}
