package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.good.platform.entity.OrganisationMissionStatements;

public interface OrganisationMissionStatementsRepository extends JpaRepository<OrganisationMissionStatements, String>{

	void deleteByIdAndOrganisation_Id(String missionStatementId, String organisationId);
	@Query(value="SELECT * FROM organisation_mission_statements orgm WHERE orgm.organisation_id = :organisationId ORDER BY orgm.id asc limit 1",nativeQuery =true)
	OrganisationMissionStatements findByOrganisationId(@Param("organisationId") String organisationId);

}
