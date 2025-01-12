package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeCreateResponse {
    private String name;
    private String surname;
    private String phone;
    private String username;
    private String email;
    private String iban;
    private LocalDate startWorkDay;
    private Role role;
    private String text;
}
