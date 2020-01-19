package com.bluejay.server;

public abstract class WebServer {
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
}
