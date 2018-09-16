package com.profitgenie.profitgenie.dao.repository;


import com.profitgenie.profitgenie.dao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {


}
