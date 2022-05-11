package com.good.platform.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.Organisation;
import com.good.platform.enums.InviteStatus;
import com.good.platform.model.OrganisationModel;
import com.good.platform.projection.InvitedUsersProjection;
import com.good.platform.projection.OrganisationDetailsProjection;
import com.good.platform.response.InvitedUsersResponse;
import com.good.platform.response.OrgByNameResponse;
import com.good.platform.response.dto.OrganisationNameDto;

/**
 * OrganisationRepository represents the repository class for the table
 * Organisation
 * 
 * @author Mohamedsuhail S
 *
 */
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, String> {

	public Organisation findByEmailIdIgnoreCase(String emailId);

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} SET headquarterCountry = :headquater,lastModifiedBy=:lastModifiedBy,"
			+ " modifiedAt=:modifiedAt WHERE id = :organisationId")
	void updateHeadquater(@Param("headquater") String headquater, @Param("organisationId") String organisationId,
			@Param("modifiedAt") Long modifiedAt, @Param("lastModifiedBy") String lastModifiedBy);

	@Query("SELECT org.id FROM #{#entityName} org WHERE org.createdBy = :userId")
	List<String> getOrganisationIdOfUser(@Param("userId") String userId);

	@Query("SELECT new com.good.platform.model.OrganisationModel(org.id, org.name) FROM #{#entityName} org WHERE org.id = (:organisationIdList)")
	List<OrganisationModel> getOrganisationIdOfUser(@Param("organisationIdList") Set<String> organisationIdList);

	@Query("SELECT org.name FROM #{#entityName} org WHERE org.id = :orgId")
	String getOrganisationName(@Param("orgId") String orgId);

	Long countByCreatedBy(String userIdpId);

	public List<Organisation> findBySyncedAtAfter(LocalDateTime convertStringToLocalDate);

	@Query(value = "select distinct new com.good.platform.response.InvitedUsersResponse(o.id,o.name,u.id,u.firstName,u.lastName,"
			+ "u.emailId,u.phone,u.userIdpId,u.inviteStatus,u.middleName, u.active, urm.userRoles.role) "
			+ "  from #{#entityName} o"
			+ " join com.good.platform.entity.UserOrganisationMapping uom on uom.organisation.id=o.id "
			+ " join com.good.platform.entity.User u on u.id=uom.user.id"
			+ " join com.good.platform.entity.UserRoleMapping urm on urm.user.id=u.id"
			+ " join com.good.platform.entity.UserProjectMapping upm on upm.user.id=u.id"
			+ " where (o.id=:organisationId) " + " and ((:roleId IS NULL) or (urm.userRoles.id=:roleId))"
			+ " and ((:inviteStatus IS NULL) or (u.inviteStatus=:inviteStatus))"
			+ " and ((:projectId IS NULL) or (upm.projectId=:projectId)) "
			+ " and ((:name IS NULL) OR (u.firstName LIKE :name%) OR (u.lastName LIKE :name%))")
	Page<InvitedUsersResponse> getInvitedUsers(@Param("organisationId") String organisationId,
			@Param("roleId") String roleId, @Param("projectId") String projectId,
			@Param("inviteStatus") InviteStatus invitStatus, @Param("name") String name, Pageable page);

	Long countByApprovalStatus(String approvalStatus);

	@Query(value = "select count(approval_status) as review from organisation where  approval_status ='REVIEW'"
			+ " UNION ALL "
			+ "select count(approval_status) as approved from organisation where  approval_status ='APPROVED'"
			+ " UNION ALL "
			+ "select count(approval_status) as rejected from organisation where  approval_status ='REJECTED'", nativeQuery = true)
	List<Long> findStatusCount();

	@Query(value = "SELECT org.id as id, org.name as name, org.approval_status as approvalStatus, "
			+ "org.approval_status_comment as approvalStatusComment, "
			+ "CONCAT(usr.first_name, ' ', usr.last_name) as createdBy, " + "org.created_at as createdOn "
			+ "FROM organisation org " + "INNER JOIN users usr ON org.created_by=usr.user_idp_id "
			+ "WHERE org.approval_status is not null and org.approval_status <> 'NONE' "
			+ " AND ((cast(:name as text) IS NULL) OR (cast(org.name as text) LIKE CONCAT(cast(:name as text),'%'))) "
			+ " AND ((:approvalStatus IS NULL) OR (org.approval_status=cast(:approvalStatus as text))) "
			+ " AND ((cast(:createdAt as text) IS NULL) OR (TO_CHAR(TO_TIMESTAMP(org.created_at/1000), 'YYYY-MM-DD')=cast(:createdAt as text))) "
			+ " ORDER BY org.created_at desc", nativeQuery = true)
	Page<OrganisationDetailsProjection> findAllOrganization(Pageable page, @Param("name") String name,
			@Param("approvalStatus") String approvalStatus, @Param("createdAt") String createdAt);

	@Query(value = "select distinct new com.good.platform.response.InvitedUsersResponse(o.id,o.name,u.id,u.firstName,u.lastName,"
			+ "u.emailId,u.phone,u.userIdpId,u.inviteStatus,u.middleName, u.active) " + "  from #{#entityName} o"
			+ " join com.good.platform.entity.UserOrganisationMapping uom on uom.organisation.id=o.id "
			+ " join com.good.platform.entity.User u on u.id=uom.user.id"
			+ " join com.good.platform.entity.UserRoleMapping urm on urm.user.id=u.id"
			+ " join com.good.platform.entity.UserProjectMapping upm on upm.user.id=u.id"
			+ " where (((:organisationId IS NULL) or (o.id=:organisationId)) and ((:roleId IS NULL) or (urm.userRoles.id=:roleId))"
			+ "and ((:inviteStatus IS NULL) or (u.inviteStatus=:inviteStatus))"
			+ "and ((:projectId IS NULL) or (upm.projectId=:projectId)))")
	Page<InvitedUsersResponse> getInvitedUsersForAdmin(@Param("organisationId") String organisationId,
			@Param("roleId") String roleId, @Param("projectId") String projectId,
			@Param("inviteStatus") InviteStatus invitStatus, Pageable page);

	@Query(value = "SELECT new com.good.platform.response.OrgByNameResponse(o.id, o.fullName, o.name, o.createdBy) "
			+ "FROM #{#entityName} o " + "WHERE o.name=:name")
	OrgByNameResponse findOrganisationByName(@Param("name") String name);

	@Query("SELECT new com.good.platform.response.dto.OrganisationNameDto(org.id, org.name) "
			+ "FROM #{#entityName} org " + "WHERE ((:name IS NULL) OR (org.name like :name%)) "
			+ "ORDER BY org.name ASC ")
	List<OrganisationNameDto> findAllOrganisationByIdAndName(@Param("name") String name);

	@Query(value = "select o.id as organisationId, o.name as organisationName, u.id as userId, "
			+ " u.first_name as firstName, u.last_name as lastName, u.email_id as emailId, u.phone as phone, "
			+ " u.user_idp_id as userIdpId, u.invite_status as inviteStatus, u.middle_name as middleName, u.active as active, "
			+ " ur.role "
			+ " from organisation o " + " join user_organisation_mapping uom on uom.partner_organisation_id=o.id "
			+ " join users u on u.id=uom.user_id " + " join user_role_mapping urm on urm.user_id=u.id "
			+ " join user_roles ur on urm.user_role_id = ur.id "
			+ " where o.id=:organisationId "
			+ " and ((cast(:roleId as text) IS NULL) or (urm.user_role_id=cast(:roleId as text))) "
			+ " and ((cast(:inviteStatus as text) IS NULL) or (u.invite_status=cast(:inviteStatus as text))) "
			+ " and ((cast(:name as text) IS NULL) OR ((LOWER(u.first_name) LIKE CONCAT('%', cast(:name as text),'%')) "
			+ "  OR (LOWER(u.last_name) LIKE CONCAT('%', cast(:name as text),'%'))))"
			+ " AND u.user_idp_id <> :userIdpId", nativeQuery = true)
	Page<InvitedUsersProjection> getInvitesdUsers(@Param("organisationId") String organisationId,
			@Param("roleId") String roleId, @Param("inviteStatus") String invitStatus, @Param("name") String name,
			@Param("userIdpId") String userIdpId, Pageable page);

	@Query(value = "select o.id as organisationId, o.name as organisationName, u.id as userId, "
			+ " u.first_name as firstName, u.last_name as lastName, u.email_id as emailId, u.phone as phone, "
			+ " u.user_idp_id as userIdpId, u.invite_status as inviteStatus, u.middle_name as middleName, u.active as active, "
			+ " ur.role "
			+ " from organisation o " + " join user_organisation_mapping uom on uom.partner_organisation_id=o.id "
			+ " join users u on u.id=uom.user_id " + " join user_role_mapping urm on urm.user_id=u.id "
			+ " join user_roles ur on urm.user_role_id = ur.id "
			+ " join user_project_mapping upm on upm.user_id=u.id " + " where o.id=:organisationId "
			+ " and ((cast(:roleId as text) IS NULL) or (urm.user_role_id=cast(:roleId as text))) "
			+ " and ((cast(:inviteStatus as text) IS NULL) or (u.invite_status=cast(:inviteStatus as text))) "
			+ " and ((cast(:projectId as text) IS NULL) or (upm.project_id=cast(:projectId as text))) "
			+ " and ((cast(:name as text) IS NULL) OR ((LOWER(u.first_name) LIKE CONCAT('%', cast(:name as text),'%')) "
			+ "  OR (LOWER(u.last_name) LIKE CONCAT('%', cast(:name as text),'%'))))"
			+ " AND u.user_idp_id <> :userIdpId", nativeQuery = true)
	Page<InvitedUsersProjection> getInvitesdUsersWithProjectId(@Param("organisationId") String organisationId,
			@Param("roleId") String roleId, @Param("projectId") String projectId,
			@Param("inviteStatus") String invitStatus, @Param("name") String name, @Param("userIdpId") String userIdpId,
			Pageable page);

}
