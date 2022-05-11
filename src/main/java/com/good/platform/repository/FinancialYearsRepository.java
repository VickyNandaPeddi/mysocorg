package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.FinancialYears;

@Repository
public interface FinancialYearsRepository extends JpaRepository<FinancialYears, String> {
	
	FinancialYears findByFinancialYear(String data);

}
