package com.rustam.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    @NotBlank(message = "The username column cannot be empty.")
    private String username;
    @NotBlank(message = "The password column cannot be empty.")
    private String password;
}
