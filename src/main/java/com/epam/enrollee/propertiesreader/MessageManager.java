package com.epam.enrollee.propertiesreader;

import java.util.ResourceBundle;

public class MessageManager {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
