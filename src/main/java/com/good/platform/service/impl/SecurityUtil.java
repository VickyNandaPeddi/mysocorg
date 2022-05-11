
package com.good.platform.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.good.platform.entity.User;
import com.good.platform.repository.UserRepository;

@Service
public class SecurityUtil {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	@Autowired
	UserRepository userRepo;

	public String getCurrentUser() {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	
		return loggedInUser.getName();

	}
	public String getToken() {
		ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		HttpServletRequest request = attributes.getRequest();
		String userToken = request.getHeader(AUTHORIZATION_HEADER);
		return userToken;

	}

	public HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", getToken());
		return headers;
	}

}
