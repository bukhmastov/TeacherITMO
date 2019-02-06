package com.bukhmastov.teacheritmo.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public abstract class AbstractConfig {

    protected Properties loadProperties(String filepath) throws IOException {
        Resource resource = new ClassPathResource(filepath);
        return PropertiesLoaderUtils.loadProperties(resource);
    }
}
