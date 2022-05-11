package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.Organisation12A80G;

@Repository
public interface Organisation12A80GRepository  extends JpaRepository<Organisation12A80G, String>{

	@Query(value="SELECT * FROM organisation_12a_80g_details obh WHERE obh.organisation_id = :id ORDER BY obh.id asc limit 1",nativeQuery =true)
	Organisation12A80G findByOrganisationId(String id);

}
