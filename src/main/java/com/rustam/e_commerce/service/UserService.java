package com.rustam.e_commerce.service;


import com.rustam.e_commerce.dao.entity.BaseUser;
import com.rustam.e_commerce.dao.entity.User;
import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.request.UserUpdateRequest;
import com.rustam.e_commerce.dto.response.UserCreateResponse;
import com.rustam.e_commerce.dto.response.UserResponse;
import com.rustam.e_commerce.dto.response.UserUpdateResponse;
import com.rustam.e_commerce.exception.custom.ExistsException;
import com.rustam.e_commerce.mapper.UserMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserService {

    BaseUserRepository baseUserRepository;
    PasswordEncoder passwordEncoder;
    UtilService utilService;
    UserMapper userMapper;
    ModelMapper modelMapper;

    public UserCreateResponse create(UserCreateRequest userCreateRequest) {
        User user = User.builder()
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .enabled(true)
                .authorities(Collections.singleton(Role.USER))
                .build();
        baseUserRepository.save(user);
        return userMapper.toResponse(user);
    }

    public List<UserResponse> read() {
        List<User> users = utilService.findAllBy();
        return userMapper.toResponses(users);
    }

    public UserUpdateResponse update(UserUpdateRequest userUpdateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        BaseUser user = utilService.findById(userUpdateRequest.getId());
        utilService.validation(currentUsername, user.getId());
        boolean exists = utilService.findAllBy().stream()
                .map(User::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(userUpdateRequest.getUsername()));
        if (exists) {
            throw new ExistsException("This username is already taken.");
        }
        modelMapper.map(userUpdateRequest, user);
        baseUserRepository.save(user);
        return userMapper.toUpdated(user);
    }
}
