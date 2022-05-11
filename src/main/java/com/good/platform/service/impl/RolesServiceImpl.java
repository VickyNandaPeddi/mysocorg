package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.good.platform.entity.UserRoles;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.repository.UserRoleRepository;
import com.good.platform.request.dto.MultipleRolesDataRequest;
import com.good.platform.request.dto.RolesDataRequest;
import com.good.platform.response.dto.RolesResponseDto;
import com.good.platform.service.RolesService;
import com.good.platform.utility.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RolesServiceImpl is to handle all the business functionalities of the roles
 * table
 * 
 * @author Mohamedsuhail S
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {

	private final UserRoleRepository userRoleRepository;

	/**
	 * addRoles is to add the roles to the table
	 */
	@Override
	@Transactional
	public RolesResponseDto addRoles(RolesDataRequest rolesDataRequest) {
		log.debug("Add user roles functionality starts");
		try {
			UserRoles existingUserRoles = userRoleRepository.findByRole(rolesDataRequest.getRole());
			if(!Objects.isNull(existingUserRoles)) {
				throw new Exception(Constants.ROLES_DATA_ALREADY_EXISTS);
			}
			UserRoles entity = new UserRoles();
			entity.setRole(rolesDataRequest.getRole());
			UserRoles savedUserRolesEntity = userRoleRepository.save(entity);
			RolesResponseDto rolesResponseDto = RolesResponseDto.builder().role(savedUserRolesEntity.getRole()).build();
			log.debug("Add user roles functionality ends");
			return rolesResponseDto;
		}catch(Exception exception) {
			log.error(exception.toString());
			if(Constants.ROLES_DATA_ALREADY_EXISTS.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ROLES_DATA_ALREADY_EXISTS);
			}else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ROLES_ADDITION_FAILED);
			}
		}
	}

	
	/**
	 * addMultipleRoles is to add multiple roles
	 * @param rolesDataRequest
	 * @return
	 */
	@Override
	public List<Map<String, String>> addMultipleRoles(MultipleRolesDataRequest rolesDataRequest) {
		log.debug("Add user roles functionality starts");
		try {
			List<Map<String, String>> dataList = new ArrayList<>();
			Map<String, String> dataMap = new HashMap<>();
			for(RolesDataRequest rolesDataRequestIterate : rolesDataRequest.getRolesDataRequest()) {
				UserRoles existingUserRoles = userRoleRepository.findByRole(rolesDataRequestIterate.getRole());
				if(Objects.isNull(existingUserRoles)) {
					UserRoles entity = new UserRoles();
					entity.setRole(rolesDataRequestIterate.getRole());
					UserRoles savedUserRolesEntity = userRoleRepository.save(entity);
					dataMap.put("id", savedUserRolesEntity.getId());
					dataMap.put("role", savedUserRolesEntity.getRole());
					dataList.add(new HashMap<>(dataMap));
				}
			}
			log.debug("Add user roles functionality ends");
			return dataList;
		}catch(Exception exception) {
			log.error(exception.toString());
			if(Constants.ROLES_DATA_ALREADY_EXISTS.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ROLES_DATA_ALREADY_EXISTS);
			}else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ROLES_ADDITION_FAILED);
			}
		}
	}

	/**
	 * updateRoles is to update the roles
	 * @param rolesDataRequest
	 * @return
	 */
	@Override
	public RolesResponseDto updateRoles(RolesDataRequest rolesDataRequest) {
		log.debug("Add user roles functionality starts");
		try {
			Optional<UserRoles> userRoles = userRoleRepository.findById(rolesDataRequest.getId());
			if (userRoles.isEmpty()) {
				throw new Exception(Constants.ROLES_NOT_FOUND);
			}
			userRoles.get().setRole(rolesDataRequest.getRole());
			UserRoles savedUserRolesEntity = userRoleRepository.save(userRoles.get());
			RolesResponseDto rolesResponseDto = RolesResponseDto.builder().role(savedUserRolesEntity.getRole()).build();
			log.debug("Add user roles functionality ends");
			return rolesResponseDto;
		} catch (Exception exception) {
			if (Constants.ROLES_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ROLES_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ROLES_UPDATE_FAILED);
			}
		}
	}

	/**
	 * getRoles is to fetch the roles data
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, String>> getRoles() {
		log.debug("Fetch user roles functionality starts");
		try {
			List<UserRoles> userRolesList = userRoleRepository.findAll();
			if (userRolesList.isEmpty()) {
				throw new Exception(Constants.ROLES_NOT_FOUND);
			}
			List<Map<String, String>> rolesList = new ArrayList<>();
			Map<String, String> dataMap = new HashMap<>();
			for (UserRoles userRole : userRolesList) {
				dataMap = new HashMap<>();
				dataMap.put("id", userRole.getId());
				dataMap.put("role", userRole.getRole());
				rolesList.add(new HashMap<>(dataMap));
			}
			log.debug("Fetch user roles functionality ends");
			return rolesList;
		} catch (Exception exception) {
			if (Constants.ROLES_NOT_FOUND.equals(exception.getMessage())) {
				throw new SOException(ErrorCode.NOT_FOUND, Constants.ROLES_NOT_FOUND);
			} else {
				throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ROLES_FETCH_FAILED);
			}
		}
	}

	/**
	 * deleteRoleData is to delete role data
	 * 
	 * @param id
	 */
	@Override
	public void deleteRoleData(String id) {
		log.debug("Deleting the role data starts");
		try {
			userRoleRepository.deleteById(id);
		} catch (Exception exception) {
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.ROLES_FETCH_FAILED);
		}
	}

}
