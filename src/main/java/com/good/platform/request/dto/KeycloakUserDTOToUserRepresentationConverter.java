package com.good.platform.request.dto;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class KeycloakUserDTOToUserRepresentationConverter implements Converter<UserRequest, UserRepresentation> {

	@Override
	public UserRepresentation convert(UserRequest source) {
		UserRepresentation user = new UserRepresentation();
		user.setUsername(source.getEmailId());
		user.setEmail(source.getEmailId());
		user.setEnabled(true);
		return user;
	}
}
