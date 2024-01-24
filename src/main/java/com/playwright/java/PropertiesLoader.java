package com.playwright.java;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesLoader {    
    public static void LoadProperties() throws Exception {
        FileInputStream propFile =
            new FileInputStream( "application.properties");
        Properties properties =
            new Properties(System.getProperties());
        properties.load(propFile);
        System.setProperties(properties);
        System.getProperties().list(System.out);
    }    
}
