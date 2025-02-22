package com.rustam.e_commerce.mapper;

import com.rustam.e_commerce.dao.entity.user.ShipmentTracking;
import com.rustam.e_commerce.dto.response.TrackOrderResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ShipmentTrackingMapper {

    TrackOrderResponse toDto(ShipmentTracking byTrackingNumber);
}
