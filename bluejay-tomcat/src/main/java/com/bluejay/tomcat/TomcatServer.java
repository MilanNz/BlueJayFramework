package com.bluejay.tomcat;

import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;
import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpRequestType;
import com.bluejay.server.http.HttpResponse;
import com.bluejay.server.servlet.ServletRegister;
import com.bluejay.server.servlet.WebServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class TomcatServer extends WebServer {
    private boolean started;
    private Tomcat tomcat;

    public TomcatServer(WebServerConfig config) {
        super(config);
    }

    @Override
    public void initServer() {
        TomcatRequestAdapter adapter = new TomcatRequestAdapter();

        tomcat = new Tomcat();
        // webServerConfig.getHostname()
        tomcat.setHost(new StandardHost());
        tomcat.setPort(webServerConfig.getPort());
        tomcat.addServlet("/", "base", new HttpServlet() {

            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
                processRequest(HttpRequestType.GET, req, resp);
            }

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
                processRequest(HttpRequestType.POST, req, resp);
            }

            @Override
            protected void doHead(HttpServletRequest req, HttpServletResponse resp) {
                processRequest(HttpRequestType.GET, req, resp);
            }

            @Override
            protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
                processRequest(HttpRequestType.GET, req, resp);
            }

            @Override
            protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
                processRequest(HttpRequestType.PUT, req, resp);
            }

            private void processRequest(HttpRequestType requestType,
                                        HttpServletRequest req,
                                        HttpServletResponse resp) {
                long startRequestTime = 0;
                if (isDebugMode()) {
                    startRequestTime = System.currentTimeMillis();
                }

                HttpRequest httpRequest = adapter.processServerExchange(req);
                httpRequest.setRequestType(requestType);

                WebServlet servlet = ServletRegister.getServletOrDefault(httpRequest.getRequestRoute());
                HttpResponse response = servlet.handleRequest(httpRequest);

                // Do response.
                // Write headers
                for (Map.Entry<String, Object> header : response.getHeaders().entrySet()) {
                    resp.setHeader(header.getKey(), (String) header.getValue());
                }

                // Set content-type
                resp.setContentType(response.getContentType());
                // Set http status
                resp.setStatus(response.getCode());
                // Set response message
                try {
                    resp.getWriter().write(response.getMsg());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (isDebugMode()) {
                    System.out.println("Request handle time " + (System.currentTimeMillis() - startRequestTime)
                            + "ms for request: " + httpRequest.getRequestRoute());
                }
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
