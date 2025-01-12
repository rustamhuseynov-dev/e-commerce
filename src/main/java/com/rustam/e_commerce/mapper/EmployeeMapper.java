package com.rustam.e_commerce.mapper;

import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.Employee;
import com.rustam.e_commerce.dto.response.EmployeeCreateResponse;
import com.rustam.e_commerce.dto.response.EmployeeResponse;
import com.rustam.e_commerce.dto.response.EmployeeUpdateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface EmployeeMapper {
    EmployeeCreateResponse toResponse(Employee employee);

    EmployeeResponse toRead(BaseUser user);

    EmployeeUpdateResponse toUpdated(Employee employee);
}
