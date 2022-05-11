package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.OrganisationFinancialDetails;

@Repository
public interface OrganisationFinancialDetailsRepository extends JpaRepository<OrganisationFinancialDetails, String> {
	OrganisationFinancialDetails findByOrganisationId(String organisationId);

	Boolean existsByOrganisationId(String organisationId);
}
