package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.CartRequest;
import com.rustam.e_commerce.dto.request.CartUpdateRequest;
import com.rustam.e_commerce.dto.response.CartDeleteResponse;
import com.rustam.e_commerce.dto.response.CartReadResponse;
import com.rustam.e_commerce.dto.response.CartResponse;
import com.rustam.e_commerce.dto.response.CartUpdateResponse;
import com.rustam.e_commerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(path = "/add-to-cart")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cartRequest){
        return new ResponseEntity<>(cartService.addToCart(cartRequest), HttpStatus.OK);
    }

    @GetMapping(path = "/readAll")
    public ResponseEntity<List<CartReadResponse>> findAll(){
        return new ResponseEntity<>(cartService.findAll(),HttpStatus.OK);
    }

    @GetMapping(path = "/readById/{id}")
    public ResponseEntity<CartReadResponse> readById(@PathVariable Long id){
        return new ResponseEntity<>(cartService.readById(id),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CartUpdateResponse> update(@RequestBody CartUpdateRequest cartUpdateRequest){
        return new ResponseEntity<>(cartService.updateCartItem(cartUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<CartDeleteResponse> delete(@PathVariable Long id){
        return new ResponseEntity<>(cartService.delete(id),HttpStatus.OK);
    }
}
