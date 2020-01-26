package com.bluejay.tomcat;

import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TomcatServer extends WebServer {
    private boolean started;
    private Tomcat tomcat;

    public TomcatServer(WebServerConfig config) {
        super(config);
    }

    @Override
    public void initServer() {
        tomcat = new Tomcat();
        // webServerConfig.getHostname()
        tomcat.setHost(new StandardHost());
        tomcat.setPort(webServerConfig.getPort());
        tomcat.addServlet("/", "base", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                super.doGet(req, resp);
            }

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                super.doPost(req, resp);
            }
        });

        tomcat.getServer().await();
    }

    @Override
    public void start() {
        if (tomcat != null) {
            try {
                tomcat.start();
                started = true;
            } catch (LifecycleException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        if (tomcat != null) {
            try {
                tomcat.stop();
                started = false;
            } catch (LifecycleException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public String getServerName() {
        return "tomcat";
    }
}
