package com.rustam.e_commerce.mapper;

import com.rustam.e_commerce.dao.entity.user.Admin;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dto.response.AdminCreateResponse;
import com.rustam.e_commerce.dto.response.AdminResponse;
import com.rustam.e_commerce.dto.response.AdminUpdateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface AdminMapper {
    AdminCreateResponse toResponse(Admin admin);

    AdminUpdateResponse toUpdated(BaseUser user);

    AdminResponse toDto(Admin user);
}
