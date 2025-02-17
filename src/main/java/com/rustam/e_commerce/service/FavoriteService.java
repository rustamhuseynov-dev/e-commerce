package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.entity.Favorite;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.repository.FavoriteRepository;
import com.rustam.e_commerce.dto.request.AddToFavoriteRequest;
import com.rustam.e_commerce.dto.request.ReadFavoritesRequest;
import com.rustam.e_commerce.dto.response.AddToFavoriteResponse;
import com.rustam.e_commerce.dto.response.ReadFavoritesResponse;
import com.rustam.e_commerce.mapper.FavoriteMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FavoriteService {

    FavoriteRepository favoriteRepository;
    UtilService utilService;
    FavoriteMapper favoriteMapper;
    public AddToFavoriteResponse addToFavorite(AddToFavoriteRequest addToFavoriteRequest) {
        String userId = utilService.getCurrentUsername();
        Product product = utilService.findByProductId(addToFavoriteRequest.getProductId());
        Category category = utilService.findByCategoryId(product.getCategoryId());
        Favorite favorite = Favorite.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .userId(userId)
                .categoryId(product.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
        favoriteRepository.save(favorite);
        return favoriteMapper.toDto(favorite);
    }

    public List<ReadFavoritesResponse> readFavorites(ReadFavoritesRequest readFavoritesRequest) {
        List<Favorite> userFavorites = favoriteRepository.findAllByUserId(readFavoritesRequest.getUserId());
        return favoriteMapper.toDtos(userFavorites);
    }
}
