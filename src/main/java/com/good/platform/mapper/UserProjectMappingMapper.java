package com.good.platform.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.UserProjectMapping;
import com.good.platform.response.dto.UserProjectSyncDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy =NullValueCheckStrategy.ALWAYS)
public interface UserProjectMappingMapper  {

	
	@Mappings({@Mapping(target = "userId", source = "userProjectMapping.user.id")})
	UserProjectSyncDto toDto(UserProjectMapping userProjectMapping);
	
	List<UserProjectSyncDto> toDto(List<UserProjectMapping> userProjectMapping);

}
