package com.company.api.fw.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvConfig {

    private static final String CONFIG = "environment.properties";

    public static final String DEBUGMODE = "debugmode";
    public static final String UPLOAD_BASEPATH = "file.upload.basepath";
    public static final String DOWNLOAD_BASEPATH = "file.download.basepath";

    public static final String SMS_SERVICE_URL = "sms.serviceUrl";
    public static final String SMS_ACCOUNT = "sms.account";
    public static final String SMS_PASSWORD = "sms.password";
    
    public static final String VERSIONNUMBER = "versionNumber";
    public static final String VERSIONURL = "versionUrl";

    private static Properties p = null;

    public static Properties getEnvConfig() {
        if (p == null) {
            synchronized (EnvConfig.class) {
                if (p == null) {
                    p = new Properties();

                    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG);
                    try {
                        p.load(is);
                    } catch (IOException e) {
                        p = null;
                        e.printStackTrace();
                    }
                }
            }
        }
        return p;
    }

    public static String getProperty(String key) {
        return getEnvConfig().getProperty(key, "");
    }

    public static String getProperty(String key, String value) {
        return getEnvConfig().getProperty(key, value);
    }

    public static Object get(String key) {
        return getEnvConfig().get(key);
    }
}
