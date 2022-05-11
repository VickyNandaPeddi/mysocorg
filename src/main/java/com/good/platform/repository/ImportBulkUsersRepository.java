package com.good.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.good.platform.entity.ImportBulkUsers;

public interface ImportBulkUsersRepository extends JpaRepository<ImportBulkUsers, Long> {

	@Query(value = "select * from import_bulk_users  order by id asc limit :limit offset :offset", nativeQuery = true)
	List<ImportBulkUsers> getBulkUser(@Param("limit") Integer limit, @Param("offset") Integer offset);

}
