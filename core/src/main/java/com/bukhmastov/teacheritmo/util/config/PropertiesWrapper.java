package com.bukhmastov.teacheritmo.util.config;

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class PropertiesWrapper {

    private final static ConfigurableConversionService conversionService = new DefaultConversionService();
    private final Properties properties;

    public PropertiesWrapper(Reader input) throws ConfigException {
        properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }

    public PropertiesWrapper(Map<String, String> input) {
        properties = new Properties();
        properties.putAll(input);
    }

    public String getProperty(String key) {
        return getPropertyImpl(key, null);
    }

    public String getProperty(String key, String defaultValue) {
        return getPropertyImpl(key, defaultValue);
    }

    public String getPropertyRequired(String key) throws ConfigException {
        String value = getProperty(key);
        if (value == null) {
            throw new ConfigException(String.format("Required key [%s] not found", key));
        }
        return value;
    }

    public <T> T getProperty(String key, Class<T> clazz) {
        return getPropertyImpl(key, clazz, null);
    }

    public <T> T getProperty(String key, Class<T> clazz, T defaultValue) {
        return getPropertyImpl(key, clazz, defaultValue);
    }

    public <T> T getPropertyRequired(String key, Class<T> valueType) throws ConfigException {
        T value = getProperty(key, valueType);
        if (value == null) {
            throw new ConfigException(String.format("Required key [%s] not found", key));
        }
        return value;
    }

    private String getPropertyImpl(String key, String defaultValue) {
        String property = properties.getProperty(key);
        return property != null ? property : defaultValue;
    }

    private <T> T getPropertyImpl(String key, Class<T> clazz, T defaultValue) {
        String property = getPropertyImpl(key, null);
        if (property == null) {
            return defaultValue;
        }
        if (String.class.equals(clazz)) {
            return (T) property;
        }
        if (!conversionService.canConvert(String.class, clazz)) {
            throw new IllegalArgumentException(String.format(
                    "Cannot convert value [%s] from String to target type [%s]",
                    property, clazz.getSimpleName()));
        }
        return conversionService.convert(property, clazz);
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertiesWrapper that = (PropertiesWrapper) o;
        return Objects.equals(properties, that.properties);
    }
}
