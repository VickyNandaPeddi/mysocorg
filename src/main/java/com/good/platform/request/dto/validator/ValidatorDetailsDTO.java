package com.good.platform.request.dto.validator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatorDetailsDTO {

	private String firstName;
	private String lastName;
	private String middleName;
	private String phone;
	private String organisation;
	//private String validatorId;
	private String docment1Url;
	private String docment2Url;
	private String docment1Name;
	private String docment2Name;
	private Boolean detailsUpdated;


}
