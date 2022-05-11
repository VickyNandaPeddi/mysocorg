package com.good.platform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.entity.User;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.OrganisationSyncDto;
import com.good.platform.response.dto.UserProjectSyncDto;
import com.good.platform.service.SyncService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/sync")
@RequiredArgsConstructor
@Slf4j
public class SyncController {

	private final SyncService syncService;

	@GetMapping("/user")
	public GoodPlatformResponseVO<List<User>> syncUser(@RequestParam String syncDate) {
		List<User> response = syncService.syncUser(syncDate);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO(), response,
				Constants.USER_DETAILS_FETCH_SUCCESS, Constants.USER_DETAILS_FETCH_FAIL);
	}

	@GetMapping("/user-project-mapping")
	public GoodPlatformResponseVO<List<UserProjectSyncDto>> syncUserProjectMapping(@RequestParam String syncDate) {
		List<UserProjectSyncDto> response = syncService.syncUserProjectMapping(syncDate);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO(), response,
				Constants.USER_PROJECT_MAPPING_FETCH_SUCCESS, Constants.USER_PROJECT_MAPPING_FETCH_FAIL);
	}

	@GetMapping("/organisation")
	public GoodPlatformResponseVO<List<OrganisationSyncDto>> syncOrganisation(@RequestParam String syncDate) {
		List<OrganisationSyncDto> response = syncService.syncOrganisation(syncDate);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO(), response,
				Constants.ORG_DETAILS_FETCH_SUCCESS, Constants.ORG_DETAILS_FETCH_FAIL);
	}
}
