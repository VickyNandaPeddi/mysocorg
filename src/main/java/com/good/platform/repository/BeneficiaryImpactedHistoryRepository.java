package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.BeneficiaryImpactedHistory;
import com.good.platform.response.dto.BeneficiaryImpactedHistoryResponse;

@Repository
public interface BeneficiaryImpactedHistoryRepository extends JpaRepository<BeneficiaryImpactedHistory, String> {

	@Query("SELECT new com.good.platform.response.dto.BeneficiaryImpactedHistoryResponse(obh.id,obh.totalBeneficiaryImpacted,obh.financialYear)"
			+ " FROM #{#entityName} obh " + "WHERE obh.organisation.id = :organisationId "
			+ "and obh.beneficiaryHistoryId.id = :beneficiaryHistoryId")
	List<BeneficiaryImpactedHistoryResponse> getByBeneficiaryHistory(
			@Param("beneficiaryHistoryId") String beneficiaryHistoryId, @Param("organisationId") String organisationId);

}
