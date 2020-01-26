package com.bluejay.tomcat;

import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpRequestAdapter;

import javax.servlet.http.HttpServletRequest;

public class TomcatRequestAdapter implements HttpRequestAdapter {

    @Override
    public HttpRequest getHttpRequest() {
        return null;
    }

    HttpRequest processServerExchange(HttpServletRequest servletRequest) {
        HttpRequest httpRequest = new HttpRequest();
        
        httpRequest.setRequestRoute(servletRequest.getRequestURI());

        // TODO: 26/01/2020 Not finished.
        
        return null;
    }
}
