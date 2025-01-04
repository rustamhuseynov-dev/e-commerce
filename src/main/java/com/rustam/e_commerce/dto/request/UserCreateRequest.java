package com.rustam.e_commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    private String name;
    private String surname;
    private String phone;
    private String username;
    private String email;
    private String password;
}
