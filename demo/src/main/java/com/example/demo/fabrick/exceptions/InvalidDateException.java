package com.example.demo.fabrick.exceptions;

public class InvalidDateException extends Exception {

    private String field;
    private String message;

    public InvalidDateException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
