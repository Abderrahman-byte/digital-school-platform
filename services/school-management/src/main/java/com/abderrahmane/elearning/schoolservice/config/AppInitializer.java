package com.abderrahmane.elearning.schoolservice.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppContext.class);

        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("Dispatcher", new DispatcherServlet(ctx));
        dispatcherServlet.addMapping("/");
        dispatcherServlet.setLoadOnStartup(1);
        // dispatcherServlet.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}