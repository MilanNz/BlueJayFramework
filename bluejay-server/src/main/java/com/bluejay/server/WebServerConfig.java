package com.bluejay.server;

public class WebServerConfig {
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
}
