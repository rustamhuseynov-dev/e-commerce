package com.rustam.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequestCreate {
    @NotBlank(message = "The id column cannot be empty.")
    private UUID id;
    private String username;
    private String email;
    private String password;
}
