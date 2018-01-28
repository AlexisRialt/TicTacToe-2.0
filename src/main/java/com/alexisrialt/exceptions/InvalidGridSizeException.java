package com.alexisrialt.exceptions;

public class InvalidGridSizeException extends BaseException {
    public InvalidGridSizeException(String value) {
        super("The value : " + value + " is invalid.\n" +
                "The minimum grid size is 3 and the maximum size is 10.");
    }
}
