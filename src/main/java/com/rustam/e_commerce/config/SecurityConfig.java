package com.rustam.e_commerce.config;

import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.util.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x ->
                        x
                                .requestMatchers(getPublicEndpoints()).permitAll()
                                .requestMatchers(getUserRoleEndpoints()).hasAuthority(Role.USER.getValue())
                                .requestMatchers("/api/v1/admin/create").hasAuthority(Role.REQUEST_ADMIN.getValue())
                                .requestMatchers(getAdminRoleEndpoints()).hasAuthority(Role.ADMIN.getValue())
                                .anyRequest().authenticated()
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll)
                .httpBasic(withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }

    private String[] getPublicEndpoints() {
        return new String[]{
                "/api/v1/auth/login",
                "/api/v1/user/create",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html/**"
        };
    }

    private String[] getUserRoleEndpoints() {
        return new String[]{
                "/api/v1/auth/**",
                "/api/v1/user/update-email-and-password",
                "/api/v1/user/update",
                "/api/v1/user/delete/{id}",
                "/api/v1/user/read-by-id/{id}",
                "/api/v1/cart/**",
                "/api/v1/order/**"
        };
    }

    private String[] getAdminRoleEndpoints() {
        return new String[]{
                "/api/v1/admin/**",
                "/api/v1/auth/**",
                "/api/v1/cart/**",
                "/api/v1/category/**",
                "/api/v1/employee/**",
                "/api/v1/order/**",
                "/api/v1/product/**",
                "/api/v1/user/**",
        };
    }

}