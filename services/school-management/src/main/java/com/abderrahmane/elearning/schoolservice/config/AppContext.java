package com.abderrahmane.elearning.schoolservice.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.abderrahmane.elearning")
@PropertySources({
    @PropertySource("classpath:jdbc.properties")
})
public class AppContext implements WebMvcConfigurer {
    @Autowired
    private Environment environment;

    @Bean
    public CriteriaBuilder criteriaBuilder () {
        return entityManagerFactory().getCriteriaBuilder();
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
}
