package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetDao extends JpaRepository<PasswordResetToken, Long>, PasswordRestCustom {


}
