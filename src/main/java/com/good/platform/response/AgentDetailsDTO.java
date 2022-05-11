package com.good.platform.response;

import java.time.LocalDate;

import com.good.platform.response.dto.GoodPlatformResponseVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgentDetailsDTO {
	
	
	public AgentDetailsDTO(String id, String firstName, String lastName,String email,String userIdpId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userIdpId =userIdpId;
	}
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private Integer beneficiaryCount ;
	private LocalDate date;
	private String userIdpId;
	

}
