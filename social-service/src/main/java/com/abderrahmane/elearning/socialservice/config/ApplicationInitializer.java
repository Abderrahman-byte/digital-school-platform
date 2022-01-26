package com.abderrahmane.elearning.socialservice.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebAppConfig.class);
        ctx.refresh();

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("Dispatcher", new DispatcherServlet(ctx));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);

        FilterRegistration.Dynamic fDynamic = servletContext.addFilter("SpringMVCFilter", new RequestContextFilter());
        fDynamic.addMappingForUrlPatterns(null, false, "/*");

        servletContext.addListener(new ContextLoaderListener(ctx));
    }
}