package com.good.platform.service;

import java.util.List;

import com.good.platform.request.RegisterDTO;
import com.good.platform.response.dto.UserResponse;

public interface BeneficiaryService {

	public UserResponse register(RegisterDTO request);

	/**
	 * To onboard the beneficiary via cron-job
	 * 
	 * @author Arya C Achari
	 * @since 25-Feb-2022
	 * 
	 * @return
	 */
	List<UserResponse> sapBeneficiaryOnBoarding();

}
