package com.rustam.e_commerce.dto.request;

import com.rustam.e_commerce.dao.entity.enums.AcceptToEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptingRequestToBecomeAdminRequest {
    @NotBlank(message = "The userId column cannot be empty.")
    private String userId;
    @NotBlank(message = "either accept or reject")
    private AcceptToEnum acceptToEnum;
}
