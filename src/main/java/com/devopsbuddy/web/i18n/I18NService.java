package com.devopsbuddy.web.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class I18NService {
	
	/*
	 * Here the spring framework will try to inject bean of type 
	 * MessageSource interface.
	 * Since ReloadableResourceBundleMessageSource implements MessageSource
	 * interface, it will get inject by spring framework here.
	 * ReloadableResourceBundleMessageSource bean was created by I18NConfig.java
	 * using @Bean annotation. 
	 */
	@Autowired
	private MessageSource messageSource;
	
	/*
	 * Returns message for the given message id and the default
	 * locale stored in the session context.
	 */
	public String getMessage(String messageId) {
		Locale locale = LocaleContextHolder.getLocale();
		return getMessage(messageId, locale);
	}
	
	public String getMessage(String messageId, Locale locale) {
		return messageSource.getMessage(messageId, null, locale);
	}
}
