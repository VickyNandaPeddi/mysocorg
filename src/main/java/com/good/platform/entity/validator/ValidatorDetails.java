package com.good.platform.entity.validator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.good.platform.entity.DocumentId;
import com.good.platform.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "validator_details")
public class ValidatorDetails extends DocumentId {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@Column(name = "organisation")
	private String organisation;
	@Column(name = "document1_url")
	private String document1Url;
	@Column(name = "document2_url")
	private String document2Url;
	@Column(name = "document1_name")
	private String document1Name;
	@Column(name = "document2_name")
	private String document2Name;


}
