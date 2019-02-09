package com.bukhmastov.teacheritmo.util.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

public abstract class AbstractFileReloadableConfig<T> extends AbstractReloadableConfig<T> {

    private static final Logger log = LoggerFactory.getLogger("config");

    protected final String sourcePath;
    protected final String encoding;

    private volatile boolean wasLoadedFirstTime;
    private URL configPath;
    private File configFile;
    private Long lastModified;

    protected AbstractFileReloadableConfig(String sourcePath) throws ConfigException {
        this(sourcePath, null);
    }

    protected AbstractFileReloadableConfig(String sourcePath, String encoding) throws ConfigException {
        this.sourcePath = sourcePath;
        this.encoding = encoding;
        firstFileCheckLocation();
        load();
    }

    @Override
    public synchronized boolean checkAndReload() throws ConfigException {
        if (!wasLoadedFirstTime) {
            log.debug("First loading {} ...", toString());
            load();
            return true;
        }
        if (configFile == null && !locateAndSetFile()) {
            return false;
        }
        log.debug("Check checkAndReload {} ...", toString());
        if (lastModified == null || configFile.lastModified() != lastModified) {
            load();
            return true;
        }
        return false;
    }

    protected abstract T createConfigData(Reader input) throws ConfigException;

    protected T createDefaultConfigData() throws ConfigException {
        throw new ConfigException(toString() + " - can't create data without file " + sourcePath);
    }

    private boolean locateAndSetFile() {
        log.debug("Locating {} file at {} ...", toString(), sourcePath);
        URL path = locateFileOrDirectory(sourcePath);
        if (path != null) {
            configPath = path;
            configFile = extractFile(configPath);
            return configFile != null;
        } else {
            return false;
        }
    }

    private void firstFileCheckLocation() {
        if (!locateAndSetFile()) {
            if (configPath == null) {
                log.warn("{} - no file at {}, default invocation", toString(), sourcePath);
            }
        }
    }

    private synchronized void load() throws ConfigException {
        if (configPath == null) {
            setData(createDefaultConfigData());
            return;
        }
        log.debug("Loading {} file : {}", toString(), configPath);
        try (InputStream is = configPath.openStream()) {
            Reader reader = encoding != null ? new InputStreamReader(is, encoding) : new InputStreamReader(is, Charset.defaultCharset());
            lastModified = configFile != null ? configFile.lastModified() : null;
            setData(createConfigData(reader));
            wasLoadedFirstTime = true;
            log.info("{} loaded successful from {}", toString(), configPath);
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }

    private URL locateFileOrDirectory(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return AbstractFileReloadableConfig.class.getClassLoader().getResource(path);
    }

    private File extractFile(URL url) {
        File file = urlToFile(url);
        if (!file.exists())
            return null;
        if (!file.isFile())
            return null;
        return file;
    }

    private File urlToFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            return new File(url.getPath());
        }
    }
}
