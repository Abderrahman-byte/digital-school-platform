package com.abderrahmane.elearning.common.config;

import com.abderrahmane.elearning.common.aspects.ClearCacheAspect;
import com.abderrahmane.elearning.common.handlers.AuthenticationHandler;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class CommonConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames("messages");
        return messageSource;
    }

    // Load aspects
    @Bean
    public ClearCacheAspect clearCacheAspect () {
        return new ClearCacheAspect();
    }

    // FIXME : this is just temporary until implementing problem details
    @Bean
    public ErrorMessageResolver errorMessageResolver() {
        return new ErrorMessageResolver();
    }

    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }
}
