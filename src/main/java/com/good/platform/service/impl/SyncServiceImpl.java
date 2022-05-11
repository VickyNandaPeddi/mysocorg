package com.good.platform.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;
import com.good.platform.entity.UserProjectMapping;
import com.good.platform.mapper.OrganisationMapper;
import com.good.platform.mapper.UserProjectMappingMapper;
import com.good.platform.repository.OrganisationRepository;
import com.good.platform.repository.UserProjectMappingRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.response.dto.OrganisationSyncDto;
import com.good.platform.response.dto.UserProjectSyncDto;
import com.good.platform.service.SyncService;
import com.good.platform.utility.DateTimeUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SyncServiceImpl implements SyncService {

	private final UserRepository userRepo;
	private final UserProjectMappingRepository userProjectMappingRepo;
	private final OrganisationRepository organisationRepo;
	private final UserProjectMappingMapper userProjectMappingMapper;
	private final OrganisationMapper organisationMapper;
	private final DateTimeUtils dateTimeUtils;

	@Override
	public List<User> syncUser(String syncDate) {
		List<User> userList = userRepo.findBySyncedAtAfter(dateTimeUtils.convertStringToLocalDate(syncDate));
		if (userList.isEmpty()) {
			return new ArrayList<>();
		}
		return userList;
	}

	@Override
	public List<UserProjectSyncDto> syncUserProjectMapping(String syncDate) {
		List<UserProjectSyncDto> responses = new ArrayList<>();
		List<UserProjectMapping> userProjectMappingList = userProjectMappingRepo
				.findBySyncedAtAfter(dateTimeUtils.convertStringToLocalDate(syncDate));
		if (userProjectMappingList.isEmpty()) {
			return new ArrayList<>();
		}

		responses.addAll(userProjectMappingMapper.toDto(userProjectMappingList));

		return responses;
	}

	@Override
	public List<OrganisationSyncDto> syncOrganisation(String syncDate) {
		List<OrganisationSyncDto> responses = new ArrayList<>();
		List<Organisation> organisationList = organisationRepo
				.findBySyncedAtAfter(dateTimeUtils.convertStringToLocalDate(syncDate));
		if (organisationList.isEmpty()) {
			return null;
		}
		responses.addAll(organisationMapper.toDto(organisationList));
		return responses;
	}
}
