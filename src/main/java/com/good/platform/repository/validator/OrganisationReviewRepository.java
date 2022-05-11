package com.good.platform.repository.validator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.validator.OrganisationReview;
import com.good.platform.entity.validator.ValidatorDetails;

@Repository
public interface OrganisationReviewRepository extends JpaRepository<OrganisationReview, String> {

	boolean existsByOrganisationId(String organisationId);

	OrganisationReview findByOrganisationId(String organisationId);

	OrganisationReview findByOrganisation_Id(String id);
		
}
