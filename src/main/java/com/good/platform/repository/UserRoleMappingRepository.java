package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.UserRoleMapping;
import com.good.platform.entity.UserRoles;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, String> {

	@Query("SELECT ur.role FROM #{#entityName} urm " + "JOIN com.good.platform.entity.UserRoles ur ON ur.id = urm.userRoles.id "
			+ "WHERE urm.user.id = :userId")
	String getUserRole(@Param("userId") String userId);
	
	@Query("SELECT ur FROM #{#entityName} urm " + "JOIN com.good.platform.entity.UserRoles ur ON ur.id = urm.userRoles.id "
			+ "WHERE urm.user.id = :userId")
	UserRoles getUserRoleId(@Param("userId") String userId);

	UserRoleMapping findByUser_Id(String userId);
	
}
