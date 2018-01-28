package com.alexisrialt.exceptions;

public class GridSizeInvalidTypeException extends BaseException {
    private static final String defaultMessage = "The grid size has to be an integer.";

    public GridSizeInvalidTypeException() {
        super(defaultMessage);
    }
}
