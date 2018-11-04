package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordRestCustom {

    PasswordResetToken findByToken(String token);
}
