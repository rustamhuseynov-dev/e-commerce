package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.entity.Favorite;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.repository.FavoriteRepository;
import com.rustam.e_commerce.dto.request.AddToFavoriteRequest;
import com.rustam.e_commerce.dto.request.ReadFavoritesRequest;
import com.rustam.e_commerce.dto.response.AddToFavoriteResponse;
import com.rustam.e_commerce.dto.response.DeleteFavoritesResponse;
import com.rustam.e_commerce.dto.response.DeletedFavorite;
import com.rustam.e_commerce.dto.response.ReadFavoritesResponse;
import com.rustam.e_commerce.exception.custom.ExistsException;
import com.rustam.e_commerce.exception.custom.NoAuthotiryException;
import com.rustam.e_commerce.mapper.FavoriteMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Optional<Favorite> existingFavorite = favoriteRepository.findByProductId(addToFavoriteRequest.getProductId());

        if (existingFavorite.isPresent()) {
            throw new ExistsException("This product has already been added to favorites!");
        }
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
        utilService.findById(UUID.fromString(readFavoritesRequest.getUserId()));
        List<Favorite> userFavorites = favoriteRepository.findAllByUserId(readFavoritesRequest.getUserId());
        boolean anyMatch = userFavorites.stream().anyMatch(fav -> !fav.getUserId().equals(readFavoritesRequest.getUserId()));
        if (anyMatch){
            throw new NoAuthotiryException("This favorites table does not belong to you.");
        }
        return favoriteMapper.toDtos(userFavorites);
    }

    public DeleteFavoritesResponse deleteFavorites(Long id) {
        String currentUsername = utilService.getCurrentUsername();
        Favorite favorite = utilService.findByFavoriteId(id);
        utilService.validation(currentUsername,favorite.getUserId());
        favoriteRepository.delete(favorite);
        DeletedFavorite deleteDto = favoriteMapper.toDeleteDto(favorite);
        return DeleteFavoritesResponse.builder()
                .message("removed from favorites")
                .deletedFavorite(deleteDto)
                .build();
    }
}
