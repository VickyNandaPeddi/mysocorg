package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "organisation_members_roles")
public class OrganisationMembersRoles extends BaseId {
	
	
	
	@Column(name = "role")	
	private String role;
	
	@Column(name = "organisation_type")	
	private String organisationType;
	
	@Column(name = "sort_order")	
	private int sortOrder;

}

