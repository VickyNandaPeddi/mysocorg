package com.good.platform.model;

import java.util.List;

import lombok.Data;

@Data
public class UserProjectOrgIdModel {
	
	private List<ProjectIdNameModel> projectModel;
	
	private String organisationId;
	
	private String organisationName;
	
	
	
	
}
