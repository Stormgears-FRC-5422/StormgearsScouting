package com.example.stormgearsscouting;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesReader {
    public void createPropertiesFile(Context context) {
        Properties prop = new Properties();
        String propertiesPath = context.getFilesDir().getPath().toString() + "/s.properties";
        try {
            FileOutputStream out = new FileOutputStream(propertiesPath);
            prop.setProperty("eventCode", "0");
            prop.store(out, null);
            out.close();
        } catch (IOException e) {
            System.err.println("Failed to open app.properties file");
            e.printStackTrace();
        }
    }
}