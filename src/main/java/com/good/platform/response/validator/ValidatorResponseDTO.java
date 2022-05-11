package com.good.platform.response.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorResponseDTO {
	
	private String id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String phone;
	private String organisation;
	private String document1Url;
	private String document2Url;
	private String document1Name;
	private String document2Name;
}
