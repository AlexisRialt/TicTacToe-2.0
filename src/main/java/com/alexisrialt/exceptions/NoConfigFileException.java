package com.alexisrialt.exceptions;

public class NoConfigFileException extends BaseException {
    public static final String defaultMessage = "No config file has been found. " +
            "Please provide a configuration file as a command line argument.";

    public NoConfigFileException(){
        super(defaultMessage);
    }
}
