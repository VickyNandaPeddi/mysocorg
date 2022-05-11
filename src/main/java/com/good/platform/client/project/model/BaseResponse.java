package com.good.platform.client.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
	private String id;
	private boolean active;
	private boolean deleted;
	private Long createdAt;
	private Long modifiedAt;
	private String createdBy;
	private String lastModifiedBy;
}
