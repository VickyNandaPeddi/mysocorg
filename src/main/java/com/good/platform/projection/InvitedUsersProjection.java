package com.good.platform.projection;

/**
 * @author Arya C Achari
 * @since 17-Feb-2022
 *
 */
public interface InvitedUsersProjection {
	
	public String getOrganisationId();
	public String getOrganisationName();
	public String getUserId();
	public String getFirstName();
	public String getLastName();
	public String getEmailId();
	public String getPhone();
	public String getUserIdpId();
	public String getInviteStatus();
	public String getMiddleName();
	public boolean isActive();
	public String getRole();
}
