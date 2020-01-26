package com.bluejay.undertow;

import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpRequestAdapter;
import io.undertow.server.HttpServerExchange;

public class UndertowRequestAdapter implements HttpRequestAdapter {
    private HttpServerExchange httpServerExchange;

    public UndertowRequestAdapter(HttpServerExchange httpServerExchange) {
        this.httpServerExchange = httpServerExchange;
    }

    @Override
    public HttpRequest getHttpRequest() {
        return null;
    }
}
