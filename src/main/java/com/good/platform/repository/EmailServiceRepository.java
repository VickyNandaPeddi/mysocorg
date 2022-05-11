package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.EmailEntity;

@Repository
public interface EmailServiceRepository extends JpaRepository<EmailEntity, String>{
	
	EmailEntity findByValidatorTokenId(String validatorTokenId);
	
	EmailEntity findByEmailIgnoreCase(String emailId);
	
	Boolean existsByValidatorTokenId(String validatorTokenId);
	
	Boolean existsByEmailIgnoreCase(String email);
	
	EmailEntity findByValidatorTokenIdAndEmailIgnoreCase(String validatorTokenId,String email);
	
}
