package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {

    private UUID id;
    private String name;
    private String surname;
    private String phone;
    private String username;
    private String email;
    private String iban;
    private LocalDate startWorkDate;
    private boolean enabled;
    private Role role;
}
