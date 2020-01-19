package com.bluejay.server.servlet;

import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpRequestType;
import com.bluejay.server.http.HttpResponse;

public abstract class WebServlet {

    public HttpResponse handleRequest(HttpRequest request) {
        if (request.getRequestType() == HttpRequestType.GET) {
            return handleGet(request);
        } else if (request.getRequestType() == HttpRequestType.POST) {
            return handlePost(request);
        }

        return null;
    }

    public HttpResponse handleGet(HttpRequest httpRequest) {
        return null;
    }

    public HttpResponse handlePost(HttpRequest httpRequest) {
        return null;
    }
}