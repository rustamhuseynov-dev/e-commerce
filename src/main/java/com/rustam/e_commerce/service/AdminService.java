package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.user.Admin;
import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.request.AdminCreateRequest;
import com.rustam.e_commerce.dto.request.AdminUpdateRequest;
import com.rustam.e_commerce.dto.request.ForAdminRequest;
import com.rustam.e_commerce.dto.response.*;
import com.rustam.e_commerce.exception.custom.ExistsException;
import com.rustam.e_commerce.mapper.AdminMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class AdminService {

    BaseUserRepository baseUserRepository;
    UtilService utilService;
    PasswordEncoder passwordEncoder;
    AdminMapper adminMapper;
    ModelMapper modelMapper;

    public ForAdminResponse adminRequest() {
        String id = utilService.getCurrentUsername();
        BaseUser user = utilService.findById(UUID.fromString(id));
        user.setAuthorities(Collections.singleton(Role.REQUEST_ADMIN));
        baseUserRepository.save(user);
        return ForAdminResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getAuthorities())
                .build();
    }

    public AdminCreateResponse create(AdminCreateRequest adminCreateRequest) {
        String id = utilService.getCurrentUsername();
        BaseUser user = utilService.findById(UUID.fromString(id));
        Admin admin = Admin.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .enabled(true)
                .authorities(Collections.singleton(Role.ADMIN))
                .build();
        if (!adminCreateRequest.getUsername().isBlank()){
            admin.setUsername(adminCreateRequest.getUsername());
        }
        if (!adminCreateRequest.getEmail().isBlank()){
            admin.setEmail(adminCreateRequest.getEmail());
        }
        if (!adminCreateRequest.getPassword().isBlank()){
            admin.setPassword(passwordEncoder.encode(adminCreateRequest.getPassword()));
        }
        baseUserRepository.save(admin);
        baseUserRepository.delete(user);
        AdminCreateResponse.builder().text("Good luck in your new position.").build();
        return adminMapper.toResponse(admin);
    }

    public AdminResponse readById() {
        String id = utilService.getCurrentUsername();
        Admin user = utilService.findByAdmin(UUID.fromString(id));
        return adminMapper.toDto(user);
    }

    public AdminUpdateResponse update(AdminUpdateRequest adminUpdateRequest) {
        String userId = utilService.getCurrentUsername();
        Admin user = (Admin) utilService.findById(UUID.fromString(userId));
        utilService.validation(userId, user.getId());
        boolean exists = utilService.findAllByAdmin().stream()
                .map(Admin::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(adminUpdateRequest.getUsername()));
        if (exists) {
            throw new ExistsException("This username is already taken.");
        }
        if (adminUpdateRequest.getName() != null && !adminUpdateRequest.getName().isBlank()) {
            user.setName(adminUpdateRequest.getName());
        }
        if (adminUpdateRequest.getSurname() != null && !adminUpdateRequest.getSurname().isBlank()) {
            user.setSurname(adminUpdateRequest.getSurname());
        }
        if (adminUpdateRequest.getUsername() != null && !adminUpdateRequest.getUsername().isBlank()) {
            user.setUsername(adminUpdateRequest.getUsername());
        }
        if (adminUpdateRequest.getPhone() != null && !adminUpdateRequest.getPhone().isBlank()) {
            user.setPhone(adminUpdateRequest.getPhone());
        }
        modelMapper.map(adminUpdateRequest, user);
        baseUserRepository.save(user);
        return adminMapper.toUpdated(user);
    }

    public AdminDeleteResponse delete() {
        String id = utilService.getCurrentUsername();
        BaseUser baseUser = utilService.findById(UUID.fromString(id));
        utilService.validation(baseUser.getId(), id);
        AdminDeleteResponse deletedResponse = new AdminDeleteResponse();
        modelMapper.map(baseUser, deletedResponse);
        deletedResponse.setText("This user was deleted by you.");
        baseUserRepository.delete(baseUser);
        return deletedResponse;
    }
}
