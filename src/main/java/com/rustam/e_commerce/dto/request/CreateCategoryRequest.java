package com.rustam.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCategoryRequest {
    @NotBlank(message = "You cannot submit an empty category name.")
    @Pattern(regexp = "^[a-zA-Z0-9 _-]{1,20}$", message = "Category name contains invalid characters!")
    private String categoryName;
}
