package com.good.platform.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Value("${info.app.version}")
	private String version;

	@Bean
	public OpenAPI hubblehoxCourseServiceOpenAPI() {
		return new OpenAPI()
			//	.components(new Components().addSecuritySchemes("bearer-jwt",
						//new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
						//		.in(SecurityScheme.In.HEADER).name("Authorization")))
				.info(new Info().title("Good Platform - Social Organisation API")
						.description("Microservice that deals with Social Organisation and associated Content resources.")
						.version(version).license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
	}

}
