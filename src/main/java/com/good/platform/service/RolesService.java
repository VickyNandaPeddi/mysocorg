package com.good.platform.service;

import java.util.List;
import java.util.Map;

import com.good.platform.request.dto.MultipleRolesDataRequest;
import com.good.platform.request.dto.RolesDataRequest;
import com.good.platform.response.dto.RolesResponseDto;

public interface RolesService {

	RolesResponseDto addRoles(RolesDataRequest rolesDataRequest);

	/**
	 * updateRoles is to update the roles
	 * @param rolesDataRequest
	 * @return
	 */
	RolesResponseDto updateRoles(RolesDataRequest rolesDataRequest);

	/**
	 * getRoles is to fetch the roles data
	 * @return
	 */
	List<Map<String, String>> getRoles();

	/**
	 * deleteRoleData is to delete role data
	 * @param id
	 */
	void deleteRoleData(String id);

	/**
	 * addMultipleRoles is to add multiple roles
	 * @param rolesDataRequest
	 * @return
	 */
	List<Map<String, String>> addMultipleRoles(MultipleRolesDataRequest rolesDataRequest);

}
