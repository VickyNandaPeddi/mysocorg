package com.good.platform.client.sap;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.good.platform.client.sap.model.UserEntityIdRequest;
import com.good.platform.client.sap.model.BeneficiariesDTO;
import com.good.platform.client.project.config.FiegnConfiguration;
import com.good.platform.response.dto.GoodPlatformResponseVO;



@FeignClient(name = "BENEFICIARY-SAP", url = "http://localhost:8085/beneficiary-sap", configuration = FiegnConfiguration.class)
public interface BeneficiarySapClient {
	

	
	@PutMapping("/v1/beneficiary/user-entity-id")
	public GoodPlatformResponseVO<BeneficiariesDTO> updateUserEntityId(@RequestBody UserEntityIdRequest refRequest);

}
