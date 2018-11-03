package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDaoCustom {

    User findUserByEmail(String email);

    Map<String, Boolean> getUsers();
}
