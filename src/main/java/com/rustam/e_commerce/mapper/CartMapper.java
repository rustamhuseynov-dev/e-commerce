package com.rustam.e_commerce.mapper;

import com.rustam.e_commerce.dao.entity.Cart;
import com.rustam.e_commerce.dto.response.CartReadResponse;
import com.rustam.e_commerce.dto.response.CartResponse;
import com.rustam.e_commerce.dto.response.CartUpdateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CartMapper {

    CartResponse toCart(Cart cart);

    CartReadResponse toRead(Cart cart);

    CartUpdateResponse toUpdated(Cart cart);

    List<CartReadResponse> toResponses(List<Cart> carts);
}
