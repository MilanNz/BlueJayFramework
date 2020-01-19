package com.bluejay.server;

public class WebServerConfig {
    private String serverOption;
    private boolean serverDebugMode;

    private String hostname;
    private int port;
    private int numberOfWorkers;
    private int bufferSize;

    public WebServerConfig() {}

    public WebServerConfig setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public WebServerConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public int getPort() {
        return port;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public WebServerConfig setNumberOfWorkers(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
        return this;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public WebServerConfig setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public String getServerOption() {
        return serverOption;
    }

    public WebServerConfig setServerOption(String serverOption) {
        this.serverOption = serverOption;
        return this;
    }

    public boolean isServerDebugMode() {
        return serverDebugMode;
    }

    public WebServerConfig setServerDebugMode(boolean serverDebugMode) {
        this.serverDebugMode = serverDebugMode;
        return this;
    }

    @Override
    public String toString() {
        return "WebServerConfig{" +
                "serverOption='" + serverOption + '\'' +
                ", serverDebugMode=" + serverDebugMode +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                ", numberOfWorkers=" + numberOfWorkers +
                ", bufferSize=" + bufferSize +
                '}';
    }

    public void overrideAndUserDefault(WebServerConfig config) {
        if (port == 0) {
            port = config.getPort();
        }

        if (hostname == null || hostname.isEmpty()) {
            hostname = config.getHostname();
        }

        if (numberOfWorkers == 0) {
            numberOfWorkers = config.getNumberOfWorkers();
        }

        if (bufferSize == 0) {
            bufferSize = config.getBufferSize();
        }

        if (serverOption == null || serverOption.isEmpty()) {
            serverOption = config.getServerOption();
        }

        if (!serverDebugMode) {
            serverDebugMode = config.isServerDebugMode();
        }
    }
}
