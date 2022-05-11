package com.good.platform.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class OrganisationMembersCountModel {
	
	private String countId;
	private String type;
	private Integer count;

}
