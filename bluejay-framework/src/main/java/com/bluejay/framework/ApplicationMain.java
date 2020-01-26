package com.bluejay.framework;

import com.bluejay.server.annotation.WebServletConfig;
import com.bluejay.server.servlet.ServletRegister;
import com.bluejay.server.servlet.WebServlet;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ApplicationMain {

    public abstract void onApplicationStarted();

    public ApplicationMain() {
    }

    public final void registerApplicationServlet(String path, WebServlet servlet) {
        if (path != null && servlet != null) {
            ServletRegister.registerServlet(path, servlet);
        }
    }

    public final void registerApplicationServlet(WebServlet servlet) {
        if (servlet != null) {
            WebServletConfig webServlet = servlet.getClass().getDeclaredAnnotation(WebServletConfig.class);
            if (webServlet != null) {
                ServletRegister.registerServlet(webServlet.path(), servlet);
            }
        }
    }

    public final void registerApplicationServletAuto() {
        try {
            List<WebServlet> easyServlets = findAllEasyServletsByAnnotation();
            if (!easyServlets.isEmpty()) {
                for (WebServlet servlet : easyServlets) {
                    registerApplicationServlet(servlet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<WebServlet> findAllEasyServletsByAnnotation() throws Exception {
        List<WebServlet> easyServlets = new ArrayList<>();

        String packageName = "";
        BlueJayFramework framework = BlueJayFramework.getInstance();
        if (framework != null) {
            packageName = framework.getApplicationPackageName();
        }

        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(WebServletConfig.class);

        if (classes != null && !classes.isEmpty()) {
            for (Class<?> clazz : classes) {
                Constructor<?> ctor = clazz.getConstructors()[0];
                ctor.setAccessible(true);

                Object object = ctor.newInstance();
                if (object instanceof WebServlet) {
                    WebServlet easyServlet = (WebServlet) object;
                    easyServlets.add(easyServlet);
                }
            }
        }

        return easyServlets;
    }
}
