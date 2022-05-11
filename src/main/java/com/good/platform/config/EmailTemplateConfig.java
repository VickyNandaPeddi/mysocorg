package com.good.platform.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * EmailTemplateConfig is to handle the configuration for the email regarding
 * agent onboarding
 * @author Mohamedsuhail
 *
 */
@Configuration
public class EmailTemplateConfig {
	
	    @Bean
	    public SpringTemplateEngine springTemplateEngine() {
	        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        templateEngine.addTemplateResolver(htmlTemplateResolver());
	        return templateEngine;
	    }
	    @Bean
	    public SpringResourceTemplateResolver htmlTemplateResolver(){
	        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
	        emailTemplateResolver.setPrefix("classpath:/templates/");
	        emailTemplateResolver.setSuffix(".html");
	        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
	        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
	        return emailTemplateResolver;
	    }

}
