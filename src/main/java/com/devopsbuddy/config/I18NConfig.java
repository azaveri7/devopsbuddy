package com.devopsbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class I18NConfig {

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource msgSource =
				new ReloadableResourceBundleMessageSource();
		msgSource.setBasename("classpath:i18n/messages");
		//Checks for new messages every 30 minutes
		msgSource.setCacheSeconds(1800);
		return msgSource;
	}
	
}
