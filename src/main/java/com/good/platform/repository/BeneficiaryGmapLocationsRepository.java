package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.BeneficiaryGmapLocations;
import com.good.platform.response.dto.BeneficiaryGmapLocationsResponse;

@Repository
public interface BeneficiaryGmapLocationsRepository extends JpaRepository<BeneficiaryGmapLocations, String> {

	@Query("SELECT new com.good.platform.response.dto.BeneficiaryGmapLocationsResponse(bgl.id,bgl.latitude,bgl.longitude)"
			+ " FROM #{#entityName} bgl " + "WHERE bgl.organisation.id = :organisationId "
			+ "and bgl.organisationBeneficiaryHistory.id = :beneficiaryHistoryId "
			+ "and bgl.beneficiaryLocations.id = :locationId ")
	List<BeneficiaryGmapLocationsResponse> getByLocationAndBeneficiaryHistoryAndOrganisation(
			@Param("locationId") String locationId, @Param("beneficiaryHistoryId") String beneficiaryHistoryId,
			@Param("organisationId") String organisationId);

}
