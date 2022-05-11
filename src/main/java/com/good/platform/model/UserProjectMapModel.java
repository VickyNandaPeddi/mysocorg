package com.good.platform.model;

import lombok.Data;

@Data
public class UserProjectMapModel {
	
	public UserProjectMapModel() {
	}

	public UserProjectMapModel(String projectId, String userId) {
		this.projectId = projectId;
		this.userId = userId;
	}

	private String projectId;
	
	private String userId;

}
