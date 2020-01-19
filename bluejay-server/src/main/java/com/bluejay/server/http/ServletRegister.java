package com.bluejay.server.http;

import com.bluejay.server.servlet.WebServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletRegister {
    private static final String DEFAULT_SERVLET_KEY = "default_servlet";
    private static Map<String, WebServlet> servletRegister;

    static {
        servletRegister = new HashMap<>();
    }
    public static void registerServlet(String path, WebServlet easyServlet) {
        servletRegister.put(path, easyServlet);
    }

    public static WebServlet getServlet(String path) {
        return servletRegister.get(path);
    }

    public static void setDefaultServlet(WebServlet servlet) {
        registerServlet(DEFAULT_SERVLET_KEY, servlet);
    }

    public static WebServlet getServletOrDefault(String path) {
        if (!servletRegister.containsKey(path)) {
            return servletRegister.get(DEFAULT_SERVLET_KEY);
        }

        return servletRegister.get(path);
    }

    public static WebServlet getDefaultServlet() {
        return servletRegister.get(DEFAULT_SERVLET_KEY);
    }
}
