package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Admin;
import com.rustam.e_commerce.dao.entity.BaseUser;
import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.request.AdminCreateRequest;
import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.response.AdminCreateResponse;
import com.rustam.e_commerce.dto.response.AdminResponse;
import com.rustam.e_commerce.dto.response.UserCreateResponse;
import com.rustam.e_commerce.mapper.AdminMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AdminService {

    BaseUserRepository baseUserRepository;
    UtilService utilService;
    PasswordEncoder passwordEncoder;
    AdminMapper adminMapper;

    public AdminCreateResponse create(AdminCreateRequest adminCreateRequest) {
        BaseUser user = utilService.findById(adminCreateRequest.getId());
        Admin admin = Admin.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .enabled(true)
                .authorities(Collections.singleton(Role.ADMIN))
                .build();
        if (adminCreateRequest.getUsername().isBlank()){
            admin.setUsername(adminCreateRequest.getUsername());
        }
        if (adminCreateRequest.getEmail().isBlank()){
            admin.setEmail(adminCreateRequest.getEmail());
        }
        if (adminCreateRequest.getPassword().isBlank()){
            admin.setPassword(passwordEncoder.encode(adminCreateRequest.getPassword()));
        }
        baseUserRepository.save(admin);
        AdminCreateResponse.builder().text("Good luck in your new position.").build();
        return adminMapper.toResponse(admin);
    }

    public AdminResponse readById(UUID id) {
        BaseUser user = utilService.findById(id);
        return adminMapper.toRead(user);
    }
}
