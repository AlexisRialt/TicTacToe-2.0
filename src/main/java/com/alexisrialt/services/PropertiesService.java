package com.alexisrialt.services;

import com.alexisrialt.Display;
import com.alexisrialt.exceptions.NoConfigFileException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {
    private final String[] args;
    private final Display display;

    public PropertiesService(Display display, String[] args) {
        this.display = display;
        this.args = args;
    }

    private InputStream getConfigStream() throws FileNotFoundException {
        if (args.length < 1) {
            throw new NoConfigFileException();
        }

        return new FileInputStream(args[0]);
    }

    public Properties get() {
        Properties properties = null;
        InputStream configStream = null;
        try {
            configStream = getConfigStream();
            properties = new Properties();
            properties.load(configStream);
        } catch (Exception e) {
            display.output(e.getMessage());
        } finally {
            if(configStream != null){
                try{
                    configStream.close();
                } catch (IOException e){
                    display.output(e.getMessage());
                }
            }
        }

        return properties;
    }
}