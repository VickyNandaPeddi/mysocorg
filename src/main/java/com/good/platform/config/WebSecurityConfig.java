package com.good.platform.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.csrf().disable().authorizeRequests()
				// allow swagger resources
				.antMatchers(HttpMethod.GET, "/", "/v2/api-docs", // swagger
						"/webjars/**", // swagger-ui webjars
						"/swagger-resources/**", // swagger-ui resources
						"/configuration/**", // swagger configuration
						"/swagger-ui.html", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll().antMatchers(HttpMethod.POST, "/v1/user").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/roles").permitAll().antMatchers(HttpMethod.POST, "/v1/auth/**")
				.permitAll().antMatchers(HttpMethod.PUT, "/v1/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/email/**").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/user/find-by-email/**").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/user/find-by-phone/**").permitAll()
				.antMatchers(HttpMethod.POST, "/v1/user/sendEmailTest").permitAll()
				.anyRequest().authenticated().and()

				.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter(); // Convert
																									// realm_access.roles
																									// claims to granted
																									// authorities, for
																									// use in access
																									// decisions

		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
		return jwtAuthenticationConverter;
	}

	class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

		@Override
		@SuppressWarnings("unchecked")
		public Collection<GrantedAuthority> convert(final Jwt jwt) {
			final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
			return ((List<String>) realmAccess.get("roles")).stream().map(roleName -> "ROLE_" + roleName)
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}

	}
}
