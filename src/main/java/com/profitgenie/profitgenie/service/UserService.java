package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.rest.controller.dto.UserDto;
import com.profitgenie.profitgenie.rest.controller.dto.UsersListDto;


public interface UserService {

    UserDto createUser(UserDto user);

    User getUser(long id);

    boolean isUserSupport(long id);

    User findUsersByEmail(String userEmail);

    void createPasswordResetTokenForUser(User user, String token);

    void changeUserPassword(User user, String password);

    UsersListDto getUsers();
}


