package com.good.platform.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@Entity(name = "phone_otp")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneOTP extends DocumentId{
	
	private String otp;
	private String phone;
	private Boolean isExpired;
	private String timeSpan;
	private Long expirationStartTime;
	private Long expirationTime;
	
}
