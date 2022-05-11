package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.UserOrganisationMapping;

@Repository
public interface UserOrganisationMappingRespository extends JpaRepository<UserOrganisationMapping, String> {


	@Query("SELECT org.organisation.id FROM #{#entityName} org WHERE org.user.id = :userId")
	List<String> getOrganisationsIdOfUser(@Param("userId") String userId);

	boolean existsByOrganisation_IdAndUser_Id(String organisationId, String userId);

}
