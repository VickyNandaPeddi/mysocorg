package com.good.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity(name = "organisation_types")
public class OrganisationTypes extends BaseId {
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "registered_as")
	private String registeredAs;

}
