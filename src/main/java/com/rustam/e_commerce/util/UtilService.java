package com.rustam.e_commerce.util;

import com.rustam.e_commerce.dao.entity.Cart;
import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.entity.Favorite;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.entity.user.Admin;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.Employee;
import com.rustam.e_commerce.dao.entity.user.User;
import com.rustam.e_commerce.dao.repository.*;
import com.rustam.e_commerce.dto.TokenPair;
import com.rustam.e_commerce.exception.custom.*;
import com.rustam.e_commerce.util.jwt.JwtUtil;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UtilService {

    BaseUserRepository baseUserRepository;
    JwtUtil jwtUtil;
    EmployeeRepository employeeRepository;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    CartRepository cartRepository;
    FavoriteRepository favoriteRepository;
    RedisTemplate<String,String> redisTemplate;

    public BaseUser findByUsername(String username) {
        return baseUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such username found."));
    }

    public UUID convertToUUID(String id) {
        try {
            log.info("id {}",id);
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid UUID format for ID: " + id, e);
        }
    }

    public TokenPair tokenProvider(String id, UserDetails userDetails) {
        return userDetails.isEnabled() ?
                TokenPair.builder()
                        .accessToken(jwtUtil.createToken(String.valueOf(id)))
                        .refreshToken(jwtUtil.createRefreshToken(String.valueOf(id)))
                        .build()
                : new TokenPair();
    }

    public BaseUser findById(UUID id) {
        return baseUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No such user found."));
    }

    public List<BaseUser> findAll(){
        return baseUserRepository.findAll();
    }

    public List<User> findAllByUser(){
        return baseUserRepository.findAllUser();
    }

    public List<Admin> findAllByAdmin(){
        return baseUserRepository.findAllAdmin();
    }

    public void validation(String currentUsername, String username) {
        if (!currentUsername.equals(username)){
            throw new NoAuthotiryException("You have no authority.");
        }
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public String generateIbanForUser() {
        String countryCode = "AZ";
        String checkDigits = "00";
        String uniqueAccountPart = UUID.randomUUID().toString().replace("-", "").substring(0, 22);

        return countryCode + checkDigits + uniqueAccountPart;
    }

    public Employee findByEmployeeId(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No such employee found."));
    }

    public Product findByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No such product found"));
    }

    public Cart findByCartId(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("No such cart was found"));
    }

    public boolean deleteRefreshToken(UUID id) {
        String redisKey = "refresh_token:" + id;
        return Boolean.TRUE.equals(redisTemplate.delete(redisKey));
    }

    public Category findByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("No such category was found"));
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public Cart findByCartIdAndUserId(Long cartId, String userId) {
        return cartRepository.findByIdAndUser(cartId,userId)
                .orElseThrow(() -> new CartNotFoundException("No cart found for these hards"));
    }

    public void cartClean(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void updateProductQuantity(Integer totalQuantity, Product product) {
        if (product.getQuantity() < totalQuantity) {
            throw new IllegalArgumentException("Insufficient product quantity!");
        }
        product.setQuantity(product.getQuantity() - totalQuantity);
        productRepository.save(product);
    }

    public void findByUserEmail(String email) {
        User user = baseUserRepository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException("No such user found."));
        user.setEnabled(true);
        baseUserRepository.save(user);
    }

    public BaseUser findByEmail(String email) {
        return baseUserRepository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException("No such user found."));
    }

    public boolean findByEmailExists(String email) {
        return baseUserRepository.existsBaseUserByEmail(email);
    }

    public boolean findByUsernameExists(String username){
        return baseUserRepository.findByUsername(username).isPresent();
    }

    public Favorite findByFavoriteId(Long id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new FavoriteNotFoundException("No such favorite found."));
    }
}
