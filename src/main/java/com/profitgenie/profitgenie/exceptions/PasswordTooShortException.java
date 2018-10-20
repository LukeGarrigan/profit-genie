package com.profitgenie.profitgenie.exceptions;

public class PasswordTooShortException extends RuntimeException {


    public PasswordTooShortException() {
        super("Password must be atleast 8 characters long");
    }

}
