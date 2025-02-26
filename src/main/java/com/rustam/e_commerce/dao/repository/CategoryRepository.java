package com.rustam.e_commerce.dao.repository;

import com.rustam.e_commerce.dao.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByCategoryName(String categoryName);

    Optional<Category> findByCategoryName(String name);
}
