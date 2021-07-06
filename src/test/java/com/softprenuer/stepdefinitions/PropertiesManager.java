package com.softprenuer.stepdefinitions;

import java.util.ResourceBundle;

public class PropertiesManager {
    private PropertiesManager() {}

    public static String getProperty(String key) {
        return ResourceBundle.getBundle("config").getString(key);
    }
}
