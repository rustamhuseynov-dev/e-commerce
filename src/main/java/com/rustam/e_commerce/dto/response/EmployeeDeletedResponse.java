package com.rustam.e_commerce.dto.response;

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
public class EmployeeDeletedResponse {
    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String phone;
    private LocalDate startWorkDay;
    private String iban;
    private String text;
}
