package com.good.platform.client.project;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.good.platform.client.project.config.FiegnConfiguration;
import com.good.platform.model.ProjectIdNameModel;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.ProjectResponseDto;

@FeignClient(name = "PROJECT-MANAGEMENT", url = "http://localhost:8083/project-management", configuration = FiegnConfiguration.class)
public interface ProjectClientApi {

	
	@GetMapping("/v1/project/count")
	public  GoodPlatformResponseVO<Long>  getProjectCount();
	
	@GetMapping("/v1/project/projectlist")
	public GoodPlatformResponseVO<List<ProjectResponseDto>> getProjectsByIds(@RequestBody List<String> projectIds);
	
	@GetMapping("/v1/project/project-id-name")
	public GoodPlatformResponseVO<List<ProjectIdNameModel>> getProjectsIdsNames(@RequestBody List<String> projectIds);
	
	@GetMapping("/v1/project/{projectId}")
	public GoodPlatformResponseVO<ProjectResponseDto> getProjectDetails(@PathVariable String projectId) ;

}
