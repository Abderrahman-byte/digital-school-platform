package com.abderrahmane.elearning.common.helpers;

import java.io.File;
import java.util.Set;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;

// FIXME : copy webapp folder to classpath that is make it gonna make it prossible to run jar on any folder

public class TomcatAppLauncher {
    private final String baseDir = "./target/tomcat";
    private String docBase = "src/main/webapp";
    private String host = "localhost";
    private int port = 8080;
    private final Tomcat tomcat;
    private Context context;

    public TomcatAppLauncher () {
        this.tomcat = new Tomcat();
        this.tomcat.setBaseDir(baseDir);
        this.tomcat.getConnector();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

    public void setDocBase (String docBase) {
        this.docBase = docBase;
    }

    public ServletContext getServletContext () {
        if (this.context == null) return null;

        return this.context.getServletContext();
    }

    public Context createWebapp () {
        return this.createWebapp("");
    }

    public Context createWebapp (String contextPath) {
        this.setupDirectory();
        this.tomcat.setPort(this.port);
        this.tomcat.setHostname(this.host);

        this.context = this.tomcat.addWebapp(contextPath, new File(this.baseDir, "ROOT").getAbsolutePath());

        return this.context;
    }

    public void addServletContainerInitializer (ServletContainerInitializer sci, Set<Class<?>> classes) throws Exception {
        this.checkContext();
        this.context.addServletContainerInitializer(sci, classes);
    }

    public Wrapper addServlet (String servletName, String servletClass, String path) throws Exception {
        this.checkContext();

        Wrapper wrapper = Tomcat.addServlet(this.context, servletName, servletClass);
        this.context.addServletMappingDecoded(path, servletName);
        wrapper.setLoadOnStartup(1);

        return wrapper;
    }

    public Wrapper addServlet (String servletName, Servlet servlet, String path) throws Exception {
        this.checkContext();

        Wrapper wrapper = Tomcat.addServlet(this.context, servletName, servlet);
        this.context.addServletMappingDecoded(path, servletName);
        wrapper.setLoadOnStartup(1);

        return wrapper;
    }

    public void start () throws LifecycleException {
        this.tomcat.setPort(this.port);
        this.tomcat.setHostname(this.host);
        this.tomcat.start();
        this.tomcat.getServer().await();
    }

    private void setupDirectory () {
        File appConfigDir = new File(this.baseDir, "ROOT");
        File webappDir = new File(this.docBase);

        if (!appConfigDir.exists()) appConfigDir.mkdirs();

        FileSystem.copyDirectoryContent(webappDir, appConfigDir);
    }

    private void checkContext () throws Exception {
        if (this.context == null) throw new Exception("Web App is not created Yet run : tomcatLauncher.createWebapp()");
    }
}