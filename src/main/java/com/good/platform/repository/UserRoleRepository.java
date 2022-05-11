package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.UserRoles;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, String>{

	UserRoles findByRole(String role);
}
