package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.UserOtherDetails;
import com.good.platform.response.dto.UserDetailsResponseDto;

@Repository
public interface UserOtherDetailsRepository extends JpaRepository<UserOtherDetails, String>{
	
	@Query("SELECT new com.good.platform.response.dto.UserDetailsResponseDto(uod.id, uod.user.id,"
			+ "uod.firstName, uod.middleName, uod.lastName, uod.emailId, uod.contactNumber, uod.organisation.id,"
			+ "uod.education, uod.educationDetails) "
			+ "FROM #{#entityName} uod "
			+ "WHERE uod.user.id = :userId")
	UserDetailsResponseDto getUserOtherDetails(@Param("userId") String userId);
	
	UserOtherDetails findByUserId(String userId);

}
