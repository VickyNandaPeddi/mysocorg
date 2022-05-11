package com.good.platform.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationMembersRolesRequest {
	
	private String role;
	private String organisationType;
	private int sortOrder;

}
