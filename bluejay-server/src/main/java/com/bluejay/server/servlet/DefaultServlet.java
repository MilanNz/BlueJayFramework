package com.bluejay.server.servlet;

import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpResponse;
import com.bluejay.server.utils.JsonObject;

public class DefaultServlet extends WebServlet {

    @Override
    public HttpResponse handleRequest(HttpRequest request) {
        return HttpResponse.returnJson(new JsonObject()
                .putString("route not found", request.getRequestRoute()), 404);
    }
}
