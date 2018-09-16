package com.profitgenie.profitgenie.exceptions;

public class EmailAlreadyRegistered extends RuntimeException {

    public EmailAlreadyRegistered(String email) {
        super(email + " is already registered");
    }
}
