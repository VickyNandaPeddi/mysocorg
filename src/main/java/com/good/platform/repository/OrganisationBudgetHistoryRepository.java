package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.OrganisationBudgetHistory;
import com.good.platform.model.OrganisationBudgetHistoryModel;

@Repository
public interface OrganisationBudgetHistoryRepository extends JpaRepository<OrganisationBudgetHistory, String>{
	
	/**
	 * getByOrganisation is to get the data from the OrganisationBudgetHistory
	 * (NOTE: Inner join data between Financial year and the organisation budget history and 
	 * setting the data in the constructor of the model and returning)
	 * @param organisationId
	 * @return
	 */
	@Query("SELECT new com.good.platform.model.OrganisationBudgetHistoryModel(obh.id, obh.financialYear, obh.overallBudget)"
			+ " FROM #{#entityName} obh "
			+ "WHERE obh.organisation.id = :organisationId")
	List<OrganisationBudgetHistoryModel> getByOrganisation(@Param("organisationId") String organisationId);
	
	@Query("SELECT new com.good.platform.model.OrganisationBudgetHistoryModel(obh.id, obh.financialYear, obh.overallBudget, "
			+ " obh.noOfSingleYearPrograms, obh.noOfMultiYearPrograms, obh.noOfProgramsCompletedAsPerTimelines,"
			+ " obh.noOfProgramsUtilizationCertificatesIssued, obh.noOfProgramsAudited)"
			+ " FROM #{#entityName} obh "
			+ "WHERE obh.organisation.id = :organisationId")
	List<OrganisationBudgetHistoryModel> getBudgetDonorHistoryByOrganisation(@Param("organisationId") String organisationId);

	@Query(value="SELECT * FROM organisation_budget_history obh WHERE obh.organisation_id = :organisationId ORDER BY obh.id asc limit 1",nativeQuery =true)
	OrganisationBudgetHistory findByOrganisationId(@Param("organisationId") String organisationId);
	
}
