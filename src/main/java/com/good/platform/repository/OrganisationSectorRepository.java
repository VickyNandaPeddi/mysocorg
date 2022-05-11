package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.good.platform.entity.OrganisationSector;

public interface OrganisationSectorRepository extends JpaRepository<OrganisationSector, String>{

	@Query(value="select * from organisation_sector where organisation_id=:organisationId and type=:type order by id asc limit 1",nativeQuery=true)
	OrganisationSector findBysecodarySectors(
			@Param("organisationId") String organisationId,@Param("type") String type);

     List<OrganisationSector> findByOrganisationId(String organisationId);
}
