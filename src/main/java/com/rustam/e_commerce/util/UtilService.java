package com.rustam.e_commerce.util;

import com.rustam.e_commerce.dao.entity.BaseUser;
import com.rustam.e_commerce.dao.entity.User;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.TokenPair;
import com.rustam.e_commerce.exception.custom.InvalidUUIDFormatException;
import com.rustam.e_commerce.exception.custom.NoAuthotiryException;
import com.rustam.e_commerce.exception.custom.UserNotFoundException;
import com.rustam.e_commerce.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
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
public class UtilService {

    private final BaseUserRepository baseUserRepository;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String,String> redisTemplate;

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

    public List<User> findAllBy(){
        return baseUserRepository.findAllBy();
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

}
