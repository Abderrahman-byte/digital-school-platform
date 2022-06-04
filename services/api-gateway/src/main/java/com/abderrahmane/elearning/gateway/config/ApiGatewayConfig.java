package com.abderrahmane.elearning.gateway.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
public class ApiGatewayConfig {
    @Bean
    public RouteLocator routeLocator (RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p.path("/api/*/auth/**")
                    .filters(f -> f.rewritePath("/api/(?<version>.*)/auth/(?<segment>.*)", "/api/${version}/${segment}"))
                    .uri("lb://auth-service"))
                .route(p -> p.path("/api/*/social/**")
                    .filters(f -> f.rewritePath("/api/(?<version>.*)/social/(?<segment>.*)", "/api/${version}/${segment}"))
                    .uri("lb://social-service"))
                .route(p -> p.path("/api/*/school/**")
                    .filters(f -> f.rewritePath("/api/(?<version>.*)/school/(?<segment>.*)", "/api/${version}/${segment}"))
                    .uri("lb://school-management"))
                .build();
    }
}
