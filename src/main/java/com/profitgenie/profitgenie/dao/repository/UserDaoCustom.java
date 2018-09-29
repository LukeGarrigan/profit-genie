package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.User;

public interface UserDaoCustom {

    User findUserByEmail(String email);

}
