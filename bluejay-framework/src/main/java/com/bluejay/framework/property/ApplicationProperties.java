package com.bluejay.framework.property;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ApplicationProperties {
    private static final String DEFAULT_PROPERTY_FILE = "application.properties";
    private static Map<String, String> properties;

    static {
        properties = new HashMap<>();
    }

    public static String getProperty(String key) {
        return properties.get(key);
    }

    public static int getPropertyAsInteger(String key) {
        String value = properties.get(key);
        if (value != null) {
            return Integer.parseInt(value);
        }

        return 0;
    }

    public static boolean getPropertyAsBoolean(String key) {
        return Boolean.parseBoolean(properties.get(key));
    }

    public static void setProperty(String key, String value) {
        if (!properties.containsKey(key)) {
            properties.put(key, value);
        }
    }

    public static void loadProperties(Class<?> clazz, String fileName) {
        Properties props = new Properties();
        try (InputStream inputStream = clazz.getClassLoader()
                .getResourceAsStream(fileName)) {
            props.load(inputStream);

            Set<String> propertyNames = props.stringPropertyNames();
            for (String name : propertyNames) {
                properties.put(name, props.getProperty(name));
            }
        } catch (Exception ignore) { }
    }

    public static void loadProperties(Class<?> clazz) {
        loadProperties(clazz, DEFAULT_PROPERTY_FILE);
    }

    public static boolean isSet() {
        return !properties.isEmpty();
    }
}
