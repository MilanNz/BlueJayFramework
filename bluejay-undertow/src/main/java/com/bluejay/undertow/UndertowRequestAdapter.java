package com.bluejay.undertow;

import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpRequestAdapter;
import com.bluejay.server.http.HttpRequestType;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.ChannelInputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class UndertowRequestAdapter implements HttpRequestAdapter {

    @Override
    public HttpRequest getHttpRequest() {
        return null;
    }

    HttpRequest processServerExchange(HttpServerExchange httpServerExchange) {
        if (httpServerExchange == null) {
            return null;
        }

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setRequestType(HttpRequestType.fromString(httpServerExchange.getRequestMethod().toString()));
        httpRequest.setRequestRoute(httpServerExchange.getRelativePath());

        if (httpRequest.getRequestType() == HttpRequestType.POST) {

            // Get body
            try {
                String body = IOUtils.toString(new ChannelInputStream(httpServerExchange.getRequestChannel()),
                        String.valueOf(StandardCharsets.UTF_8));
                httpRequest.setBody(body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<String, Object> headerProperties = new HashMap<>();

            // Get headers
            HeaderMap headerValues = httpServerExchange.getRequestHeaders();
            if (headerValues != null && headerValues.size() > 0) {
                Collection<HttpString> headers = headerValues.getHeaderNames();
                for (HttpString header : headers) {
                    Object value = headerValues.get(header.toString());
                    headerProperties.put(header.toString(), value);
                }
            }

            httpRequest.setHeaderProperties(headerProperties);

        } else if (httpRequest.getRequestType() == HttpRequestType.GET) {
            Map<String, String> queryParams = new HashMap<>();

            Map<String, Deque<String>> queryMap = httpServerExchange.getQueryParameters();
            if (queryMap.size() > 0) {
                queryParams = new HashMap<>();
            }

            for (Map.Entry<String, Deque<String>> entry : queryMap.entrySet()) {
                queryParams.put(entry.getKey(), entry.getValue().getFirst());
            }

            httpRequest.setQueryParams(queryParams);
        }

        return httpRequest;
    }
}
