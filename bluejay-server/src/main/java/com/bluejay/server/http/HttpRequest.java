package com.bluejay.server.http;

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

    public HttpRequest() {
        this.headerProperties = new HashMap<>();
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setRequestType(HttpRequestType requestType) {
        this.requestType = requestType;
    }

    public HttpRequestType getRequestType() {
        return requestType;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public String getQueryParam(String key) {
        return queryParams != null ? queryParams.get(key) : null;
    }

    public Iterator<Map.Entry<String, String>> getQueryIterator() {
        return queryParams != null ? queryParams.entrySet().iterator() : null;
    }

    public void setHeaderProperties(Map<String, Object> headerProperties) {
        this.headerProperties = headerProperties;
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

    public void setRequestRoute(String requestRoute) {
        this.requestRoute = requestRoute;
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
