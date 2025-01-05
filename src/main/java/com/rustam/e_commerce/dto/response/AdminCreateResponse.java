package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminCreateResponse {

    private String name;
    private String surname;
    private String phone;
    private String username;
    private String email;
    private Role role;
    private String text;
}
