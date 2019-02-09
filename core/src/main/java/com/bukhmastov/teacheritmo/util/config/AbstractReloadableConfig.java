package com.bukhmastov.teacheritmo.util.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

public abstract class AbstractReloadableConfig<T> implements ReloadableConfig<T> {

    private static final Logger log = LoggerFactory.getLogger("config");

    private volatile T data;

    protected void setData(T data) {
        log.info("{} reloaded : {}", toString(), data);
        this.data = data;
    }

    @Override
    public T data() {
        return data;
    }

    @Override
    public boolean isDataSet() {
        return data != null;
    }

    @Override
    public String toString() {
        return "Config[" + this.getClass().getSimpleName() + "]";
    }
}
