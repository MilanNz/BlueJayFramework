package com.bluejay.framework;

import com.bluejay.framework.annotation.ApplicationConfiguration;
import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BlueJayApplication {
    private WebServer webServer;

    private String applicationPackageName;
    private String applicationName;

    public void start() {
        try {
            startApplication();
        } catch (Exception e) {
            System.out.println("Failed to start easy server");
            e.printStackTrace();
        }
    }

    public String getPackageName() {
        return applicationPackageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setWebServer(WebServer webServer) {
        this.webServer = webServer;
    }

    private void startApplication() throws Exception {
        printBanner();
        Reflections reflections = new Reflections(applicationPackageName);
        Set<Class<? extends ApplicationMain>> classes = reflections.getSubTypesOf(ApplicationMain.class);

        if (classes != null && !classes.isEmpty()) {
            for (Class<? extends ApplicationMain> clazz : classes) {
                Constructor<?> ctor = clazz.getConstructors()[0];
                ctor.setAccessible(true);

                // Get package name.
                applicationPackageName = clazz.getPackage().getName();

                Object object = ctor.newInstance();
                if (object instanceof ApplicationMain) {
                    ApplicationMain applicationMain = (ApplicationMain) object;
                    configApplicationClass(applicationMain);

                    // start application
                    applicationMain.onApplicationStarted();
                }

                // Application class can only be one :)
                break;
            }
        } else {
            System.out.println("No regiters main methods");
        }
    }

    private void configApplicationClass(ApplicationMain applicationMain) {
        ApplicationConfiguration annotation = applicationMain.getClass().getDeclaredAnnotation(ApplicationConfiguration.class);
        if (annotation != null) {
            // Register servlets automatically
            if (annotation.autoRegisterServlets()) {
                applicationMain.registerApplicationServletAuto();
            }

            // Use application package name is set
            if (!annotation.applicationPackage().isEmpty()) {
                applicationPackageName = annotation.applicationPackage();
            }

            // Set application name if configured
            if (!annotation.applicationName().isEmpty()) {
                applicationName = annotation.applicationName();
            }
        } else {
            // By default we register servlets automatically
            applicationMain.registerApplicationServletAuto();
        }
    }

    private void printBanner() {
        System.out.println(
                "" +
                        "   ____  _                _             \n" +
                        "  | __ )| |_   _  ___    | | __ _ _   _ \n" +
                        "  |  _ \\| | | | |/ _ \\_  | |/ _` | | | |\n" +
                        "  | |_) | | |_| |  __/ |_| | (_| | |_| |\n" +
                        "  |____/|_|\\__,_|\\___|\\___/ \\__,_|\\__, |\n" +
                        "                                  |___/ ");
        String frameworkVersion = BlueJayApplication.class.getPackage().getImplementationVersion();
        if (frameworkVersion == null || frameworkVersion.isEmpty()) {
            frameworkVersion = "Unknown";
        }
        System.out.println("BlueJay version: " + frameworkVersion);
        System.out.println("Web server, " + webServer.getServerName());

        WebServerConfig config = webServer.getWebServerConfig();
        if (config != null) {
            System.out.println(config.toString());
            System.out.println("Server is running on " + config.getHostname() + ":" + config.getPort());
        }

        if (applicationName != null) {
            System.out.println("Application " + applicationName + " is initialized!");
        }
        System.out.println("-->");
    }
}
