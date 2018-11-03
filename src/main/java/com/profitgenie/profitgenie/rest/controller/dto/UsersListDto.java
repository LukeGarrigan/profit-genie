package com.profitgenie.profitgenie.rest.controller.dto;

import java.util.HashMap;
import java.util.Map;

public class UsersListDto {


    private Map<String, Boolean> names = new HashMap<>();

    public Map<String, Boolean> getNames() {
        return names;
    }

    public void setNames(Map<String, Boolean> names) {
        this.names = names;
    }
}

