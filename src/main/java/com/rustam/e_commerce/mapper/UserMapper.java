package com.rustam.e_commerce.mapper;


import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.User;
import com.rustam.e_commerce.dto.response.UserCreateResponse;
import com.rustam.e_commerce.dto.response.UserResponse;
import com.rustam.e_commerce.dto.response.UserUpdateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    UserCreateResponse toResponse(User user);

    List<UserResponse> toResponses(List<User> users);

    UserUpdateResponse toUpdated(BaseUser user);

    UserResponse toDto(User user);
}
