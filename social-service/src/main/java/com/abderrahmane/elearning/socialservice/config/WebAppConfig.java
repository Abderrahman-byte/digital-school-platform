package com.abderrahmane.elearning.socialservice.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.abderrahmane.elearning.socialservice.converters.JsonMapHttpMessageConverter;
import com.abderrahmane.elearning.socialservice.handlers.AuthenticatedOnly;
import com.abderrahmane.elearning.socialservice.handlers.AuthenticationHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.abderrahmane.elearning.socialservice")
@PropertySources({
    @PropertySource("classpath:jdbc.properties")
})
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    private Environment environment;

    @Bean
    public MessageSource messageSource () {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");

        return messageSource;
    }

    @Bean
    public EntityManager entityManager () {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory () {
        return Persistence.createEntityManagerFactory("main-unit", jpaProperties());
    }

    @Bean
    public Map<String, String> jpaProperties () {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", environment.getProperty("jdbc.driver"));
        properties.put("javax.persistence.jdbc.url", environment.getProperty("jdbc.url"));
        properties.put("javax.persistence.jdbc.user", environment.getProperty("jdbc.user"));
        properties.put("javax.persistence.jdbc.password", environment.getProperty("jdbc.password"));

        return properties;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new JsonMapHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
    }

    /* Configure interceptors */
    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandler()).addPathPatterns("/api/**").order(0);
        registry.addInterceptor(new AuthenticatedOnly()).addPathPatterns("/api/**").order(1);
    }
}
