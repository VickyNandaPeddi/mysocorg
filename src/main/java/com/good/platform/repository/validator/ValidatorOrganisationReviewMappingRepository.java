package com.good.platform.repository.validator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.validator.OrganisationReview;
import com.good.platform.entity.validator.ValidatorDetails;
import com.good.platform.entity.validator.ValidatorOrganisationReviewMapping;

@Repository
public interface ValidatorOrganisationReviewMappingRepository
		extends JpaRepository<ValidatorOrganisationReviewMapping, String> {

	@Query(value = "SELECT count(*) FROM validator_organisation_review_mapping vorm WHERE vorm.user_id = :validatorId "
			+ "AND vorm.organisation_id = :organisationId", nativeQuery = true)
	Integer countByUserIdAndOrganisationId(@Param("organisationId") String organisationId,
			@Param("validatorId") String validatorId);
}
