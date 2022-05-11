package com.good.platform.entity;

import javax.persistence.Entity;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Entity(name = "user_roles")
public class UserRoles extends DocumentId{

	private String role;

	public UserRoles(String role) {
		this.role = role;
	}

	public UserRoles() {
	}

}
