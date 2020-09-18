package com.bluejay.jaynio;

import com.bluejay.jaynio.server.HttpListener;
import com.bluejay.jaynio.server.JayNioWebServer;
import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;

import java.io.IOException;

public class JayNioServer extends WebServer {
    private JayNioRequestAdapter jayNioRequestAdapter;
    private JayNioWebServer jayNioWebServer;

    public JayNioServer(WebServerConfig config) {
        super(config);
        initServer();
    }

    @Override
    public void initServer() {
        jayNioRequestAdapter = new JayNioRequestAdapter();

        jayNioWebServer = new JayNioWebServer();
        jayNioWebServer.setServerHttpListening(webServerConfig.getHostname(), webServerConfig.getPort());
        jayNioWebServer.setHttpListener(new HttpListener() {
            @Override
            public void handleRequest() {
                System.out.println("NEW REQUEST HAS RECEIVED!");
            }
        });
    }

    @Override
    public void start() {
        try {
            jayNioWebServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            jayNioWebServer.stopServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isStarted() {
        return jayNioWebServer.isStarted();
    }

    @Override
    public String getServerName() {
        return "jaynio";
    }
}
