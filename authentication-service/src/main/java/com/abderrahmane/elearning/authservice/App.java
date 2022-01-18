package com.abderrahmane.elearning.authservice;

import com.abderrahmane.elearning.authservice.helpers.TomcatAppLauncher;

public class App {
    public static void main(String[] args) throws Exception {
        TomcatAppLauncher appLauncher = new TomcatAppLauncher();

        appLauncher.setPort(8080);
        appLauncher.createWebapp();

        appLauncher.start();
    }
}
