package com.good.platform.response.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProjectMappingResponse {
	
	private String id;
	private String userId;
	private List<String> projectId;
	
}
