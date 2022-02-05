package com.abderrahmane.elearning.schoolservice;

import java.util.HashSet;
import java.util.Set;

import com.abderrahmane.elearning.schoolservice.config.AppInitializer;
import com.abderrahmane.elearning.common.helpers.TomcatAppLauncher;

import org.springframework.web.SpringServletContainerInitializer;

public class App {
    public static void main(String[] args) throws Exception {
        TomcatAppLauncher appLauncher = new TomcatAppLauncher();
        Set<Class<?>> initializers = new HashSet<Class<?>>();
        initializers.add(AppInitializer.class);

        appLauncher.setPort(8080);
        appLauncher.createWebapp();

        // This may only work if the all app classes and dependencies classes where assembled in .jar
        // file otherwise comment the next line because ServletContainerInitializer is auto-detected
        // When It is on its own jar package
        appLauncher.addServletContainerInitializer(new SpringServletContainerInitializer(), initializers);

        appLauncher.start();
    }
}
