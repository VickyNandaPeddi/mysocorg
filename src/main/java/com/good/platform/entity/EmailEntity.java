package com.good.platform.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@Entity(name = "email_data")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailEntity extends DocumentId{
	
	private String validatorTokenId;
	private String email;
	private String role;
	private Boolean isExpired;
	private String timeSpan;
	private Long expirationStartTime;
	private Long expirationTime;
	
}
