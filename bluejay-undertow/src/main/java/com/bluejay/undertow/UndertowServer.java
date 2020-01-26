package com.bluejay.undertow;

import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;
import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpResponse;
import com.bluejay.server.servlet.ServletRegister;
import com.bluejay.server.servlet.DefaultServlet;
import com.bluejay.server.servlet.WebServlet;
import io.undertow.Undertow;
import io.undertow.Version;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

import java.util.Map;

public class UndertowServer extends WebServer {
    private Undertow undertow;
    private boolean started;

    public UndertowServer(WebServerConfig config) {
        super(config);
        initServer();
    }

    @Override
    public void initServer() {
        Undertow.Builder builder = Undertow.builder();
        builder.addHttpListener(webServerConfig.getPort(), webServerConfig.getHostname());

        // set thread workers
        if (webServerConfig.getNumberOfWorkers() != 0) {
            builder.setWorkerThreads(webServerConfig.getNumberOfWorkers());
        }

        // set handler
        builder.setHandler(exchange -> {
            long startRequestTime = 0;
            if (isDebugMode()) {
                startRequestTime = System.currentTimeMillis();
            }

            HttpRequest httpRequest = new HttpRequest(exchange);
            WebServlet servlet = ServletRegister.getServletOrDefault(exchange.getRelativePath());
            HttpResponse response = servlet.handleRequest(httpRequest);

            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, response.getContentType());
            for (Map.Entry<String, Object> header : response.getHeaders().entrySet()) {
                exchange.getResponseHeaders().put(HttpString.tryFromString(header.getKey()), (String) header.getValue());
            }

            exchange.setStatusCode(response.getCode());
            exchange.getResponseSender().send(response.getMsg());

            if (isDebugMode()) {
                System.out.println("Request handle time " + (System.currentTimeMillis() - startRequestTime)
                        + "ms for request: " + httpRequest.getRequestRoute());
            }
        });

        // set buffer size
        if (webServerConfig.getBufferSize() != 0) {
            builder.setBufferSize(webServerConfig.getBufferSize());
        }

        // build
        undertow = builder.build();

        // Set default servlet
        ServletRegister.setDefaultServlet(new DefaultServlet());
    }

    @Override
    public void start() {
        // start server.
        if (undertow != null) {
            undertow.start();
            started = true;
        }
    }

    @Override
    public void stop() {
        // stop server.
        if (undertow != null) {
            undertow.stop();
            started = false;
        }
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public String getServerName() {
        return "Undertow version: " + Version.getFullVersionString();
    }
}
