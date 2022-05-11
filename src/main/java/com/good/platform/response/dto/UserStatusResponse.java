package com.good.platform.response.dto;

import com.good.platform.enums.InviteStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Arya C Achari
 * @since 16-Nov-2021
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class UserStatusResponse {
	
	private String id;
	private String firstName;
	private String lastName;
	private String emailId;
	private InviteStatus inviteStatus;
	private Boolean detailsUpdated;
}
