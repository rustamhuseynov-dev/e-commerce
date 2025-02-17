package com.rustam.e_commerce.mapper;

import com.rustam.e_commerce.dao.entity.Favorite;
import com.rustam.e_commerce.dto.response.AddToFavoriteResponse;
import com.rustam.e_commerce.dto.response.ReadFavoritesResponse;
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
public interface FavoriteMapper {
    AddToFavoriteResponse toDto(Favorite favorite);

    List<ReadFavoritesResponse> toDtos(List<Favorite> userFavorites);
}
