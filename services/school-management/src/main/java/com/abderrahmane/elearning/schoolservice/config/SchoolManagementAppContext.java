package com.abderrahmane.elearning.schoolservice.config;

import com.abderrahmane.elearning.common.handlers.AuthenticatedOnly;
import com.abderrahmane.elearning.common.handlers.AuthenticationHandler;
import com.abderrahmane.elearning.common.handlers.SchoolOnly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.abderrahmane.elearning")
@EntityScan("com.abderrahmane.elearning")
public class SchoolManagementAppContext implements WebMvcConfigurer {
    @Autowired
    private Environment environment;

    @Autowired
    private AuthenticationHandler authenticationHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandler).addPathPatterns("/api/**").order(0);
        registry.addInterceptor(new AuthenticatedOnly()).addPathPatterns("/api/**").order(1);
        registry.addInterceptor(new SchoolOnly()).addPathPatterns("/api/**").order(2);
    }

    /* CORS Configuration */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowOrigins = environment.getProperty("cors.allowOrigins");

        if (allowOrigins == null)
            return;

        String[] allowOriginsList = allowOrigins.split(",");

        if (allowOriginsList.length <= 0)
            return;

        registry.addMapping("/api/**").allowedOrigins(allowOriginsList).allowCredentials(true).maxAge(3600);
    }
}
