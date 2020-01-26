package com.bluejay.framework;

import com.bluejay.framework.property.ApplicationProperties;
import com.bluejay.framework.property.PropertiesEnum;
import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;

public class BlueJayFramework {
    private static final int MAX_NUMBER_OF_TRIES = 10;

    private static BlueJayFramework instance = null;
    private BlueJayApplication blueJayApplication;

    public synchronized static void startTheWorld(WebServerConfig config, Class<?> clazz, String[] args) {
        if (instance == null) {
            instance = new BlueJayFramework();
            instance.initializeApplication(config, clazz);
        }
    }

    public synchronized static void startTheWorld(Class<?> clazz, String[] args) {
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
        WebServer server = WebServer.getServerByString(config.getServerOption(), config);
        server.setDebugMode(config.isServerDebugMode());
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
    }

    private WebServerConfig createOrOverrideWebServerConfig(WebServerConfig config) {
        if (config == null) {
            config = new WebServerConfig();
        }

        // Override params with params form application properties
        if (ApplicationProperties.isSet()) {
            WebServerConfig overrideConfig = new WebServerConfig();
            overrideConfig.setPort(ApplicationProperties.getPropertyAsInteger(PropertiesEnum.SERVER_PORT.getPropertyName()));
            overrideConfig.setHostname(ApplicationProperties.getProperty(PropertiesEnum.SERVER_HOSTNAME.getPropertyName()));
            overrideConfig.setNumberOfWorkers(ApplicationProperties.getPropertyAsInteger(PropertiesEnum.SERVER_WORKERS.getPropertyName()));
            overrideConfig.setBufferSize(ApplicationProperties.getPropertyAsInteger(PropertiesEnum.SERVER_BUFFER_SIZE.getPropertyName()));
            overrideConfig.setServerDebugMode(ApplicationProperties.getPropertyAsBoolean(PropertiesEnum.SERVER_DEBUG.getPropertyName()));
            overrideConfig.setServerOption(ApplicationProperties.getProperty(PropertiesEnum.SERVER_OPTION.getPropertyName()));
            config.overrideAndUserDefault(overrideConfig);
        }

        // At the end override params with default values if not set.
        WebServerConfig defaultConfig = new WebServerConfig();
        defaultConfig.setPort((int) PropertiesEnum.SERVER_PORT.getDefaultValue());
        defaultConfig.setHostname((String) PropertiesEnum.SERVER_HOSTNAME.getDefaultValue());
        defaultConfig.setNumberOfWorkers((int) PropertiesEnum.SERVER_WORKERS.getDefaultValue());
        defaultConfig.setBufferSize((int) PropertiesEnum.SERVER_BUFFER_SIZE.getDefaultValue());
        defaultConfig.setServerOption((String) PropertiesEnum.SERVER_OPTION.getDefaultValue());
        defaultConfig.setServerDebugMode((boolean) PropertiesEnum.SERVER_DEBUG.getDefaultValue());
        config.overrideAndUserDefault(defaultConfig);

        return config;
    }
}
