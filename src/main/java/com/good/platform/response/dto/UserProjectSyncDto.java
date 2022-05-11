package com.good.platform.response.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProjectSyncDto extends BaseResponse{
	
	private String projectId;
	private String userId;
	private LocalDateTime syncedAt;

}
