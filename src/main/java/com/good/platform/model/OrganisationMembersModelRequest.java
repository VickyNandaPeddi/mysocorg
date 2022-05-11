package com.good.platform.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class OrganisationMembersModelRequest {

	private String memberId;
	/**
	 * 
	 */
	private String name;

	private String type;

	private String otherType;

	private String email;

	private String phone;

	private String idProof;

	private String otherIdProof;

	private String idProofNumber;

	private Boolean authorisedSignatory;

	private Boolean permanentEmployee;


	private String amlCheck;

	private Integer sortOrder;

}
