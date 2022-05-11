package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.good.platform.entity.OrganisationAddress;
import com.good.platform.entity.OrganisationMembers;

public interface OrganisationAddressRepository extends JpaRepository<OrganisationAddress, String>{

	void deleteByIdAndOrganisation_Id(String addressId, String organisationId);
	
	@Query(value="SELECT * FROM organisation_address oa WHERE oa.organisation_id=:organisationId ORDER BY oa.id LIMIT 1",nativeQuery=true)
	OrganisationAddress findByOrganisationId(String organisationId);

}
