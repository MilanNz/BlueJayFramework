package com.bluejay.server.http;

public enum HttpRequestType {
    POST, GET, DELETE, PUT, UNKNOWN;

    public static HttpRequestType fromString(String requestMethod) {
        for (HttpRequestType type : HttpRequestType.values()) {
            if (type.name().equalsIgnoreCase(requestMethod)) {
                return type;
            }
        }

        return UNKNOWN;
    }
}
