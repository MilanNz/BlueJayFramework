package com.bluejay.server.servlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServletRegister {
    private static final String DEFAULT_SERVLET_KEY = "default_servlet";
    private static Map<String, WebServlet> servletRegister;

    static {
        servletRegister = new ConcurrentHashMap<>();
    }

    public static void registerServlet(String path, WebServlet easyServlet) {
        servletRegister.put(path, easyServlet);
    }

    public static WebServlet getServlet(String path) {
        return servletRegister.get(path);
    }

    public static void removeServlet(String path) {
        servletRegister.remove(path);
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
