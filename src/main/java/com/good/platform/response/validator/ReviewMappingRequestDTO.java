package com.good.platform.response.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewMappingRequestDTO {
	private String validatorId;
	private String organisationId;
}
