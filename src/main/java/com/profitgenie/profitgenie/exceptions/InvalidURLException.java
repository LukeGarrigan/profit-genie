package com.profitgenie.profitgenie.exceptions;

public class InvalidURLException extends RuntimeException {

    public InvalidURLException(String url) {
        super("URL " + url+ " is not valid.");
    }

}
