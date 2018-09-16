package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.rest.controller.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto user);

    User getUser(long id);

    UserDto loginUser(UserDto userDto);
}


