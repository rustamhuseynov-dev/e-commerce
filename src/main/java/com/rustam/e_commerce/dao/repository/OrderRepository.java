package com.rustam.e_commerce.dao.repository;

import com.rustam.e_commerce.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
