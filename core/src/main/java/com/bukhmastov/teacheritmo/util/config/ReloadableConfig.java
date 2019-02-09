package com.bukhmastov.teacheritmo.util.config;

public interface ReloadableConfig<T> {

    T data();

    boolean isDataSet();

    boolean checkAndReload() throws ConfigException;
}
