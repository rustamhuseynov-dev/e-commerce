package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Cart;
import com.rustam.e_commerce.dao.entity.CartItem;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.repository.CartRepository;
import com.rustam.e_commerce.dto.request.CartRequest;
import com.rustam.e_commerce.dto.request.CartUpdateRequest;
import com.rustam.e_commerce.dto.response.CartDeleteResponse;
import com.rustam.e_commerce.dto.response.CartReadResponse;
import com.rustam.e_commerce.dto.response.CartResponse;
import com.rustam.e_commerce.dto.response.CartUpdateResponse;
import com.rustam.e_commerce.mapper.CartMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {

    CartRepository cartRepository;
    UtilService utilService;
    CartMapper cartMapper;
    ModelMapper modelMapper;

    public CartResponse addToCart(CartRequest cartRequest) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(cartRequest.getUserId());
        Cart cart = new Cart();
        if (cartOptional.isEmpty()){
            cart.setUser(cartRequest.getUserId());
        }
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(cartRequest.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null){
            existingItem.setQuantity(existingItem.getQuantity() + cartRequest.getQuantity());
            existingItem.calculateTotalPrice();
        }else {
            CartItem newItem = new CartItem();
            newItem.setProduct(cartRequest.getProductId());
            newItem.setQuantity(cartRequest.getQuantity());
            Product product = utilService.findByProductId(cartRequest.getProductId());
            newItem.setCart(cartOptional.get());
            newItem.setPrice(product.getPrice());
            newItem.setProductName(product.getProductName());

            cart.getCartItems().add(newItem);
        }
        cart.calculateTotalPrice();
        cartRepository.save(cart);
        CartResponse.builder().message("Product added successfully!").build();
        return cartMapper.toCart(cart);

    }

    public List<CartReadResponse> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return cartMapper.toResponses(carts);
    }

    public CartReadResponse readById(Long id) {
        Cart cart = utilService.findByCartId(id);
        return cartMapper.toRead(cart);
    }

    public CartUpdateResponse updateCartItem(CartUpdateRequest cartUpdateRequest) {
        Cart cart = utilService.findByCartId(cartUpdateRequest.getId());
        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(cartUpdateRequest.getProductId()))
                .findFirst()
                .ifPresent(cartItem -> {
                    cartItem.setQuantity(cartItem.getQuantity());
                    cartItem.calculateTotalPrice();
                    cart.getTotalPrice();
                });
        cartRepository.save(cart);
        return cartMapper.toUpdated(cart);
    }

    public CartDeleteResponse delete(Long id) {
        Cart cart = utilService.findByCartId(id);
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(String.valueOf(cart.getUser()),currentUsername);
        CartDeleteResponse cartDeleteResponse = new CartDeleteResponse();
        modelMapper.map(cart,cartDeleteResponse);
        cartDeleteResponse.setMessage("This cart was deleted by you");
        cartRepository.delete(cart);
        return cartDeleteResponse;
    }
}
