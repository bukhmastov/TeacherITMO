package com.bukhmastov.teacheritmo.util.config;

import java.io.Reader;

public abstract class AbstractPropertiesReloadableConfig<T> extends AbstractFileReloadableConfig<T> {

    public AbstractPropertiesReloadableConfig(String sourcePath) throws ConfigException {
        super(sourcePath);
    }

    public AbstractPropertiesReloadableConfig(String sourcePath, String encoding) throws ConfigException {
        super(sourcePath, encoding);
    }

    @Override
    protected T createConfigData(Reader input) throws ConfigException {
        return createConfigData(new PropertiesWrapper(input));
    }

    protected abstract T createConfigData(PropertiesWrapper wrapper) throws ConfigException;
}
