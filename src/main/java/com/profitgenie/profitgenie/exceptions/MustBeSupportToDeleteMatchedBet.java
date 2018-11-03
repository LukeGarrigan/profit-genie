package com.profitgenie.profitgenie.exceptions;

public class MustBeSupportToDeleteMatchedBet extends RuntimeException {

    public MustBeSupportToDeleteMatchedBet(long id){
        super("Tried to remove matched bet "  + id);
    }
}
