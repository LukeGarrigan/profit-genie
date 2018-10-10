package com.profitgenie.profitgenie.exceptions;

public class PasswordIncorrectException extends RuntimeException {

    public PasswordIncorrectException() {
        super("Incorrect credentials entered");
    }
}
