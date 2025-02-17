package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.AddToFavoriteRequest;
import com.rustam.e_commerce.dto.request.ReadFavoritesRequest;
import com.rustam.e_commerce.dto.response.AddToFavoriteResponse;
import com.rustam.e_commerce.dto.response.DeleteFavoritesResponse;
import com.rustam.e_commerce.dto.response.ReadFavoritesResponse;
import com.rustam.e_commerce.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping(path = "/add-to-favorites")
    public ResponseEntity<AddToFavoriteResponse> addToFavorite(@RequestBody AddToFavoriteRequest addToFavoriteRequest){
        return new ResponseEntity<>(favoriteService.addToFavorite(addToFavoriteRequest), HttpStatus.OK);
    }

    @GetMapping(path = "/read-favorites")
    public ResponseEntity<List<ReadFavoritesResponse>> readFavorites(@RequestBody ReadFavoritesRequest readFavoritesRequest){
        return new ResponseEntity<>(favoriteService.readFavorites(readFavoritesRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete-favorites/{id}")
    public ResponseEntity<DeleteFavoritesResponse> deleteFavorites(@PathVariable Long id){
        return new ResponseEntity<>(favoriteService.deleteFavorites(id),HttpStatus.OK);
    }
}
