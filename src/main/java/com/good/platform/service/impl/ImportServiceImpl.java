package com.good.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.good.platform.entity.ImportBulkUsers;
import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;
import com.good.platform.entity.UserOrganisationMapping;
import com.good.platform.entity.UserProjectMapping;
import com.good.platform.repository.ImportBulkUsersRepository;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.UserOrganisationMappingRespository;
import com.good.platform.repository.UserProjectMappingRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.request.dto.UserRequest;
import com.good.platform.service.ImportService;
import com.good.platform.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImportServiceImpl implements ImportService {

	@Autowired
	ImportBulkUsersRepository importBulkRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserOrganisationMappingRespository userOrgMapRepo;
	@Autowired
	UserProjectMappingRepository userProjectRepo;
	@Autowired
	SecurityUtil securityUtil;
	@Autowired
	UserService userService;
	@Autowired
	OrganisationRepository orgRepo;

	@Override
	public Object addBulkUsers(Integer limit, Integer offset) {

		List<ImportBulkUsers> bulkUsers = importBulkRepo.getBulkUser(limit, offset);
		String projectId = "8a8b84c67dc3114e017e2a3591a70032";
		String organisationId = "8a8b803a7b98333f017b9d3706d5001d";
		Organisation org = orgRepo.getOne(organisationId);
		String roleId = "8a8b803a7b98333f017b9b21ab960002";

		int count = 0;
		System.out.println("----- Bulk user import start -----");
		for (ImportBulkUsers item : bulkUsers) {

			// SAVE USERS TABLE
			User user = new User();
			user.setFirstName(item.getFirstName());
			user.setLastName(item.getLastName());
			user.setEmailId(item.getEmail());
			user.setFirstName(item.getFirstName());
			user.setFirstName(item.getFirstName());
			user.setCreatedBy(securityUtil.getCurrentUser());

			User savedEntity = userRepo.save(user);

			// SAVE USER PROJECT MAPPING
			UserProjectMapping upm = new UserProjectMapping();
			upm.setProjectId(projectId);
			upm.setUser(savedEntity);
			upm.setCreatedBy(securityUtil.getCurrentUser());
			userProjectRepo.save(upm);

			// SAVE USER ORGANISATION MAPPING
			UserOrganisationMapping uom = new UserOrganisationMapping();
			uom.setCreatedBy(securityUtil.getCurrentUser());
			uom.setUser(savedEntity);
			uom.setOrganisation(org);
			userOrgMapRepo.save(uom);

			UserRequest request = new UserRequest();
			request.setEmailId(item.getEmail());
			request.setFirstName(item.getFirstName());
			request.setLastName(item.getLastName());
			request.setUserName(item.getEmail());
			request.setPassword("agent@1234");
			request.setRoleId(roleId);

			// create credentials
			userService.setPassword(savedEntity.getId(), request);

			if (count % 10 == 0) {
				System.out.println("-- Imported " + count + " users --");
			}
			count++;

		}
		System.out.println("----- Completed bulk users import -----");
		return null;
	}

}
