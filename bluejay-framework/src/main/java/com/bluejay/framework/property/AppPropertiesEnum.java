package com.bluejay.framework.property;

public enum AppPropertiesEnum {
    SERVER_PORT("bluejay.server.port", 8331),
    SERVER_HOSTNAME("bluejay.server.hostname", "localhost"),
    SERVER_WORKERS("bluejay.server.workers", 0),
    SERVER_BUFFER_SIZE("bluejay.server.buffersize", 0);

    private String propertyName;
    private Object defaultValue;

    AppPropertiesEnum(String propertyName, Object defaultValue) {
        this.propertyName = propertyName;
        this.defaultValue = defaultValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}
