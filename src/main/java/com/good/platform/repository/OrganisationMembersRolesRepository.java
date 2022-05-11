package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.good.platform.entity.OrganisationMembersRoles;

public interface OrganisationMembersRolesRepository extends JpaRepository<OrganisationMembersRoles, String> {

	@Query("SELECT  org.role  FROM #{#entityName} org WHERE lower(org.organisationType) = :type ")
	List<String> findByOrganisationTypes(String type);
	
	@Query("SELECT  role  FROM #{#entityName} ")
	List<String> findRole();

}


