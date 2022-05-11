package com.good.platform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.request.RegisterDTO;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.UserResponse;
import com.good.platform.service.BeneficiaryService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/sap")
@RequiredArgsConstructor
@Slf4j
public class SAPController {

	private final BeneficiaryService beneficiaryService;

	@PostMapping("/beneficiary/registration")
	public GoodPlatformResponseVO<UserResponse> register(@RequestBody RegisterDTO request) {
		UserResponse response = beneficiaryService.register(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<UserResponse>(), response,
				Constants.USER_ADD_SUCCESS, Constants.USER_ADD_FAIL);

	}
	
	@PostMapping("/beneficiary-onboarding")
	public GoodPlatformResponseVO<List<UserResponse>> sapBeneficiaryOnBoarding() {
		List<UserResponse> response = beneficiaryService.sapBeneficiaryOnBoarding();
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<List<UserResponse>>(), response,
				Constants.BENEFICIARY_ON_BOARDING_SUCCESS, Constants.BENEFICIARY_ON_BOARDING_FAILED);

	}

}
