package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.good.platform.entity.OrganisationMembersDetails;

public interface OrganisationMembersDetailsRepository extends JpaRepository<OrganisationMembersDetails, String> {

	@Query(value="SELECT * FROM organisation_members_details obh WHERE obh.organisation_id = :organisationId ORDER BY obh.id asc limit 1",nativeQuery =true)
	OrganisationMembersDetails findByOrganisationId(String organisationId);

}
