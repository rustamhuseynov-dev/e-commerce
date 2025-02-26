package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {

    private UUID id;
    private String name;
    private String surname;
    private String phone;
    private String username;
    private String email;
    private boolean enabled;
}
