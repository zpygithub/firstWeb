package com.firstWeb.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtil {
    private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class.getName());

    private static Properties properties = loadProperty();;

    private static Properties loadProperty() {
        Properties p = new Properties();
        loadProp("config/config.properties", p);
//        loadProp("config/mail.properties", p);
//        loadProp("config/variable.properties", p);
        return p;
    }

    private static void loadProp(String config, Properties p) {
        InputStream is = null;
        try {
            is = getInputStream(config);
            if (null != is) {
                p.load(is);
            }
        } catch (IOException e) {
            LOGGER.log(java.util.logging.Level.WARNING, "Exception happened in loadProp() " + config, e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.log(java.util.logging.Level.WARNING, "Exception happened in loadProperty()" + config, e);
                }
            }
        }
    }

    public static String getValue(String key) {
        String value = properties.getProperty(key);
        return null == value ? "" : value;
    }

    private static InputStream getInputStream(String path) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            InputStream is = classLoader.getResourceAsStream(path);
            if (null == is) {
                throw new FileNotFoundException(path + " cannot be opened because it does not exist");
            }
            return is;
        }
        return null;
    }
}
