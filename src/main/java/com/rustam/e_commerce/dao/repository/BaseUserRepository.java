package com.rustam.e_commerce.dao.repository;

import com.rustam.e_commerce.dao.entity.user.Admin;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, UUID> {
    @Query(value = "SELECT * FROM base_users WHERE user_type IN ('USER','ADMIN','EMPLOYEE') AND username = :username", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * from base_users where user_type = 'USER'",nativeQuery = true)
    List<User> findAllUser();

    @Query(value = "SELECT * from base_users where user_type = 'ADMIN'",nativeQuery = true)
    List<Admin> findAllAdmin();

    Optional<User> findByEmail(String email);

    boolean existsBaseUserByEmail(String email);
}
