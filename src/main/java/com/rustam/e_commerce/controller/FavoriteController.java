package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.AddToFavoriteRequest;
import com.rustam.e_commerce.dto.response.AddToFavoriteResponse;
import com.rustam.e_commerce.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping(path = "add-to-favorites")
    public ResponseEntity<AddToFavoriteResponse> addToFavorite(@RequestBody AddToFavoriteRequest addToFavoriteRequest){
        return new ResponseEntity<>(favoriteService.addToFavorite(addToFavoriteRequest), HttpStatus.OK);
    }
}
