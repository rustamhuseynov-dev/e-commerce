package com.rustam.e_commerce.controller;


import com.rustam.e_commerce.dto.request.*;
import com.rustam.e_commerce.dto.response.AuthResponse;
import com.rustam.e_commerce.dto.response.EmailVerificationResponse;
import com.rustam.e_commerce.dto.response.ResetPasswordResponse;
import com.rustam.e_commerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return new ResponseEntity<>(authService.logout(refreshTokenRequest),HttpStatus.OK);
    }

    @PostMapping(path = "/email-verification")
    public ResponseEntity<EmailVerificationResponse> emailVerification(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest){
        return new ResponseEntity<>(authService.emailVerification(emailVerificationRequest),HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/forgot-your-password")
    public ResponseEntity<String> forgotYourPassword(@RequestBody ForgotYourPasswordRequest forgotYourPasswordRequest){
        return new ResponseEntity<>(authService.forgotYourPassword(forgotYourPasswordRequest),HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        return new ResponseEntity<>(authService.resetPassword(resetPasswordRequest),HttpStatus.ACCEPTED);
    }
}
