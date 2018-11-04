package com.profitgenie.profitgenie.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String data) {
        super("User " + data +" not found");
    }
}
