package com.good.platform.request.dto;

import lombok.Data;

@Data
public class RoleUpdateRequest {

	private String userId;
	private String newRoleId;
	private String oldRoleId;
}
