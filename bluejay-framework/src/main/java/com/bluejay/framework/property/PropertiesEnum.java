package com.bluejay.framework.property;

public enum PropertiesEnum {
    SERVER_PORT("bluejay.server.port", 8331),
    SERVER_HOSTNAME("bluejay.server.hostname", "localhost"),
    SERVER_WORKERS("bluejay.server.workers", 0),
    SERVER_BUFFER_SIZE("bluejay.server.buffersize", 0),
    SERVER_OPTION("bluejay.server.option", "undertow"),
    SERVER_DEBUG("bluejay.server.debug", false);

    private String propertyName;
    private Object defaultValue;

    PropertiesEnum(String propertyName, Object defaultValue) {
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
