package com.good.platform.model;

import com.good.platform.utility.FileUtils;

import groovy.transform.ToString;
import lombok.Getter;
@ToString
@Getter
public class OrganisationMembersModel {
	
	
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
	
	private int sortOrder;
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}

	public void setOtherIdProof(String otherIdProof) {
		this.otherIdProof = otherIdProof;
	}

	public void setIdProofNumber(String idProofNumber) {
		
		this.idProofNumber = FileUtils.decrypt(idProofNumber);
	}

	public void setAuthorisedSignatory(Boolean authorisedSignatory) {
		this.authorisedSignatory = authorisedSignatory;
	}

	public void setAmlCheck(String amlCheck) {
		this.amlCheck = amlCheck;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setPermanentEmployee(Boolean permanentEmployee) {
		this.permanentEmployee = permanentEmployee;
	}
	
	

}
