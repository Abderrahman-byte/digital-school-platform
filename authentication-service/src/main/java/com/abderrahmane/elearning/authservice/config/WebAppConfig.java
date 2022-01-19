package com.abderrahmane.elearning.authservice.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.abderrahmane.elearning.authservice")
@PropertySources(@PropertySource("classpath:jdbc.properties"))
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    Environment environment;

    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer () {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean EntityManager entityManager () {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        Map<String, String> jpaProperties = new HashMap<String, String>();
        jpaProperties.put("javax.persistence.jdbc.driver", environment.getProperty("jdbc.driver"));
        jpaProperties.put("javax.persistence.jdbc.url", environment.getProperty("jdbc.url"));
        jpaProperties.put("javax.persistence.jdbc.user", environment.getProperty("jdbc.user"));
        jpaProperties.put("javax.persistence.jdbc.password", environment.getProperty("jdbc.password"));

        return Persistence.createEntityManagerFactory("main-unit", jpaProperties);
    }
}
