package com.rustam.e_commerce.dao.repository;


import com.rustam.e_commerce.dao.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    List<Favorite> findAllByUserId(String userId);
}
