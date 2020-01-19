package com.bluejay.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JsonObject {
    private ObjectNode objectNode;

    public JsonObject() {
        objectNode = new ObjectMapper().createObjectNode();
    }

    public JsonObject(String value) {
        if (value != null && !value.isEmpty()) {
            try {
                objectNode = (ObjectNode) new ObjectMapper().readTree(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JsonObject putString(String key, String value) {
        objectNode.put(key, value);
        return this;
    }

    public String getString(String key) {
        return objectNode.get(key).asText();
    }

    public String getString(String key, String defaultValue) {
        return objectNode.get(key).asText(defaultValue);
    }

    public JsonObject putInt(String key, int value) {
        objectNode.put(key, value);
        return this;
    }

    public int getInt(String key) {
        return objectNode.get(key).asInt();
    }

    public int getInt(String key, int defaultValue) {
        return objectNode.get(key).asInt(defaultValue);
    }

    public JsonObject putLong(String key, long value) {
        objectNode.put(key, value);
        return this;
    }

    public long getLong(String key) {
        return objectNode.get(key).asLong();
    }

    public long getLong(String key, long defaultValue) {
        return objectNode.get(key).asLong(defaultValue);
    }

    public JsonObject putFloat(String key, float value) {
        objectNode.put(key, value);
        return this;
    }

    public JsonObject putDouble(String key, double value) {
        objectNode.put(key, value);
        return this;
    }

    public double getDouble(String key) {
        return objectNode.get(key).asDouble();
    }

    public double getDouble(String key, double defaultValue) {
        return objectNode.get(key).asDouble(defaultValue);
    }

    public JsonObject putBoolean(String key, boolean value) {
        objectNode.put(key, value);
        return this;
    }

    public boolean getBoolean(String key) {
        return objectNode.get(key).asBoolean();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return objectNode.get(key).asBoolean(defaultValue);
    }

    @Override
    public String toString() {
        return objectNode.toString();
    }
}
