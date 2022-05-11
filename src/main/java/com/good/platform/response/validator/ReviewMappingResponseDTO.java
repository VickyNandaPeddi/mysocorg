package com.good.platform.response.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewMappingResponseDTO {
	private String id;
	private String validatorId;
	private String organisationId;
}
