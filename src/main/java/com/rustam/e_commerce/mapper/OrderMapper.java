package com.rustam.e_commerce.mapper;

import com.rustam.e_commerce.dao.entity.Order;
import com.rustam.e_commerce.dto.response.OrderCreateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface OrderMapper {
    OrderCreateResponse toDto(Order order);
}
