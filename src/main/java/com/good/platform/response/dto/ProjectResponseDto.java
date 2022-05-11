package com.good.platform.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDto extends BaseResponse {

	private String name;
	private String organisationId;
	private Boolean isImmunizationProject;

}
