package com.good.platform.request.dto.put;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignedAggrementRequest {

	private String organisationId;
	private String signedAgreementUrl;

}
