package com.good.platform.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "user_project_mapping")
public class UserProjectMapping extends DocumentId{

	public UserProjectMapping() {
	}

	public UserProjectMapping(String userProjectId) {
		this.setId(userProjectId);
	}
	
	@Column(name = "project_id")
	private String projectId;
	

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
}
