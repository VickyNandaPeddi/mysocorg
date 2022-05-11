package com.good.platform.service;

import java.util.List;

import com.good.platform.entity.Organisation;
import com.good.platform.entity.User;
import com.good.platform.entity.UserProjectMapping;
import com.good.platform.response.dto.OrganisationSyncDto;
import com.good.platform.response.dto.UserProjectSyncDto;

public interface SyncService {

	public List<User> syncUser(String syncedDt);

	public List<UserProjectSyncDto> syncUserProjectMapping(String syncDate);

	public List<OrganisationSyncDto> syncOrganisation(String syncDate);
	
}
