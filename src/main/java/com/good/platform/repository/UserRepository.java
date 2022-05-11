package com.good.platform.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.User;
import com.good.platform.enums.InviteStatus;
import com.good.platform.projection.SocialAdminDetailsProjection;
import com.good.platform.response.AgentDetailsDTO;
import com.good.platform.response.InvitedUsersResponse;
import com.good.platform.response.UserMinDetails;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByEmailIdIgnoreCase(String emailId);

	User findByUserName(String userName);

	Boolean existsByEmailIdIgnoreCase(String emailId);

	User findByUserIdpId(String userIdpId);

	User findByPhone(String phone);

	Boolean existsByPhone(String phone);

	@Query("SELECT count(u.id) FROM #{#entityName} u "
			+ "join com.good.platform.entity.UserRoles ur on ur.role = :role "
			+ "join com.good.platform.entity.UserRoleMapping urm on urm.user.id=u.id " + "and urm.userRoles.id=ur.id "
			+ "WHERE u.createdBy = :userId")
	Long getAgentCount(@Param("role") String role, @Param("userId") String userId);

	List<User> findBySyncedAtAfter(LocalDateTime convertStringToLocalDate);

	@Query(value = "select u.id as id, " + "CASE "
			+ "	WHEN (u.first_name is not null and u.middle_name is not null and u.last_name is not null) "
			+ "		then CONCAT(u.first_name, ' ', u.middle_name ,' ', u.last_name) "
			+ "	WHEN (u.middle_name is null and u.last_name is not null) "
			+ "		then CONCAT(u.first_name, ' ', u.last_name) "
			+ "	WHEN (u.middle_name is not null and u.last_name is null) "
			+ "		then CONCAT(u.first_name, ' ', u.middle_name) " + "END AS name, "
			+ "u.email_id as emailId, u.phone as phone " + "from public.users u "
			+ "inner join public.user_organisation_mapping uom on u.id=uom.user_id "
			+ "inner join public.user_role_mapping urm on urm.user_id=uom.user_id "
			+ "inner join public.user_roles r on r.id=urm.user_role_id and r.role='SOCIAL-ADMIN' "
			+ "where uom.partner_organisation_id=:organisationId", nativeQuery = true)

	List<SocialAdminDetailsProjection> findSocialAdminDetails(@Param("organisationId") String organisationId);

	@Query(value = "SELECT new com.good.platform.response.UserMinDetails(u.id, u.userIdpId, u.firstName, u.middleName, u.lastName, u.emailId, u.phone) "
			+ " FROM #{#entityName} u " + " WHERE u.userIdpId=:userIdpId ")
	UserMinDetails findUserByUserIdpId(@Param("userIdpId") String userIdpId);

	@Query(value = "SELECT new com.good.platform.response.UserMinDetails(u.id, u.userIdpId, u.firstName, u.middleName, u.lastName, u.emailId, u.phone) "
			+ " FROM #{#entityName} u " + " WHERE u.id=:id ")
	UserMinDetails findUserByUserId(@Param("id") String id);

	@Query("select distinct new com.good.platform.response.AgentDetailsDTO(u.id,u.firstName,u.lastName,u.emailId,u.userIdpId) FROM #{#entityName} u "
			+ " join com.good.platform.entity.UserOrganisationMapping uom on uom.user.id=u.id"
			+ " join com.good.platform.entity.UserProjectMapping upm on upm.user.id=u.id "
			+ " join com.good.platform.entity.UserRoleMapping urm on urm.user.id=u.id "
			+ " WHERE uom.organisation.id = :organisationId and upm.projectId = :projectId and  urm.userRoles.id = :roleId")
	List<AgentDetailsDTO> getAgentDetails(@Param("organisationId") String organisationId,
			@Param("projectId") String projectId, @Param("roleId") String roleId);

	@Query(value = "select distinct new com.good.platform.response.InvitedUsersResponse(o.id,o.name,u.id,u.firstName,u.lastName,"
			+ "u.emailId,u.phone,u.userIdpId,u.inviteStatus,u.middleName, u.active, ur.role) "
			+ "  from #{#entityName} u"
			+ " left join com.good.platform.entity.UserOrganisationMapping uom on uom.user.id=u.id "
			+ " left join com.good.platform.entity.Organisation o on o.id=uom.organisation.id "
			+ " join com.good.platform.entity.UserRoleMapping urm on urm.user.id=u.id"
			+ " join com.good.platform.entity.UserRoles ur on ur.id=urm.userRoles.id "
			+ " where ur.role in ('SOCIAL-ADMIN', 'ADMIN', 'VALIDATOR') "
			+ " and ((:organisationId IS NULL) or (o.id=:organisationId)) "
			+ " and ((:roleId IS NULL) or (urm.userRoles.id=:roleId)) "
			+ " and ((:inviteStatus IS NULL) or (u.inviteStatus=:inviteStatus)) "
			+ " and ((:name IS NULL) or (u.firstName like :name%) or (u.lastName like :name%)) "
			+ " AND u.userIdpId <> :userIdpId")
	Page<InvitedUsersResponse> getInvitedUsersForAdmin(@Param("organisationId") String organisationId,
			@Param("roleId") String roleId, @Param("inviteStatus") InviteStatus invitStatus, @Param("name") String name,
			@Param("userIdpId") String userIdpId, Pageable page);

	@Query(value = "select distinct new com.good.platform.response.InvitedUsersResponse(o.id,o.name,u.id,u.firstName,u.lastName,"
			+ "u.emailId,u.phone,u.userIdpId,u.inviteStatus,u.middleName, u.active, ur.role) "
			+ "  from #{#entityName} u"
			+ " left join com.good.platform.entity.UserOrganisationMapping uom on uom.user.id=u.id "
			+ " left join com.good.platform.entity.Organisation o on o.id=uom.organisation.id "
			+ " join com.good.platform.entity.UserRoleMapping urm on urm.user.id=u.id"
			+ " join com.good.platform.entity.UserRoles ur on ur.id=urm.userRoles.id "
			+ " left join com.good.platform.entity.UserProjectMapping upm on upm.user.id=u.id"
			+ " where ur.role in ('SOCIAL-ADMIN', 'ADMIN', 'VALIDATOR') "
			+ " and ((:organisationId IS NULL) or (o.id=:organisationId)) "
			+ " and ((:roleId IS NULL) or (urm.userRoles.id=:roleId)) "
			+ " and ((:inviteStatus IS NULL) or (u.inviteStatus=:inviteStatus)) "
			+ " and ((:projectId IS NULL) or (upm.projectId=:projectId)) "
			+ " and ((:name IS NULL) or (u.firstName like :name%) or (u.lastName like :name%)) "
			+ " AND u.userIdpId <> :userIdpId")
	Page<InvitedUsersResponse> getInvitedUsersForAdminWithProjectId(@Param("organisationId") String organisationId,
			@Param("roleId") String roleId, @Param("projectId") String projectId,
			@Param("inviteStatus") InviteStatus invitStatus, @Param("name") String name,
			@Param("userIdpId") String userIdpId, Pageable page);

}