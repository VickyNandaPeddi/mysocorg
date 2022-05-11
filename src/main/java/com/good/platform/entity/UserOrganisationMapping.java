package com.good.platform.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "user_organisation_mapping")
public class UserOrganisationMapping extends DocumentId{

	public UserOrganisationMapping() {
	}
	
	public UserOrganisationMapping(String partnershipId) {
		this.setId(partnershipId);
	}
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partner_organisation_id", nullable = false)
	private Organisation organisation;
	
}
