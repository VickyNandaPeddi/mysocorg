package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.EmailEntity;
import com.good.platform.request.dto.EmailRequestDto;
import com.good.platform.response.dto.EmailResponseDto;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EmailMapper {
	
    @Mappings({
    	@Mapping(source = "validatorTokenId", target = "validatorTokenId"),
    	@Mapping(source = "email", target = "email"),
    	@Mapping(source = "role", target = "role"),
    	@Mapping(source = "isExpired", target = "isExpired"),
    	@Mapping(source = "timeSpan", target = "timeSpan"),
    	@Mapping(source = "expirationStartTime", target = "expirationStartTime"),
    	@Mapping(source = "expirationTime", target = "expirationTime")
    })
    public EmailEntity emailBodyValuesMapper(EmailRequestDto dto);
    
    @Mappings({
    	@Mapping(source = "validatorTokenId", target = "validatorTokenId"),
    	@Mapping(source = "email", target = "email"),
    	@Mapping(source = "role", target = "role"),
    	@Mapping(source = "isExpired", target = "isExpired"),
    	@Mapping(source = "timeSpan", target = "timeSpan"),
    	@Mapping(source = "expirationStartTime", target = "expirationStartTime"),
    	@Mapping(source = "expirationTime", target = "expirationTime")
    })
    public EmailRequestDto emailBodyValuesMapper(EmailEntity entity);
    
    @Mappings({
    	@Mapping(source = "validatorTokenId", target = "validatorTokenId"),
    	@Mapping(source = "email", target = "email"),
    	@Mapping(source = "role", target = "role"),
    	@Mapping(source = "isExpired", target = "isExpired"),
    	@Mapping(source = "timeSpan", target = "timeSpan"),
    	@Mapping(source = "expirationStartTime", target = "expirationStartTime"),
    	@Mapping(source = "expirationTime", target = "expirationTime")
    })
    public EmailRequestDto emailBodyValuesMapper(EmailResponseDto entity);

    @Mappings({
    	@Mapping(source = "validatorTokenId", target = "validatorTokenId"),
    	@Mapping(source = "email", target = "email"),
    	@Mapping(source = "role", target = "role"),
    	@Mapping(source = "isExpired", target = "isExpired"),
    	@Mapping(source = "timeSpan", target = "timeSpan"),
    	@Mapping(source = "expirationStartTime", target = "expirationStartTime"),
    	@Mapping(source = "expirationTime", target = "expirationTime")
    })
    public EmailResponseDto emailBodyEntityToResponseMapper(EmailEntity entity);
    
    @Mappings({
    	@Mapping(source = "validatorTokenId", target = "validatorTokenId"),
    	@Mapping(source = "email", target = "email"),
    	@Mapping(source = "role", target = "role"),
    	@Mapping(source = "isExpired", target = "isExpired"),
    	@Mapping(source = "timeSpan", target = "timeSpan"),
    	@Mapping(source = "expirationStartTime", target = "expirationStartTime"),
    	@Mapping(source = "expirationTime", target = "expirationTime")
    })
    public EmailResponseDto emailBodyRequestToResponseMapper(EmailRequestDto entity);

}
