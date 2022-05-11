package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.OrganisationTypes;

@Repository
public interface OrganisationTypeRepository extends JpaRepository<OrganisationTypes, String> {

	@Query("SELECT  org.type  FROM #{#entityName} org WHERE lower(org.registeredAs) = :registerAs")
	List<String> findByRegisterAs(String registerAs);

	@Query("SELECT type  FROM #{#entityName}")
	List<String> findOrganisationTypes();

	@Query("SELECT distinct registeredAs  FROM #{#entityName} ")
	List<String> findRegisterAs();

	@Query("SELECT distinct org.registeredAs  FROM #{#entityName} org WHERE lower(org.type) = :type")
	List<String> findByOrganisationTypes(String type);
}
