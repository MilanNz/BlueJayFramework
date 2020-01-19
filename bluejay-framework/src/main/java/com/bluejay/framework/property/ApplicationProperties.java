package com.bluejay.framework.property;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ApplicationProperties {
    private static final String DEFAULT_PROPERTY_FILE = "application.properties";
    private static Map<String, Object> properties;

    static {
        properties = new HashMap<>();
    }

    public static Object getProperty(String propertyKey) {
        return properties.get(propertyKey);
    }

    public static String getPropertyAsString(String key) {
        Object value = properties.get(key);
        if (value instanceof String) {
            return (String) value;
        }

        return null;
    }

    public static int getPropertyAsInteger(String key) {
        Object value = properties.get(key);
        if (value instanceof Integer) {
            return (int) value;
        }

        return -1;
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
