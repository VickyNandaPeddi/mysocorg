package com.good.platform.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.good.platform.entity.OrganisationBeneficiaryHistory;

public interface OrganisationBeneficiaryHistoryRepository
		extends JpaRepository<OrganisationBeneficiaryHistory, String> {
	
	OrganisationBeneficiaryHistory findByOrganisationId(String organisationId);

    Boolean existsByOrganisationId(String organisationId);
    
}