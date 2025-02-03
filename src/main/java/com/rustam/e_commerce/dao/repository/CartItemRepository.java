package com.rustam.e_commerce.dao.repository;

import com.rustam.e_commerce.dao.entity.CartItem;
import com.rustam.e_commerce.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.product WHERE ci.id = :id")
    CartItem findByIdWithProduct(@Param("id") Long id);

    Optional<CartItem> findByCartId(Long cartId);
}
