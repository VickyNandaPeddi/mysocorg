package com.good.platform.service.impl;

import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import com.good.platform.config.KeycloakConfigProperties;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.request.dto.KeycloakUserDTOToUserRepresentationConverter;
import com.good.platform.request.dto.UserRequest;
import com.good.platform.utility.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

	private final Keycloak keycloak;

	private final KeycloakUserDTOToUserRepresentationConverter keycloakUserDTOToUserRepresentationConverter;

	private final KeycloakConfigProperties keycloakConfigProperties;

	public String createUser(UserRequest userDTO) {
		UserRepresentation userRepresentation = keycloakUserDTOToUserRepresentationConverter.convert(userDTO);
		addCredentials(userRepresentation, userDTO.getPassword());
		Response response = keycloak.realm(keycloakConfigProperties.getRealm()).users().create(userRepresentation);
		System.out.println("keycloak response"+response.toString());
		if (response.getStatus() == 409) {
			throw new SOException(ErrorCode.CONFLICT, Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS);
		}
		String location = response.getLocation().getPath();
		String userId = location.substring(location.lastIndexOf("/") + 1);
		if (response.getStatus() != 201) {
			removeUser(userId);
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PASSWORD_ADD_FAIL);
		}

		return userId;
	}

	public Response removeUser(String userId) {
		return keycloak.realm(keycloakConfigProperties.getRealm()).users().delete(userId);
	}

	public void updateCredentials(String userId, String password) {
		UserResource userResource = keycloak.realm(keycloakConfigProperties.getRealm()).users().get(userId);
		UserRepresentation user = userResource.toRepresentation();
		addCredentials(user, password);
		userResource.update(user);
	}

	private void addCredentials(UserRepresentation userRepresentation, String password) {
		CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
		credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
		credentialRepresentation.setValue(password);
		userRepresentation.setCredentials(Arrays.asList(credentialRepresentation));

	}

	public void addRole(String role, String userId) {

		RoleRepresentation realmRole = keycloak.realm(keycloakConfigProperties.getRealm()).roles().get(role)
				.toRepresentation();
		UserResource userResource = keycloak.realm(keycloakConfigProperties.getRealm()).users().get(userId);
		userResource.roles().realmLevel().add(Arrays.asList(realmRole));

	}
	
	public String create(UserRequest userDTO) {
		UserRepresentation userRepresentation = keycloakUserDTOToUserRepresentationConverter.convert(userDTO);
		addCredentials(userRepresentation, userDTO.getPassword());
		Response response = keycloak.realm(keycloakConfigProperties.getRealm()).users().create(userRepresentation);
		System.out.println("keycloak response"+response.toString());
		if (response.getStatus() == 409) {
			throw new SOException(ErrorCode.CONFLICT, Constants.USER_KEYCLOAK_EMAIL_ALREADY_EXISTS);
		}
		String location = response.getLocation().getPath();
		String userId = location.substring(location.lastIndexOf("/") + 1);
		if (response.getStatus() != 201) {
			removeUser(userId);
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.PASSWORD_ADD_FAIL);
		}
		return userId;
	}
	
	public void updateRole(String existingRole, String newRole,String userId) {

		RoleRepresentation realmRole = keycloak.realm(keycloakConfigProperties.getRealm()).roles().get(newRole)
				.toRepresentation();
		UserResource userResource = keycloak.realm(keycloakConfigProperties.getRealm()).users().get(userId);
	
		RoleRepresentation rolesToRemove = keycloak.realm(keycloakConfigProperties.getRealm()).roles().get(existingRole)
				.toRepresentation();
		userResource.roles().realmLevel().remove(Arrays.asList(rolesToRemove));
		userResource.roles().realmLevel().add(Arrays.asList(realmRole));

	}
}
