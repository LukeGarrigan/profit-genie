package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.rest.controller.dto.UserViewDto;

import java.util.List;
import java.util.Map;

public interface UserDaoCustom {

    User findUserByEmail(String email);

    List<UserViewDto> getUsers();
}
