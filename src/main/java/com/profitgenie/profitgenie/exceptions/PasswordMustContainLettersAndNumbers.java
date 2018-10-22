package com.profitgenie.profitgenie.exceptions;

public class PasswordMustContainLettersAndNumbers extends RuntimeException{


    public PasswordMustContainLettersAndNumbers() {
        super("Password must contain both letters and numbers");
    }
}
