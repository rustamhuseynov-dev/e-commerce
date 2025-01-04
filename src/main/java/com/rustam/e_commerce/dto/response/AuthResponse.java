package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dto.TokenPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private TokenPair tokenPair;
}
