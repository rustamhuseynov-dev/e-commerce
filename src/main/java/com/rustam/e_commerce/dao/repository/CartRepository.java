package com.rustam.e_commerce.dao.repository;

import com.rustam.e_commerce.dao.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(UUID userId);
}
