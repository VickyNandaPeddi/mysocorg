package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.OrganisationMembers;

/**
 * OrganisationRepository represents the repository class for the table Organisation
 * @author Mohamedsuhail S
 *
 */
@Repository
public interface OrganisationMembersRepository extends JpaRepository<OrganisationMembers, String>{

	void deleteByIdAndOrganisation_Id(String memberId, String organisationId);
	@Query(value="SELECT * FROM organisation_members o WHERE o.organisation_id=:organisationId AND o.type=:type ORDER BY o.id LIMIT 1",nativeQuery=true)
	OrganisationMembers findByOrganisationIdAndType(@Param("organisationId") String organisationId,@Param("type") String type);

	@Query(value="SELECT * FROM organisation_members o WHERE o.organisation_id=:organisationId ORDER BY o.id LIMIT 1",nativeQuery=true)
	OrganisationMembers findByOrganisationId(@Param("organisationId") String organisationId);
	
	@Query(value="SELECT * FROM organisation_members o WHERE o.organisation_id=:organisationId ORDER BY o.sort_order",nativeQuery=true)
	List<OrganisationMembers> findAllByOrganisationId(@Param("organisationId") String organisationId);

}
