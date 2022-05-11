package com.good.platform.request.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgBasicDetailsRequest {
	
	/**
	 * organisationId is to get the organisation ID
	 */
	private String organisationId;
	
	/**
	 * name represents the organisation name in the overview page
	 */
	private String name;
	
	/**
	 * type represents the organisation type dropdown ("Registered as") in overview page 
	 */
	private String type;
	
	/**
	 * yearFounded represents the year of foundation of the organisation
	 * Represents the "Year Founded" field in the Organisation overview page 
	 */
	private String yearFounded;
	
	/**
	 * noOfEmployees represents the no. of employees dropdown field in the organisation
	 * overview page 
	 */
	private String noOfEmployees;
	
	/**
	 * emailId represents the organisation Email in the organisation overview page
	 */
	private String emailId;
	
	/**
	 * website represents the website URL field in the organisation overview page
	 */
	private String website;
	
	/**
	 * headquarterCountry represents the headquarter country in the organisation Locations page
	 */
	private Integer headquarterCountry;
	
	/**
	 * about represents the description about the organisation and is representation of the
	 * field "About the organisation" in the Organisation Locations page 
	 */
	private String about;
	
	/**
	 * GSTNumber
	 */
	private String gstNumber;
	
	/**
	 * approvalStatus
	 */
	private String approvalStatus;
	
	/**
	 * creatorMemberId is to mention the Organisation Member ID
	 */
	private String creatorMemberId;
	
	/**
	 * fullName represents the "full Name" field in the organisation overview page 
	 */
	private String fullName;
	
	/**
	 * memberType is to represent the member type in the organisation overview page
	 */
	private String memberType;
	
	/**
	 * otherMemberType is to represent the other member type in the organisation overview page
	 */
	private String otherMemberType;
	
	/**
	 * memberEmailId is to represent the email ID of the user details section in the overview page
	 */
	private String memberEmailId;
	
	/**
	 * mobile represents the "Mobile No." field in the organisation overview page 
	 */
	private String mobile;
	
	/**
	 * idProof represents the "ID Proof" field in the organisation overview page 
	 */
	private String idProof;
	
	/**
	 * otherIdProof is to get the other ID proofs other than mentioned proofs in the screen
	 */
	private String otherIdProof;
	
	/**
	 * idProofNumber represents the input if the user has selected the other proof option
	 */
	private String idProofNumber;
	
	private String registerdAs;
	
	private String amlCheck;
	
	private String fcraNumber;
	private LocalDate fcraIssueDate;
	private LocalDate fcraExpiryDate;
	
	private String org12a80gCertificateNumber;
	private LocalDate org12a80gIssueDate;
	private LocalDate org12a80gValidityDate;
	
}
