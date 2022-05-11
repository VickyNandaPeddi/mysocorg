package com.good.platform.client.sap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiariesDTO {

	private String id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String idCardNumber;

}