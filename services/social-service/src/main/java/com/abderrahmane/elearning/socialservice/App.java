package com.abderrahmane.elearning.socialservice;

import java.util.HashSet;
import java.util.Set;

import com.abderrahmane.elearning.socialservice.config.ApplicationInitializer;
import com.abderrahmane.elearning.common.helpers.TomcatAppLauncher;

import org.springframework.web.SpringServletContainerInitializer;

public class App {
    public static void main(String[] args) throws Exception {
        TomcatAppLauncher appLauncher = new TomcatAppLauncher();
        Set<Class<?>> initializers = new HashSet<>();

        initializers.add(ApplicationInitializer.class);

        appLauncher.setPort(8080);
        appLauncher.createWebapp();
        appLauncher.addServletContainerInitializer(new SpringServletContainerInitializer(), initializers);

        appLauncher.start();
    }
}
