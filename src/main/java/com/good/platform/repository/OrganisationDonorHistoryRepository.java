package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.good.platform.entity.OrganisationDonorHistory;
import com.good.platform.model.OrganisationDonorHistoryModel;

public interface OrganisationDonorHistoryRepository extends JpaRepository<OrganisationDonorHistory, String>{
	
	/**
	 * getByOrganisationAndBudgetId is to get the data from the OrganisationDonorHistory
	 * @param organisationId
	 * @return
	 */
	@Query("SELECT new com.good.platform.model.OrganisationDonorHistoryModel(odh.id, odh.donorName, odh.country, odh.amountFunded,"
			+ " odh.percentageContributed)"
			+ " FROM #{#entityName} odh "
			+ "WHERE odh.organisation.id = :organisationId "
			+ "AND odh.budgetHistory.id = :budgetId")
	List<OrganisationDonorHistoryModel> getByOrganisationAndBudgetId(@Param("organisationId") String organisationId,
			@Param("budgetId") String budgetId);
	@Query(value="SELECT * FROM organisation_donor_history obh WHERE obh.organisation_id = :organisationId ORDER BY obh.id asc limit 1",nativeQuery =true)
	OrganisationDonorHistory findByOrganisationId(String organisationId);
	
	void deleteByIdAndOrganisation_Id(String donorId, String organisationId);
	
}
