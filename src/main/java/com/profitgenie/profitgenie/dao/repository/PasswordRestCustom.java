package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.PasswordResetToken;

public interface PasswordRestCustom {

    PasswordResetToken findByToken(String token);
}
