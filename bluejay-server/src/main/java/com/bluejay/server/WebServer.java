package com.bluejay.server;


import com.bluejay.server.exceptions.FailedServerException;
import java.lang.reflect.Constructor;

public abstract class WebServer {
    private static final String UNDERTOW_PACKAGE = "com.bluejay.undertow.UndertowServer";
    private static final String TOMCAT_PACKAGE = "com.bluejay.tomcat.TomcatServer";
    private static final String JAYNIO_PACKAGE = "com.bluejay.jaynio.JayNioServer";

    protected WebServerConfig webServerConfig;
    boolean debugMode = false;

    public WebServer(WebServerConfig config) {
        webServerConfig = config;
    }

    public abstract void initServer();
    public abstract void start();
    public abstract void stop();
    public abstract boolean isStarted();
    public abstract String getServerName();

    public WebServerConfig getWebServerConfig() {
        return webServerConfig;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public static WebServer getServerByString(String server, WebServerConfig config)
            throws FailedServerException {
        String serverClassString;
        if (server.equalsIgnoreCase("undertow")) {
            serverClassString = UNDERTOW_PACKAGE;
        } else if (server.equalsIgnoreCase("tomcat")) {
            serverClassString = TOMCAT_PACKAGE;
        } else if (server.equalsIgnoreCase("jaynio")) {
            serverClassString = JAYNIO_PACKAGE;
        } else {
            throw new FailedServerException("Failed to instance WebServer with name: " + server + ", available servers[undertow, tomcat]");
        }

        try {
            Class<?> clazz = Class.forName(serverClassString);
            if (clazz != null) {
                Constructor<?> ctor = clazz.getConstructor(WebServerConfig.class);
                return (WebServer) ctor.newInstance(new Object[]{ config });
            }
        } catch (ReflectiveOperationException exception) {
            throw new FailedServerException("Failed to instance web server: " + exception.getMessage());
        }

        throw new FailedServerException("Failed to instance web server, check dependency for " + server);
    }
}
