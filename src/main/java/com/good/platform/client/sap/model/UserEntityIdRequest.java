package com.good.platform.client.sap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class UserEntityIdRequest {
	private String id;
	private String email;
	private String UserEntityId;
	
}
