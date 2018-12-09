package com.devopsbuddy.config;

import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.devopsbuddy.backend.service.EmailService;
import com.devopsbuddy.backend.service.MockEmailService;

@Configuration
@Profile("dev")
@PropertySource("file:///${user.home}/logs/config/application-dev.properties")
public class DevelopmentConfig {

	private static final Logger LOG = LoggerFactory.getLogger(DevelopmentConfig.class);
	
	@Bean
	public EmailService getEmailService() {
		LOG.debug("Inside DevelopmentConfig");
		return new MockEmailService();
	}
	
	@Bean
	public ServletRegistrationBean h2ConsoleServletRegistrationBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet());
		bean.addUrlMappings("/console/*");
		return bean;
	}
	
}
