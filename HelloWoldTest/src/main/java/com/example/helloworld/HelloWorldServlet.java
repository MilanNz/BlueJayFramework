package com.example.helloworld;

import com.bluejay.server.annotation.WebServletConfig;
import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpResponse;
import com.bluejay.server.servlet.WebServlet;
import com.bluejay.server.utils.JsonObject;

@WebServletConfig(path = "/v1/hello")
public class HelloWorldServlet extends WebServlet {

    @Override
    public HttpResponse handleGet(HttpRequest easyRequest) {
        return HttpResponse.returnJson(new JsonObject()
                .putString("status", "OK")
                .putString("message", "hello, hello ;)"));
    }

    @Override
    public HttpResponse handlePost(HttpRequest easyRequest) {
        String body = easyRequest.getBody();
        if (body != null) {
            return HttpResponse.jsonResponse("Hello:  " + body, 200);
        }

        return HttpResponse.jsonResponse("Hello from the POST method: ", 200);
    }
}
