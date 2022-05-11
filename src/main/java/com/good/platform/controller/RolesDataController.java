package com.good.platform.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.request.dto.MultipleRolesDataRequest;
import com.good.platform.request.dto.RolesDataRequest;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.RolesResponseDto;
import com.good.platform.service.RolesService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/roles")
@Slf4j
public class RolesDataController {
	@Autowired
	RolesService rolesService;

	/**
	 * addRoles is to add roles data
	 * 
	 * @param token
	 * @param accessInfo
	 * @param request
	 * @return
	 */
	@PostMapping()
	public GoodPlatformResponseVO<RolesResponseDto> addRoles(@RequestBody RolesDataRequest request) {
		RolesResponseDto rolesResponseDto = rolesService.addRoles(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<RolesResponseDto>(), rolesResponseDto,
				Constants.ROLES_ADDED_SUCCESSFULLY, Constants.ROLES_ADDITION_FAILED);
	}
	
	/**
	 * addMultipleRoles is to add multiple roles 
	 * @param request
	 * @return
	 */
	@PostMapping("/add-multiple")
	public GoodPlatformResponseVO<List<Map<String, String>>> addMultipleRoles(@RequestBody MultipleRolesDataRequest request) {
		List<Map<String, String>> rolesResponse = rolesService.addMultipleRoles(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<RolesResponseDto>(), rolesResponse,
				Constants.ROLES_ADDED_SUCCESSFULLY, Constants.ROLES_ADDITION_FAILED);
	}

	/**
	 * updateRoles is to update the roles data
	 * 
	 * @param token
	 * @param accessInfo
	 * @param request
	 * @return
	 */
	@PutMapping()
	public GoodPlatformResponseVO<RolesResponseDto> updateRoles(@RequestBody RolesDataRequest request) {
		RolesResponseDto rolesResponseDto = rolesService.updateRoles(request);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<RolesResponseDto>(), rolesResponseDto,
				Constants.ROLES_UPDATED_SUCCESSFULLY, Constants.ROLES_UPDATE_FAILED);
	}

	/**
	 * getRoles is to fetch the roles data
	 * 
	 * @param token
	 * @param accessInfo
	 * @return
	 */
	@GetMapping()
	public GoodPlatformResponseVO<List<Map<String, String>>> getRoles() {
		List<Map<String, String>> dataList = rolesService.getRoles();
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<RolesResponseDto>(), dataList,
				Constants.ROLES_FETCH_SUCCESSFUL, Constants.ROLES_FETCH_FAILED);
	}

	/**
	 * deleteRoles is to delete the roles data
	 * 
	 * @param token
	 * @param accessInfo
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public GoodPlatformResponseVO<Boolean> deleteRoles(@PathVariable("id") String id) {
		rolesService.deleteRoleData(id);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Boolean>(), true,
				Constants.ROLES_DELETE_SUCCESSFUL, Constants.ROLES_DELETE_FAILED);
	}
}
