package com.bluejay.server.http;

import com.bluejay.server.utils.JsonHelper;
import com.bluejay.server.utils.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private Map<String, Object> headers = new HashMap<>();
    private String contentType = "text/plain";
    private String msg;
    private int code;

    public static HttpResponse jsonResponse(String message, int code) {
        HttpResponse response = new HttpResponse(message, code);
        response.contentType = "application/json";
        return response;
    }

    public static HttpResponse returnObject(Object object) {
        try {
            return jsonResponse(JsonHelper.objectToJson(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResponse(null, 500);
    }

    public static HttpResponse returnJson(JsonObject jsonObject) {
        return jsonResponse(jsonObject.toString());
    }

    public static HttpResponse returnJson(JsonObject jsonObject, int code) {
        return jsonResponse(jsonObject.toString(), code);
    }

    public static HttpResponse jsonResponse(String message) {
        return jsonResponse(message, 200);
    }

    private HttpResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    /**
     * Adds response header property. Do not add property if key already exists.
     */
    public void addHeaderProperty(String key, Object value) {
        if (!headers.containsKey(key)) {
            headers.put(key, value);
        }
    }

    /**
     * Adds response header property or override if key already exists.
     */
    public void setHeaderProperty(String key, Object value) {
        headers.put(key, value);
    }

    public Object getHeaderProperty(String key) {
        return headers.get(key);
    }
}
