package com.good.platform.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.good.platform.enums.InviteStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Entity(name = "users")
public class User extends DocumentId {

	public User(String userId) {
		this.setId(userId);
	}
	
	public User() {
		
	}

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email_verified")
	private Boolean emailVerified;
	
	@Column(name = "phone_verified")
	private Boolean phoneVerified;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "profile_image_url")
	private String profileImageUrl;
	
	@Column(name = "user_idp_id")
	private String userIdpId;
	
    @Column(name ="details_updated" ,columnDefinition = "boolean default false")
	private Boolean detailsUpdated;
    
	@Column(name = "invite_status")
	@Enumerated(EnumType.STRING)
	private InviteStatus inviteStatus;
	
	@Column(name = "profile_image_filename")
	private String profileImageFilename;
	
	@Column(name = "middle_name")
	private String middleName;

}
