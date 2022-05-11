package com.good.platform.request.dto;

import java.util.List;

import lombok.Data;

@Data
public class MultipleRolesDataRequest {

	private List<RolesDataRequest> rolesDataRequest;
	
}
