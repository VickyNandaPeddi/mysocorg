package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.BeneficiaryLocations;
import com.good.platform.response.dto.BeneficiaryLocationsResponse;

@Repository
public interface BeneficiaryLocationsRepository extends JpaRepository<BeneficiaryLocations, String> {

	@Query("SELECT new com.good.platform.response.dto.BeneficiaryLocationsResponse(bl.id,bl.location,bl.locationTitle)"
			+ " FROM #{#entityName} bl " + "WHERE bl.organisation.id = :organisationId "
			+ "and bl.organisationBeneficiaryHistory.id = :beneficiaryHistoryId")
	List<BeneficiaryLocationsResponse> getByBeneficiaryHistoryAndOrganisation(
			@Param("beneficiaryHistoryId") String beneficiaryHistoryId, @Param("organisationId") String organisationId);
}
