package com.rustam.e_commerce.service;


import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.User;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.request.UserUpdateRequest;
import com.rustam.e_commerce.dto.response.UserCreateResponse;
import com.rustam.e_commerce.dto.response.UserDeletedResponse;
import com.rustam.e_commerce.dto.response.UserResponse;
import com.rustam.e_commerce.dto.response.UserUpdateResponse;
import com.rustam.e_commerce.exception.custom.ExistsException;
import com.rustam.e_commerce.exception.custom.UnauthorizedException;
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
import java.util.UUID;

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
                .phone(userCreateRequest.getPhone())
                .enabled(true)
                .authorities(Collections.singleton(Role.USER))
                .build();
        baseUserRepository.save(user);
        return userMapper.toResponse(user);
    }

    public List<UserResponse> read() {
        List<User> users = utilService.findAllByUser();
        return userMapper.toResponses(users);
    }

    public UserUpdateResponse update(UserUpdateRequest userUpdateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        BaseUser user = utilService.findById(userUpdateRequest.getId());
        utilService.validation(currentUsername, user.getId());
        boolean exists = utilService.findAllByUser().stream()
                .map(User::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(userUpdateRequest.getUsername()));
        if (exists) {
            throw new ExistsException("This username is already taken.");
        }
        modelMapper.map(userUpdateRequest, user);
        UserUpdateResponse.builder().text("This user has been updated by you.").build();
        baseUserRepository.save(user);
        return userMapper.toUpdated(user);
    }

    public UserDeletedResponse delete(UUID id) {
        BaseUser baseUser = utilService.findById(id);
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(baseUser.getId(), currentUsername);
        boolean tokenDeleted = utilService.deleteRefreshToken(id);
        if (!tokenDeleted){
            throw new UnauthorizedException("An error occurred while logging out.");
        }
        UserDeletedResponse deletedResponse = new UserDeletedResponse();
        modelMapper.map(baseUser, deletedResponse);
        deletedResponse.setText("This user was deleted by you.");
        baseUserRepository.delete(baseUser);
        return deletedResponse;
    }
}
