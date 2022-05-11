package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.good.platform.entity.OrganisationMembersCount;

public interface OrganisationMembersCountRepository extends JpaRepository<OrganisationMembersCount, String> {

	@Query(value="SELECT * FROM organisation_members_count obh WHERE obh.organisation_id = :organisationId ORDER BY obh.id asc ",nativeQuery =true)
	List<OrganisationMembersCount> findByOrganisationId(String organisationId);

}
