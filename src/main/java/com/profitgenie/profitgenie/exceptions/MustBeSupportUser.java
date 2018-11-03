package com.profitgenie.profitgenie.exceptions;

public class MustBeSupportUser extends RuntimeException {

    public MustBeSupportUser(){
        super("Tried support stuff");
    }
}
