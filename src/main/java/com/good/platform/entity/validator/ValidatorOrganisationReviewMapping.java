package com.good.platform.entity.validator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.good.platform.entity.DocumentId;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "validator_organisation_review_mapping")
public class ValidatorOrganisationReviewMapping extends DocumentId {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;
}
