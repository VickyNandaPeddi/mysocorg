package com.good.platform.repository.validator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.validator.ValidatorDetails;

@Repository
public interface ValidatorDetailsRepository extends JpaRepository<ValidatorDetails, String> {

	ValidatorDetails findByUser_Id(String validatorId);
	boolean existsByUser_Id(String validatorId);
}
