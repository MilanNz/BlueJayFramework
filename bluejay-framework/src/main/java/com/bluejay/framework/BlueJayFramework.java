package com.bluejay.framework;

import com.bluejay.framework.property.AppPropertiesEnum;
import com.bluejay.framework.property.ApplicationProperties;
import com.bluejay.server.UndertowServer;
import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;

public class BlueJayFramework {
    private static final int MAX_NUMBER_OF_TRIES = 10;

    private static BlueJayFramework instance = null;
    private BlueJayApplication blueJayApplication;

    public synchronized static void startTheWorld(WebServerConfig config, Class<?> clazz) {
        if (instance == null) {
            instance = new BlueJayFramework();
            instance.initializeApplication(config, clazz);
        }
    }

    public synchronized static void startTheWorld(Class<?> clazz) {
        if (instance == null) {
            instance = new BlueJayFramework();
            instance.initializeApplication(null, clazz);
        }
    }

    public static BlueJayFramework getInstance() {
        return instance;
    }

    public String getApplicationPackageName() {
        if (blueJayApplication != null) {
            return blueJayApplication.getPackageName();
        }

        return null;
    }

    public String getApplicationName() {
        if (blueJayApplication != null) {
            return blueJayApplication.getApplicationName();
        }

        return null;
    }

    private void initializeApplication(WebServerConfig config, Class<?> clazz) {
        blueJayApplication = new BlueJayApplication();

        // Load properties from file
        ApplicationProperties.loadProperties(clazz);
        config = createOrOverrideWebServerConfig(config);

        // start web server.
        WebServer server = new UndertowServer(config);
        server.start();

        try {
            int tryCounter = 0;
            while (!server.isStarted() && tryCounter < MAX_NUMBER_OF_TRIES) {
                Thread.sleep(1_000);
                tryCounter++;
            }
        } catch (Exception ignored) {}

        // start application.
        blueJayApplication.setWebServer(server);
        blueJayApplication.start();
        System.out.println("get prop form system: " + System.getProperty("bluejay.server.port"));
    }

    private WebServerConfig createOrOverrideWebServerConfig(WebServerConfig config) {
        if (config == null) {
            config = new WebServerConfig();
        }

        int serverPort = config.getPort();
        String serverHostname = config.getHostname();
        int serverNumberOfWorkers = config.getNumberOfWorkers();
        int serverBufferSize = config.getBufferSize();

        // Override params with params form application properties
        if (ApplicationProperties.isSet()) {
            if (serverPort == 0) {
                serverPort = ApplicationProperties.getPropertyAsInteger(AppPropertiesEnum.SERVER_PORT.getPropertyName());
            }

            if (serverHostname == null || serverHostname.isEmpty()) {
                serverHostname = ApplicationProperties.getPropertyAsString(AppPropertiesEnum.SERVER_HOSTNAME.getPropertyName());
            }

            if (serverNumberOfWorkers == 0) {
                serverNumberOfWorkers = ApplicationProperties.getPropertyAsInteger(AppPropertiesEnum.SERVER_WORKERS.getPropertyName());
            }

            if (serverBufferSize == 0) {
                serverBufferSize = ApplicationProperties.getPropertyAsInteger(AppPropertiesEnum.SERVER_BUFFER_SIZE.getPropertyName());
            }
        }

        // At the end override paranms with default values if not set.
        if (serverPort == 0) {
            serverPort = (int) AppPropertiesEnum.SERVER_PORT.getDefaultValue();
        }

        if (serverHostname == null || serverHostname.isEmpty()) {
            serverHostname = (String) AppPropertiesEnum.SERVER_HOSTNAME.getDefaultValue();
        }

        if (serverNumberOfWorkers == 0) {
            serverNumberOfWorkers = (int) AppPropertiesEnum.SERVER_WORKERS.getDefaultValue();
        }

        if (serverBufferSize == 0) {
            serverBufferSize = (int) AppPropertiesEnum.SERVER_BUFFER_SIZE.getDefaultValue();
        }

        config.setPort(serverPort);
        config.setHostname(serverHostname);
        config.setNumberOfWorkers(serverNumberOfWorkers);
        config.setBufferSize(serverBufferSize);

        return config;
    }
}
