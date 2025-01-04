package com.rustam.e_commerce.controller;


import com.rustam.e_commerce.dto.request.AuthRequest;
import com.rustam.e_commerce.dto.request.RefreshTokenRequest;
import com.rustam.e_commerce.dto.response.AuthResponse;
import com.rustam.e_commerce.service.AuthService;
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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return new ResponseEntity<>(authService.logout(refreshTokenRequest),HttpStatus.OK);
    }

}
