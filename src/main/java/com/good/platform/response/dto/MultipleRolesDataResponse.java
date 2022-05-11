package com.good.platform.response.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MultipleRolesDataResponse {

	private List<Map<String, String>> rolesDataRequest;
	
}
