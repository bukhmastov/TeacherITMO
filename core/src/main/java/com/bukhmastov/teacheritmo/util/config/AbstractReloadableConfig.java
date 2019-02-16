package com.bukhmastov.teacheritmo.util.config;

public abstract class AbstractReloadableConfig<T> implements ReloadableConfig<T> {

    private volatile T data;

    protected void setData(T data) {
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
