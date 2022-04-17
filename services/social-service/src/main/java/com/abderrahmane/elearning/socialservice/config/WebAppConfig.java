package com.abderrahmane.elearning.socialservice.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

// import com.abderrahmane.elearning.common.aspects.TransactionManager;
// import com.abderrahmane.elearning.common.converters.JsonMessageConverter;
import com.abderrahmane.elearning.common.handlers.AuthenticatedOnly;
import com.abderrahmane.elearning.common.handlers.AuthenticationHandler;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.socialservice.handlers.StudentsOnly;
import com.abderrahmane.elearning.socialservice.handlers.TeachersOnly;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.abderrahmane.elearning")
@PropertySource("classpath:env.properties")
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
    public ErrorMessageResolver errorMessageResolver () {
        return new ErrorMessageResolver();
    }


    @Bean
    public DataSource dataSource () {
        String url = environment.getProperty("jdbc.url");
        String user = environment.getProperty("jdbc.user");
        String password = environment.getProperty("jdbc.password");
        String driver = environment.getProperty("jdbc.driver");

        return DataSourceBuilder.create().username(user).password(password).url(url).driverClassName(driver).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory () {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        Properties jpaProperties = new Properties();

        jpaProperties.setProperty("javax.persistence.schema-generation.database.action", "none");

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("com.abderrahmane.elearning.common.models");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager () {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return jpaTransactionManager;
    }

    // @Bean
    // public CriteriaBuilder criteriaBuilder () {
    //     return entityManagerFactory().getCriteriaBuilder();
    // }

    // @Bean
    // public EntityManager entityManager () {
    //     return entityManagerFactory().createEntityManager();
    // }

    // @Bean
    // public EntityManagerFactory entityManagerFactory () {
    //     return Persistence.createEntityManagerFactory("main-unit", jpaProperties());
    // }

    // @Bean
    // public Map<String, String> jpaProperties () {
    //     Map<String, String> properties = new HashMap<>();
    //     properties.put("javax.persistence.jdbc.driver", environment.getProperty("jdbc.driver"));
    //     properties.put("javax.persistence.jdbc.url", environment.getProperty("jdbc.url"));
    //     properties.put("javax.persistence.jdbc.user", environment.getProperty("jdbc.user"));
    //     properties.put("javax.persistence.jdbc.password", environment.getProperty("jdbc.password"));

    //     return properties;
    // }

    // /* Configure transaction management aspect */
    // @Bean
    // public TransactionManager transactionManagerAspect () {
    //     return new TransactionManager();
    // }

    // @Override
    // public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //     converters.add(new JsonMessageConverter());
    //     converters.add(new StringHttpMessageConverter());
    // }

    /* Configure interceptors */
    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandler()).addPathPatterns("/api/**").order(0);
        registry.addInterceptor(new AuthenticatedOnly()).addPathPatterns("/api/**").order(1);
        registry.addInterceptor(new TeachersOnly()).addPathPatterns("/api/v?/teacher/**", "/api/teacher/**").order(2);
        registry.addInterceptor(new StudentsOnly()).addPathPatterns("/api/v?/student/**", "/api/student/**").order(2);
    }

    // /* Configure Persistence Exception Translator */
    // @Bean
    // public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor () {
    //     return new PersistenceExceptionTranslationPostProcessor();
    // }

    // @Bean
    // public PersistenceExceptionTranslator persistenceExceptionTranslator () {
    //     return new HibernateExceptionTranslator();
    // }

    /* Allow cors */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowOrigins = environment.getProperty("cors.allowOrigins");

        if (allowOrigins == null) return;

        String allowedOriginsList[] = allowOrigins.split(",");

        if (allowedOriginsList.length <= 0) return;

        registry.addMapping("/api/**").allowedOrigins(allowedOriginsList).allowCredentials(true).maxAge(3600);
    }
}
