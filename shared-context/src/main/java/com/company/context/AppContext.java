package com.company.context;

public class AppContext {

    private static String appPath;
    
    public static String getAppPath() {
        return appPath;
    }
    
    public static void setAppPath(String path) {
        appPath = path;
    }
}
