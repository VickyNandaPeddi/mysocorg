package com.good.platform.projection;

/**
 * @implNote : Created to take the list or organization for the API
 *           /v1/validator/organisations?pageNumber=0&pageSize=10
 * @author Arya C Achari
 * @since 19-Jan-2022
 *
 */
public interface OrganisationDetailsProjection {

	public String getId();

	public String getName();

	public String getApprovalStatus();

	public String getApprovalStatusComment();

	public String getCreatedBy();

	//public LocalDate getCreatedOn();
	public Long getCreatedOn();
}
