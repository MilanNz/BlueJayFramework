package com.bluejay.server;

public abstract class WebServer {
    boolean debugMode = false;
    WebServerConfig webServerConfig;

    public WebServer(WebServerConfig config) {
        webServerConfig = config;
    }

    abstract void initServer();
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

    public static WebServer getServerByString(String server, WebServerConfig config) {
        if (server.equalsIgnoreCase("undertow")) {
            return new UndertowServer(config);
        }

        // Undertow is a default web server.
        return new UndertowServer(config);
    }
}
