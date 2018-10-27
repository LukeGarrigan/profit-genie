package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
}
