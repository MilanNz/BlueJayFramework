package com.bluejay.server.http;

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
import java.util.Iterator;
import java.util.Map;

public class HttpRequest {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private HttpRequestType requestType;
    private Map<String, Object> headerProperties;
    private Map<String, String> queryParams;
    private String requestRoute;
    private String body;

    public HttpRequest(HttpServerExchange httpServerExchange) {
        this.httpServerExchange = httpServerExchange;
        this.headerProperties = new HashMap<>();
        processServerExchange();
    }

    public String getBody() {
        return body;
    }

    public HttpRequestType getRequestType() {
        return requestType;
    }

    private void processServerExchange() {
        requestType = HttpRequestType.fromString(httpServerExchange.getRequestMethod().toString());
        requestRoute = httpServerExchange.getRelativePath();
        if (requestType == HttpRequestType.POST) {

            // Get body
            try {
                this.body = IOUtils.toString(new ChannelInputStream(httpServerExchange.getRequestChannel()), String.valueOf(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get headers
            HeaderMap headerValues = httpServerExchange.getRequestHeaders();
            if (headerValues != null && headerValues.size() > 0) {
                Collection<HttpString> headers = headerValues.getHeaderNames();
                for (HttpString header : headers) {
                    Object value = headerValues.get(header.toString());
                    headerProperties.put(header.toString(), value);
                }
            }

        } else if (requestType == HttpRequestType.GET) {
            Map<String, Deque<String>> queryMap = httpServerExchange.getQueryParameters();
            if (queryMap.size() > 0) {
                queryParams = new HashMap<>();
            }
            for (Map.Entry<String, Deque<String>> entry : queryMap.entrySet()) {
                queryParams.put(entry.getKey(), entry.getValue().getFirst());
            }
        }
    }

    public String getQueryParam(String key) {
        return queryParams != null ? queryParams.get(key) : null;
    }

    public Iterator<Map.Entry<String, String>> getQueryIterator() {
        return queryParams != null ? queryParams.entrySet().iterator() : null;
    }

    public Object getHeaderProperty(String key) {
        return headerProperties.get(key);
    }

    public String getAuthorizationHeader() {
        return getHeaderTypeString(AUTHORIZATION_HEADER);
    }

    public String getContentTypeHeader() {
        return getHeaderTypeString(CONTENT_TYPE_HEADER);
    }

    public String getRequestRoute() {
        return requestRoute;
    }

    private String getHeaderTypeString(String key) {
        Object value = headerProperties.get(key);
        if (value != null) {
            return (String) value;
        }

        return null;
    }
}
