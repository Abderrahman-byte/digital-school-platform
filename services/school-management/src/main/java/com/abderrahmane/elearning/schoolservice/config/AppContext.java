package com.abderrahmane.elearning.schoolservice.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import com.abderrahmane.elearning.common.aspects.TransactionManager;
import com.abderrahmane.elearning.common.converters.JsonMessageConverter;
import com.abderrahmane.elearning.common.handlers.AuthenticatedOnly;
import com.abderrahmane.elearning.common.handlers.AuthenticationHandler;
import com.abderrahmane.elearning.common.handlers.SchoolOnly;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;

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
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
    public MessageSource messageSource () {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");

        return messageSource;
    }

    @Bean
    public ErrorMessageResolver errorMessageResolver () {
        return new ErrorMessageResolver();
    }

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

    /* Configure oap aspects */
    @Bean
    public TransactionManager transactionManagerAspect() {
        return new TransactionManager();
    }

    /* Configure handlers and interceptors */
    @Bean
    public AuthenticationHandler authenticationHandler () {
        return new AuthenticationHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandler()).addPathPatterns("/api/**").order(0);
        registry.addInterceptor(new AuthenticatedOnly()).addPathPatterns("/api/**").order(1);
        registry.addInterceptor(new SchoolOnly()).addPathPatterns("/api/**").order(2);
    }

    /* Configure Message Http converters */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new JsonMessageConverter());
        converters.add(new StringHttpMessageConverter());
    }

    /* Configure Persistence Exception Translator */
    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor () {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PersistenceExceptionTranslator persistenceExceptionTranslator () {
        return new HibernateExceptionTranslator();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowOrigins = environment.getProperty("cors.allowOrigins");

        if (allowOrigins == null) return;

        String[] allowOriginsList = allowOrigins.split(",");

        if (allowOriginsList.length <= 0) return;

        registry.addMapping("/api/**").allowedOrigins(allowOriginsList).allowCredentials(true).maxAge(3600);
    }
}
