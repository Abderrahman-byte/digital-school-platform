package com.abderrahmane.elearning.socialservice.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.abderrahmane.elearning.common.aspects.ClearCacheAspect;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
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

    @Bean
    public ClearCacheAspect clearCacheAspect () {
        return new ClearCacheAspect();
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
        registry.addInterceptor(new TeachersOnly()).addPathPatterns("/api/v?/teacher/**", "/api/teacher/**").order(2);
        registry.addInterceptor(new StudentsOnly()).addPathPatterns("/api/v?/student/**", "/api/student/**").order(2);
    }

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
