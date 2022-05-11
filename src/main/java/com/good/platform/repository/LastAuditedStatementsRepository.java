package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.LastAuditedStatements;
import com.good.platform.entity.OrganisationFinancialDetails;
import com.good.platform.model.LastAuditStatementsModel;
import com.good.platform.response.dto.LastAuditedStatementsResponse;

@Repository
public interface LastAuditedStatementsRepository extends JpaRepository<LastAuditedStatements, String> {
	@Query("SELECT new com.good.platform.response.dto.LastAuditedStatementsResponse(obh.id,"
			+ "obh.auditedYear,obh.auditor, (CASE "
			+ "WHEN org.approvalStatus = 'APPROVED' THEN obh.auditedStatementWorkdocsUrl "
			+ "ELSE obh.auditedStatementUrl END), obh.auditedStatementFileName)"
			+ " FROM #{#entityName} obh JOIN com.good.platform.entity.Organisation org on org.id = obh.organisation.id"
			+ " WHERE obh.organisation.id = :organisationId "
			+ "and obh.organisationFinancialDetails.id = :beneficiaryHistoryId" )
	List<LastAuditedStatementsResponse>  getByBeneficiaryHistory(@Param("beneficiaryHistoryId")String beneficiaryHistoryId,@Param("organisationId") String organisationId);
	
//	@Query("SELECT new com.good.platform.response.dto.LastAuditedStatementsResponse(obh.id,"
//			+ "obh.auditedYear,obh.auditor,obh.auditedStatementUrl, obh.auditedStatementFileName)"
//			+ " FROM #{#entityName} obh "
//			+ "WHERE obh.organisation.id = :organisationId "
//			+ "and obh.organisationFinancialDetails.id = :beneficiaryHistoryId" )
//	List<LastAuditedStatementsResponse>  getByBeneficiaryHistory(@Param("beneficiaryHistoryId")String beneficiaryHistoryId,@Param("organisationId") String organisationId);
	
	void deleteByIdAndOrganisation_Id(String lastAuditStatementId, String organisationId);

	Boolean existsByOrganisationFinancialDetails(OrganisationFinancialDetails financialDetails);
	
	List<LastAuditedStatements> findByOrganisationId(String organisationId);

	@Query("SELECT new com.good.platform.model.LastAuditStatementsModel(obh.id,"
			+ " obh.auditedStatementUrl) "
			+ " FROM #{#entityName} obh"
			+ " WHERE obh.organisation.id = :organisationId AND obh.auditedStatementUrl IS NOT NULL AND obh.auditedStatementUrl !=''")
	List<LastAuditStatementsModel>  getLastAuditStatementsDetailsByOrganisationId(@Param("organisationId") String organisationId);
	
}
