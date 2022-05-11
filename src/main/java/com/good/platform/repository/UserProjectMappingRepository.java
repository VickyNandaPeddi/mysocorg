package com.good.platform.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.UserProjectMapping;
import com.good.platform.model.UserProjectMapModel;

@Repository
public interface UserProjectMappingRepository extends JpaRepository<UserProjectMapping, String> {

	@Query("SELECT new com.good.platform.model.UserProjectMapModel(upm.projectId, upm.user.id) "
			+ "FROM #{#entityName} upm " + "WHERE upm.user.id = :userId")
	List<UserProjectMapModel> getProjectUserMapModel(@Param("userId") String userId);

	@Query("SELECT upm.projectId FROM #{#entityName} upm WHERE upm.user.id = :userId")
	List<String> getProjectIdOfUser(@Param("userId") String userId);
	
	@Query("SELECT DISTINCT upm.projectId FROM #{#entityName} upm WHERE upm.user.id IN(:userId)")
	List<String> getDistinctProjectIdOfUsers(@Param("userId") List<String> userId);
	
	UserProjectMapping findByUserId(String userId);

	List<UserProjectMapping> findBySyncedAtAfter(LocalDateTime convertStringToLocalDate);

	//@Query("SELECT upm.projectId FROM #{#entityName} upm WHERE upm.user.id = :userId")
	//List<String> getProjectId(@Param("userId") String userId);
	
	@Query("SELECT new com.good.platform.model.UserProjectMapModel(upm.projectId, upm.user.id) "
			+ "FROM #{#entityName} upm " + "WHERE upm.user.id = :userId AND upm.projectId = :projectId")
	UserProjectMapModel findByUserIdAndProjectId(String userId, String projectId);
	
}
