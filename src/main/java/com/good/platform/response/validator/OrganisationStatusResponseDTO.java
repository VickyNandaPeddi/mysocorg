package com.good.platform.response.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrganisationStatusResponseDTO {
	
	private String organisationId;
	private String approvalStatus;

}
