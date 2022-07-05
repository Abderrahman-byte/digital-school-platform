package com.abderrahmane.elearning.authservice.config;

import com.abderrahmane.elearning.common.config.CommonConfig;
import com.abderrahmane.elearning.common.handlers.AuthenticationHandler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO : Implement problem details

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.abderrahmane.elearning")
@EntityScan("com.abderrahmane.elearning")
@Import({ CommonConfig.class })
@Slf4j
public class AuthServiceConfig implements WebMvcConfigurer {
    @Autowired
    private AuthenticationHandler authenticationHandler;

    @Autowired
    private Environment environment;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandler).addPathPatterns("/api/v1/isLoggedIn").order(1);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowOrigins = environment.getProperty("cors.allowOrigins");

        log.info("Allow Origin : " + allowOrigins);

        if (allowOrigins == null)
            return;

        String allowedOriginsList[] = allowOrigins.split(",");

        if (allowedOriginsList.length <= 0) return;

        registry.addMapping("/api/**")
            .allowedOrigins(allowedOriginsList)
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
