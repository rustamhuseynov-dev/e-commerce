package com.rustam.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailAndPasswordUpdateRequest {

    @NotNull(message = "The user id column cannot be empty.")
    private UUID userId;
    @Pattern(regexp = "[a-z]+@[a-z]+\\.[a-z]{2,4}", message = "Enter your email address.")
    private String email;
    @NotBlank(message = "The password column cannot be empty.")
    private String password;
}
