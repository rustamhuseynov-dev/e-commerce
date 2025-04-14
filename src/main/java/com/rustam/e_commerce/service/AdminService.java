package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.user.Admin;
import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.User;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import com.rustam.e_commerce.dto.request.AcceptingRequestToBecomeAdminRequest;
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
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

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
    EmailSendService emailSendService;

    public ForAdminResponse adminRequest() {
        String id = utilService.getCurrentUsername();
        BaseUser user = utilService.findById(UUID.fromString(id));
        user.setAuthorities(Collections.singleton(Role.REQUEST_ADMIN));
        baseUserRepository.save(user);
        emailSendService.sendEmailToMessage(user.getEmail(),"We have received your application to become an admin. If you are suitable, we will give you this title.");
        return ForAdminResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getAuthorities())
                .text("Information has been sent to your email.")
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

    public AdminUpdateResponse update(AdminUpdateRequest request) {
        UUID userId = UUID.fromString(utilService.getCurrentUsername());
        Admin user = (Admin) utilService.findById(userId);
        utilService.validation(userId.toString(), user.getId());

        if (isUsernameTaken(request.getUsername())) {
            throw new ExistsException("This username is already taken.");
        }

        updateFieldIfPresent(request.getName(), user::setName);
        updateFieldIfPresent(request.getSurname(), user::setSurname);
        updateFieldIfPresent(request.getUsername(), user::setUsername);
        updateFieldIfPresent(request.getPhone(), user::setPhone);

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

    public AcceptingRequestToBecomeAdminResponse acceptingRequestToBecomeAdmin(AcceptingRequestToBecomeAdminRequest acceptingRequestToBecomeAdminRequest) {
        User user = utilService.findByUserRoleIsRequestAdmin(acceptingRequestToBecomeAdminRequest.getUserId());
        switch (acceptingRequestToBecomeAdminRequest.getAcceptToEnum()) {
            case ACCEPTED:
                user.setAuthorities(Collections.singleton(Role.ADMIN));
                baseUserRepository.save(user);
                break;
            case REJECTED:
                user.setAuthorities(Collections.singleton(Role.USER));
                baseUserRepository.save(user);
                return AcceptingRequestToBecomeAdminResponse.builder()
                        .username(user.getUsername())
                        .role(user.getAuthorities())
                        .text("Unfortunately, you were not worthy of this role.")
                        .build();
            default:
                throw new IllegalArgumentException("Unknown request type!");
        }
        return AcceptingRequestToBecomeAdminResponse.builder()
                .username(user.getUsername())
                .role(Collections.singleton(Role.ADMIN))
                .text("Congratulations, good luck in your new position.")
                .build();
    }

    public List<ApplicationsToBecomeAdmin> applicationsToBecomeAdmin() {
        List<User> users = baseUserRepository.findAllByRoleRequestAdmin(Role.REQUEST_ADMIN);
        return adminMapper.toApplicationsToBecomeAdminResponses(users);
    }

    private boolean isUsernameTaken(String newUsername) {
        return utilService.findAllByAdmin().stream()
                .map(Admin::getUsername)
                .anyMatch(newUsername::equals);
    }

    private void updateFieldIfPresent(String value, Consumer<String> setter) {
        if (value != null && !value.isBlank()) {
            setter.accept(value);
        }
    }
}
