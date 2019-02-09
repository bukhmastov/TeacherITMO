package com.bukhmastov.teacheritmo.config;

import com.bukhmastov.teacheritmo.util.config.AbstractPropertiesReloadableConfig;
import com.bukhmastov.teacheritmo.util.config.ConfigException;
import com.bukhmastov.teacheritmo.util.config.PropertiesWrapper;

public class AppConfig extends AbstractPropertiesReloadableConfig<AppConfigData> {

    public AppConfig() throws ConfigException {
        super("teacheritmo.properties");
    }

    @Override
    protected AppConfigData createConfigData(PropertiesWrapper wrapper) throws ConfigException {
        return new AppConfigData(wrapper);
    }
}
