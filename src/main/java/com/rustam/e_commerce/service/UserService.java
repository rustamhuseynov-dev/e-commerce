package com.rustam.e_commerce.service;


import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.User;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.request.EmailAndPasswordUpdateRequest;
import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.request.UserUpdateRequest;
import com.rustam.e_commerce.dto.response.*;
import com.rustam.e_commerce.exception.EmailExistsException;
import com.rustam.e_commerce.exception.custom.ExistsException;
import com.rustam.e_commerce.exception.custom.UnauthorizedException;
import com.rustam.e_commerce.mapper.UserMapper;
import com.rustam.e_commerce.util.UtilService;
import jakarta.validation.Valid;
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
    EmailSendService emailSendService;

    public UserCreateResponse create(UserCreateRequest userCreateRequest) {
        boolean emailExists = utilService.findByEmailExists(userCreateRequest.getEmail());
        if (emailExists){
            throw new EmailExistsException("This email already exists in the database.");
        }
        User user = User.builder()
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .phone(userCreateRequest.getPhone())
                .enabled(false)
                .authorities(Collections.singleton(Role.USER))
                .build();
        baseUserRepository.save(user);
        emailSendService.sendVerificationCode(userCreateRequest.getEmail());
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

    public EmailAndPasswordUpdateResponse updateEmailAndPassword(EmailAndPasswordUpdateRequest emailAndPasswordUpdateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        BaseUser user = utilService.findById(emailAndPasswordUpdateRequest.getUserId());
        utilService.validation(currentUsername,user.getId());
        utilService.validation(currentUsername, user.getId());
        boolean exists = utilService.findAllByUser().stream()
                .map(User::getEmail)
                .anyMatch(existingUsername -> existingUsername.equals(emailAndPasswordUpdateRequest.getEmail()));
        if (exists) {
            throw new ExistsException("This email is already taken.");
        }
        user.setEmail(emailAndPasswordUpdateRequest.getEmail());
        if (!(emailAndPasswordUpdateRequest.getPassword() == null)){
            user.setPassword(emailAndPasswordUpdateRequest.getPassword());
        }
        baseUserRepository.save(user);
        EmailAndPasswordUpdateResponse emailAndPasswordUpdateResponse = new EmailAndPasswordUpdateResponse();
        modelMapper.map(user, emailAndPasswordUpdateResponse);
        emailAndPasswordUpdateResponse.setText("Email and Password change successfully");
        return emailAndPasswordUpdateResponse;
    }

    public UserResponse findById(UUID userId) {
        String currentUsername = utilService.getCurrentUsername();
        User user = (User) utilService.findById(userId);
        utilService.validation(currentUsername,user.getId());
        return userMapper.toDto(user);
    }
}
