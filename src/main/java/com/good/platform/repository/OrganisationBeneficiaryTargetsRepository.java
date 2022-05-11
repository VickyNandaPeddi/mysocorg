package com.good.platform.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.OrganisationBeneficiaryTargets;
import com.good.platform.response.dto.OrganisationBeneficiaryTargetsResponse;

@Repository
public interface OrganisationBeneficiaryTargetsRepository
		extends JpaRepository<OrganisationBeneficiaryTargets, String> {


	
	@Query("SELECT obh.targetCategory"
			+ " FROM #{#entityName} obh " + "WHERE obh.organisation.id = :organisationId "
			+ "and obh.organisationBeneficiaryHistory.id = :beneficiaryHistoryId")
	List<String> getByBeneficiaryHistory(
			@Param("beneficiaryHistoryId") String beneficiaryHistoryId, @Param("organisationId") String organisationId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM #{#entityName} WHERE organisation_id = :organisationId")
	void deleteTargetCategories(@Param("organisationId") String organisationId);
}