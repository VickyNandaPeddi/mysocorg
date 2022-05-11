package com.good.platform.response.dto;

import com.good.platform.projection.SocialAdminDetailsProjection;

import lombok.Data;

/**
 * @implNote This is wrote for the mg-beneficiary to get the social-admin user details
 * 
 * @author Arya C Achari
 * @since 12-Jan-2022
 *
 */
@Data
public class SocialAdminDetails {
	
	private String id;
	private String name;
	private String emailId;
	private String phone;
	
	public SocialAdminDetails(SocialAdminDetailsProjection projection) {
		this.id = projection.getId();
		this.name = projection.getName();
		this.emailId = projection.getEmailId();
		this.phone = projection.getPhone();
	}
	
}
