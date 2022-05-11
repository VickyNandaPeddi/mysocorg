package com.good.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.good.platform.entity.OrganisationFCRADetails;

public interface OrganisationFCRADetailsRepository extends JpaRepository<OrganisationFCRADetails, String> {

	@Query(value="SELECT * FROM organisation_fcra_details obh WHERE obh.organisation_id = :id ORDER BY obh.id asc limit 1",nativeQuery =true)
	OrganisationFCRADetails findByOrganisationId(String id);

}
