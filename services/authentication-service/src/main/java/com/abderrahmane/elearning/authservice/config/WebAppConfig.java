package com.abderrahmane.elearning.authservice.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.abderrahmane.config.CommonConfig;
import com.abderrahmane.elearning.common.aspects.ClearCacheAspect;
import com.abderrahmane.elearning.common.handlers.AuthenticationHandler;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.abderrahmane.elearning")
@PropertySource("classpath:env.properties")
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    private Environment environment;

    @Bean
    public CommonConfig commonConfig () {
        return new CommonConfig();
    }

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

    @Bean
    public DataSource dataSource() {
        String url = environment.getProperty("jdbc.url");
        String user = environment.getProperty("jdbc.user");
        String password = environment.getProperty("jdbc.password");
        String driver = environment.getProperty("jdbc.driver");

        return DataSourceBuilder.create().username(user).password(password).url(url).driverClassName(driver).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        Properties jpaProperties = new Properties();

        jpaProperties.setProperty("javax.persistence.schema-generation.database.action", "none");

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("com.abderrahmane.elearning");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return jpaTransactionManager;
    }

    @Bean
    public ClearCacheAspect clearCacheAspect () {
        return new ClearCacheAspect();
    }

    @Bean
    public ErrorMessageResolver errorMessageResolver() {
        return new ErrorMessageResolver();
    }

    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandler()).addPathPatterns("/api/v1/isLoggedIn").order(1);
    }

    /* Add CORS support */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowOrigins = environment.getProperty("cors.allowOrigins");

        if (allowOrigins == null)
            return;

        String allowedOriginsList[] = allowOrigins.split(",");

        if (allowedOriginsList.length <= 0)
            return;

        registry.addMapping("/api/**").allowedOrigins(allowedOriginsList).allowCredentials(true).maxAge(3600);
    }
}
