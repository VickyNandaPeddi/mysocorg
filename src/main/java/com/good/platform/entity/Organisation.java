package com.good.platform.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "organisation")
public class Organisation extends DocumentId {

	/**
	 * name represents the organisation name in the overview page
	 */
	@Column(name = "name")
	private String name;

	/**
	 * type represents the organisation type dropdown 
	 */
	@Column(name = "type")
	private String type;

	/**
	 * yearFounded represents the year of foundation of the organisation
	 * Represents the "Year Founded" field in the Organisation overview page 
	 */
	@Column(name = "year_founded")
	private String yearFounded;

	/**
	 * noOfEmployees represents the no. of employees dropdown field in the organisation
	 * overview page 
	 */
	@Column(name = "no_of_employees")
	private String noOfEmployees;

	/**
	 * emailId represents the organisation Email in the organisation overview page
	 */
	@Column(name = "email_id")
	private String emailId;

	/**
	 * website represents the website URL field in the organisation overview page
	 */
	@Column(name = "website")
	private String website;

	/**
	 * about represents the description about the organisation and is representation of the
	 * field "About the organisation" in the Organisation Locations page 
	 */
	@Column(name = "about", columnDefinition = "TEXT")
	private String about;

	/**
	 * headquarterCountry represents the headquarter country in the organisation Locations page
	 */
	@Column(name = "headquarter_country")
	private String headquarterCountry;
	
	/**
	 * creatorMemberId is the column which represents the user who created the initial organisation data
	 */
	@Column(name = "creator_member_id")
	private String creatorMemberId;
	
	/**
	 * fullName represents the "full Name" field in the organisation overview page 
	 */
	@Column(name = "full_name")
	private String fullName;
	
	/**
	 * mobile represents the "Mobile No." field in the organisation overview page 
	 */
	@Column(name = "mobile")
	private String mobile;
	
	/**
	 * idProof represents the "ID Proof" field in the organisation overview page 
	 */
	@Column(name = "id_proof")
	private String idProof;

	@Column(name = "gst_number")
	private String gstNumber;

	@Column(name = "approval_status")
	private String approvalStatus;
	
	@Column(name = "approval_status_comment", columnDefinition = "TEXT")
	private String approvalStatusComment;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationAddress> address = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationMembers> members = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationSector> sectors = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationMissionStatements> missionStatements = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationBudgetHistory> budgetHistories = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationDonorHistory> donorHistories = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationBeneficiaryHistory> beneficiaryHistories = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
	private List<OrganisationFinancialDetails> financialDetails = new ArrayList<>();
	
	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation") private
	 * List<OrganisationLegalDetails> legalDetails = new ArrayList<>();
	 */
	public Organisation() {
		
	}
	
	public Organisation(String id) {
		this.setId(id);
	}
	/**
	 * 
	 *  represents the organisation 'Registred As' dropdown 
	 */
	 
	@Column(name = "registerd_as")
	private String registerdAs;
	
	@Column(name = "signed_agreement_url")
	private String signedAgreementUrl;
	
	@Column(name = "vision_statement", columnDefinition = "TEXT")
	private String visionStatement;

	@Column(name = "value_statement", columnDefinition = "TEXT")
	private String valueStatement;

}
