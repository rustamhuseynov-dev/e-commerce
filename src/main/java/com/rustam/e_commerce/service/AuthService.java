package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.BaseUser;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.TokenPair;
import com.rustam.e_commerce.dto.request.AuthRequest;
import com.rustam.e_commerce.dto.response.AuthResponse;
import com.rustam.e_commerce.exception.custom.IncorrectPasswordException;
import com.rustam.e_commerce.util.UserDetailsServiceImpl;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AuthService {

    BaseUserRepository baseUserRepository;
    UtilService utilService;
    UserDetailsServiceImpl userDetailsService;
    PasswordEncoder passwordEncoder;
    RedisTemplate<String,String> redisTemplate;

    public AuthResponse login(AuthRequest authRequest) {
        BaseUser baseUser = utilService.findByUsername(authRequest.getUsername());
        if (!passwordEncoder.matches(authRequest.getPassword(), baseUser.getPassword())) {
            throw new IncorrectPasswordException("password does not match");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(baseUser.getId()));
        TokenPair tokenPair = utilService.tokenProvider(baseUser.getId(), userDetails);
        String redisKey = "refresh_token:" + baseUser.getId();
        redisTemplate.opsForValue().set(redisKey, tokenPair.getRefreshToken(), Duration.ofDays(2)); // 2 gün müddətinə saxla
        return AuthResponse.builder()
                .tokenPair(tokenPair)
                .build();
    }
}
