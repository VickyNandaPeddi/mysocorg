package com.good.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.OrganisationSector;
import com.good.platform.enums.OrgSectors;
import com.good.platform.helper.MappingHelper;
import com.good.platform.model.OrganisationSectorModel;

/**
 * OrganisationSectorsMapper is to handle all the mapping requirement between objects in sector part of organisation purpose
 * page
 * @author Mohamedsuhail S
 *
 */
@Mapper(componentModel = "spring",imports = MappingHelper.class, nullValuePropertyMappingStrategy =NullValuePropertyMappingStrategy.SET_TO_NULL,
nullValueCheckStrategy =NullValueCheckStrategy.ALWAYS)
public interface OrganisationSectorsMapper {
	
	/**
	 * organisationSectorValuesMapper is to represent mapping from OrganisationSectorModel(Model) -> OrganisationSector (Entity)
	 * @param model
	 * @param organisation
	 * @return
	 */
	@Mapping(source = "organisation", target = "organisation")
    @Mapping(source = "model.id", target = "id", ignore = true)
	@Mapping(target = "sector", expression = "java(MappingHelper.mapSectors(model.getSectorId()))")
	@Mapping(source = "model.type", target = "type")
    public OrganisationSector organisationSectorValuesMapper(OrganisationSectorModel model, Organisation organisation);
	
	/**
	 * organisationSectorUpdateValuesMapper is to represent mapping from OrganisationSectorModel(Model) -> OrganisationSector (Entity)
	 * for updating process
	 * @param model
	 * @param organisation
	 * @return
	 */
	@Mapping(source = "organisation", target = "organisation")
      @Mapping(target = "sector", expression = "java(MappingHelper.mapSectors(model.getSectorId()))")
    @Mapping(source = "model.id", target = "id")
	@Mapping(source = "model.type", target = "type")
    public OrganisationSector organisationSectorUpdateValuesMapper(OrganisationSectorModel model, Organisation organisation);
    
	/**
	 * organisationSectorValuesMapper is to represent mapping of OrganisationSector (Entity) -> OrganisationSectorModel(Model)
	 * @param organisationId
	 * @param entity
	 * @return
	 */
	@Mapping(target = "sector", expression = "java(MappingHelper.mapSectorsName(entity.getSector().getId()))")
	@Mapping( target = "sectorId", expression = "java(MappingHelper.mapSectorsId(entity.getSector().getId()))")
	@Mapping(source = "entity.id", target = "id")
    public OrganisationSectorModel organisationSectorValuesMapper(String organisationId,OrganisationSector entity);
	
	/**
	 * getSectorType is to find the country from the list with requested country ID
	 * @param headquarterCountry
	 * @return
	 */
	@Named("getSectorType")
	default OrgSectors getSectorType(String sectorType) {
		if(sectorType == null || sectorType.isEmpty()) {
			return null;
		}
		OrgSectors enumValue = OrgSectors.getByName(sectorType);
		return enumValue;
	}
	
	/**
	 * getSectorTypeString is to find the country from the list with requested country ID
	 * @param headquarterCountry
	 * @return
	 */
	@Named("getSectorTypeString")
	default String getSectorTypeString(OrgSectors sectorType) {
		return sectorType.toString();
	}
    
}
