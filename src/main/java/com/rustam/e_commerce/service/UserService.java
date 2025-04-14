package com.rustam.e_commerce.service;


import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.User;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.request.EmailAndPasswordUpdateRequest;
import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.request.UserUpdateRequest;
import com.rustam.e_commerce.dto.response.*;
import com.rustam.e_commerce.exception.custom.EmailExistsException;
import com.rustam.e_commerce.exception.custom.ExistsException;
import com.rustam.e_commerce.exception.custom.UnauthorizedException;
import com.rustam.e_commerce.exception.custom.UsernameExistsException;
import com.rustam.e_commerce.mapper.UserMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    Environment environment;

    public UserCreateResponse create(UserCreateRequest userCreateRequest) {
        boolean emailExists = utilService.findByEmailExists(userCreateRequest.getEmail());
        boolean usernameExists = utilService.findByUsernameExists(userCreateRequest.getUsername());
        if (usernameExists){
            throw new UsernameExistsException("This username already exists in the database.");
        }
        if (emailExists){
            throw new EmailExistsException("This email already exists in the database.");
        }
        boolean isTestProfile = Arrays.asList(environment.getActiveProfiles()).contains("test");
        User user = User.builder()
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .phone(userCreateRequest.getPhone())
                .enabled(isTestProfile)
                .authorities(Collections.singleton(Role.USER))
                .build();
        baseUserRepository.save(user);
        if (!isTestProfile) {
            emailSendService.sendVerificationCode(userCreateRequest.getEmail());
        }
        return userMapper.toResponse(user);
    }

    public List<UserResponse> read() {
        List<User> users = utilService.findAllByUser();
        return userMapper.toResponses(users);
    }

    public UserUpdateResponse update(UserUpdateRequest request) {
        String currentUsername = utilService.getCurrentUsername();
        UUID currentUserId = UUID.fromString(currentUsername);
        User user = (User) utilService.findById(currentUserId);

        utilService.validation(currentUsername, user.getId());

        checkUsernameOrEmailExists(request, UUID.fromString(user.getId()));

        updateUserFields(user, request);
        baseUserRepository.save(user);
        return userMapper.toUpdatedResponse(user);
    }

    public UserDeletedResponse delete(UUID id) {
        BaseUser baseUser = utilService.findById(id);
//        String currentUsername = utilService.getCurrentUsername();
//        utilService.validation(baseUser.getId(), currentUsername);
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

    private void checkUsernameOrEmailExists(UserUpdateRequest request, UUID userId) {
        List<User> users = utilService.findAllByUser();

        boolean usernameExists = users.stream()
                .anyMatch(u -> !u.getId().equals(userId) && u.getUsername().equals(request.getUsername()));

        boolean emailExists = users.stream()
                .anyMatch(u -> !u.getId().equals(userId) && u.getEmail().equals(request.getEmail()));

        if (usernameExists || emailExists) {
            throw new ExistsException("This username or email is already taken.");
        }
    }

    private void updateUserFields(User user, UserUpdateRequest request) {
        if (isNotBlank(request.getName())) user.setName(request.getName());
        if (isNotBlank(request.getSurname())) user.setSurname(request.getSurname());
        if (isNotBlank(request.getUsername())) user.setUsername(request.getUsername());
        if (isNotBlank(request.getEmail())) user.setEmail(request.getEmail());
        if (isNotBlank(request.getPhone())) user.setPhone(request.getPhone());
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
