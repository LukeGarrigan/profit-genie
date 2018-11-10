package com.profitgenie.profitgenie.rest.controller.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersListDto {


    private List<UserViewDto> users = new ArrayList<>();


    public List<UserViewDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserViewDto> users) {
        this.users = users;
    }
}

