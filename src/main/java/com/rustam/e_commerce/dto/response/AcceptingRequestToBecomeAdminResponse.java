package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptingRequestToBecomeAdminResponse {
    private String username;
    private Set<Role> role;
    private String text;
}
