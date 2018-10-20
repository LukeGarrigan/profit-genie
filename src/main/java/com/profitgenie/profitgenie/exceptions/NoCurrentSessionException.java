package com.profitgenie.profitgenie.exceptions;

public class NoCurrentSessionException extends RuntimeException {


    public NoCurrentSessionException() {
        super("No current session");
    }
}
