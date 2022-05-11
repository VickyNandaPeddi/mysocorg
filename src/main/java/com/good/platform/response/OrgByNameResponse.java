package com.good.platform.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @implNote To get the oranisation details by name
 * 
 * @author Arya C Achari
 * @since 31-Dec-2021
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class OrgByNameResponse {
	
	String id;
	String fullName;
	String name;
	String createdBy;
	
	public OrgByNameResponse() {}
}
