package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.TokenPair;
import com.rustam.e_commerce.dto.request.*;
import com.rustam.e_commerce.dto.response.AuthResponse;
import com.rustam.e_commerce.dto.response.EmailVerificationResponse;
import com.rustam.e_commerce.dto.response.ResetPasswordResponse;
import com.rustam.e_commerce.exception.custom.EmailVerificationProcessFailedException;
import com.rustam.e_commerce.exception.custom.IncompatibilityOccurredException;
import com.rustam.e_commerce.exception.custom.IncorrectPasswordException;
import com.rustam.e_commerce.exception.custom.UnauthorizedException;
import com.rustam.e_commerce.util.UserDetailsServiceImpl;
import com.rustam.e_commerce.util.UtilService;
import com.rustam.e_commerce.util.jwt.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    UtilService utilService;
    UserDetailsServiceImpl userDetailsService;
    PasswordEncoder passwordEncoder;
    RedisTemplate<String,String> redisTemplate;
    BaseUserRepository baseUserRepository;
    JwtUtil jwtUtil;
    EmailSendService emailSendService;
    ModelMapper modelMapper;

    public AuthResponse login(AuthRequest authRequest) {
        BaseUser baseUser = utilService.findByUsername(authRequest.getUsername());
        if (!passwordEncoder.matches(authRequest.getPassword(), baseUser.getPassword())) {
            throw new IncorrectPasswordException("password does not match");
        }
        if (!baseUser.getEnabled()){
            throw new EmailVerificationProcessFailedException("You need to confirm your email");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(baseUser.getId()));
        TokenPair tokenPair = utilService.tokenProvider(baseUser.getId(), userDetails);
        String redisKey = "refresh_token:" + baseUser.getId();
        redisTemplate.opsForValue().set(redisKey, tokenPair.getRefreshToken(), Duration.ofDays(2)); // 2 gün müddətinə saxla
        return AuthResponse.builder()
                .tokenPair(tokenPair)
                .build();
    }

    public String refreshToken(RefreshTokenRequest refreshTokenRequest) {
        log.info("api-gateway to send refresh token {}",refreshTokenRequest.getRefreshToken());
        String userId = jwtUtil.getUserIdAsUsernameFromTokenExpired(refreshTokenRequest.getRefreshToken()); // Token-dan user ID-ni çıxart
        if (userId == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        String redisKey = "refresh_token:" + userId; // Redis açarını yaradın
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey); // Redis-dən saxlanmış refresh token-i alın
        if (storedRefreshToken != null) {
            return jwtUtil.createToken(userId);
        } else {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    public String logout(RefreshTokenRequest refreshTokenRequest) {
        String userId = jwtUtil.getUserIdAsUsernameFromToken(refreshTokenRequest.getRefreshToken());
        String redisKey = "refresh_token:" + userId;
        Boolean delete = redisTemplate.delete(redisKey);
        if (Boolean.TRUE.equals(delete)){
            return "The refresh token was deleted and the user was logged out.";
        }
        else {
            throw new UnauthorizedException("An error occurred while logging out.");
        }
    }

    public EmailVerificationResponse emailVerification(EmailVerificationRequest emailVerificationRequest) {
        emailSendService.verificationCodeEqualsRequestCode(emailVerificationRequest);
        EmailVerificationResponse emailVerificationResponse = new EmailVerificationResponse();
        modelMapper.map(emailVerificationRequest,emailVerificationResponse);
        emailVerificationResponse.setText("Verification was successful.");
        return emailVerificationResponse;
    }

    public String forgotYourPassword(ForgotYourPasswordRequest forgotYourPasswordRequest) {
        emailSendService.sendEmailToChangePassword(forgotYourPasswordRequest);
        return "";
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        BaseUser user = utilService.findByEmail(resetPasswordRequest.getEmail());
        if (resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getAgainPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getAgainPassword()));
        }else {
            throw new IncompatibilityOccurredException("The entered passwords do not match");
        }
        baseUserRepository.save(user);
        return ResetPasswordResponse.builder()
                .email(resetPasswordRequest.getEmail())
                .newPassword(resetPasswordRequest.getNewPassword())
                .text("The password was successfully changed.")
                .build();
    }
}
