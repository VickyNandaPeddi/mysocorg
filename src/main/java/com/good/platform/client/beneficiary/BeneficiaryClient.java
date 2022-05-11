package com.good.platform.client.beneficiary;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.good.platform.client.project.config.FiegnConfiguration;
import com.good.platform.client.sap.model.BeneficiariesDTO;
import com.good.platform.client.sap.model.BeneficiaryRegisterResponseDto;
import com.good.platform.client.sap.model.UserEntityIdRequest;
import com.good.platform.response.dto.GoodPlatformResponseVO;



@FeignClient(name = "BENEFICIARY", url = "http://localhost:8084/beneficiary", configuration = FiegnConfiguration.class)
public interface BeneficiaryClient {
	
	@GetMapping("/v1/beneficiary/count")
	public GoodPlatformResponseVO<Integer> getCountByFieldAgentOwned(@RequestParam(value = "fieldAgentId", required = true) String fieldAgentId,
			@RequestParam(value = "date", required = true) String date);
	
	@PutMapping("/v1/beneficiary/user-entity-id")
	public GoodPlatformResponseVO<BeneficiariesDTO> updateUserEntityId(@RequestBody UserEntityIdRequest refRequest);
	
	@GetMapping("/v1/beneficiary/imported-list")
	public GoodPlatformResponseVO<List<BeneficiaryRegisterResponseDto>> getBeneficiaryListForRegister();
	
	@GetMapping("/v1/beneficiary/distinct-project-list")
	public GoodPlatformResponseVO<List<String>> getDistinctProjectList();
}
