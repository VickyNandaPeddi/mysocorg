package com.good.platform.model;

import com.good.platform.enums.SectorType;

import lombok.Getter;
import lombok.Setter;

/**
 * OrganisationSectorModel represents the sector object model
 * @author Mohamedsuhail S
 *
 */
@Getter
@Setter
public class OrganisationSectorModel {
	
	private String id;
	//sectors-title
	private String sector;
	//PRIMARY OR SECONDARY
	private SectorType type;
	//sectors-id
	private Long sectorId;

}
