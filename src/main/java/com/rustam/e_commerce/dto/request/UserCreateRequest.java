package com.rustam.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    @NotBlank(message = "The name column cannot be empty.")
    private String name;
    @NotBlank(message = "The surname column cannot be empty.")
    private String surname;
    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{4}", message = "The phone number must be in the format (000)-000-0000.")
    private String phone;
    @NotBlank(message = "The username column cannot be empty.")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter a valid email address.")
    private String email;
    @NotBlank(message = "The password column cannot be empty.")
    private String password;
}
