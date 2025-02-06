package com.rustam.e_commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailAndPasswordUpdateResponse {

    private String userId;
    private String email;
    private String password;
    private String text;
}
