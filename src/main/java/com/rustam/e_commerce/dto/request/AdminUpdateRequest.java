package com.rustam.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateRequest {
    @Size(max = 20, message = "Name cannot be longer than 20 characters.")
    private String name;

    @Size(max = 20, message = "Surname cannot be longer than 20 characters.")
    private String surname;

    @Pattern(regexp = "^[a-zA-Z0-9 _-]{0,20}$", message = "Username contains invalid characters or is too long!")
    private String username;

    @Pattern(regexp = "^$|\\(\\d{3}\\)-\\d{3}-\\d{4}", message = "The phone number must be in the format (000)-000-0000.")
    private String phone;
}
