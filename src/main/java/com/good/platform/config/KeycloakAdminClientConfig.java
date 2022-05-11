package com.good.platform.config;

import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KeycloakAdminClientConfig {

  private final KeycloakConfigProperties keycloakConfigProperties;

  @Bean
  public Keycloak getKeycloakAdmin() {
    return Keycloak.getInstance(keycloakConfigProperties.getKeycloakUrl(),
        keycloakConfigProperties.getAdminRealm(), keycloakConfigProperties.getAdminUser(),
        keycloakConfigProperties.getAdminPassword(), keycloakConfigProperties.getAdminClientId());
  }

}
