package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Cart;
import com.rustam.e_commerce.dao.entity.CartItem;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.repository.CartRepository;
import com.rustam.e_commerce.dto.request.CartRequest;
import com.rustam.e_commerce.dto.request.CartUpdateRequest;
import com.rustam.e_commerce.dto.response.CartDeleteResponse;
import com.rustam.e_commerce.dto.response.CartReadResponse;
import com.rustam.e_commerce.dto.response.CartResponse;
import com.rustam.e_commerce.dto.response.CartUpdateResponse;
import com.rustam.e_commerce.exception.custom.NotManyProductsException;
import com.rustam.e_commerce.mapper.CartMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {

    CartRepository cartRepository;
    UtilService utilService;
    CartMapper cartMapper;
    ModelMapper modelMapper;

    @Transactional
    public CartResponse addToCart(CartRequest cartRequest) {
        BaseUser user = utilService.findById(cartRequest.getUserId());
        Cart cart = getOrCreateCart(user);

        Product product = utilService.findByProductId(cartRequest.getProductId());
        validateProductStock(product, cartRequest.getQuantity());

        CartItem cartItem = getOrCreateCartItem(cart, product);
        updateCartItemQuantity(cartItem, cartRequest.getQuantity());

        updateCartTotalPrice(cart);
        cartRepository.save(cart);

        return buildCartResponse(cart, cartRequest.getUserId());
    }


    private void updateCartTotalPrice(Cart cart) {
        cart.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum());
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

    private Cart getOrCreateCart(BaseUser user) {
        return cartRepository.findByUser(user.getId())
                .orElseGet(() -> Cart.builder()
                        .user(user.getId())
                        .cartItems(new ArrayList<>())
                        .totalPrice(0)
                        .build());
    }

    private void validateProductStock(Product product, int requestedQuantity) {
        if (product.getQuantity() < requestedQuantity) {
            throw new NotManyProductsException("Not many products");
        }
    }

    private CartItem getOrCreateCartItem(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .product(product)
                            .quantity(0)
                            .cart(cart)
                            .price(product.getPrice())
                            .productName(product.getProductName())
                            .build();
                    cart.getCartItems().add(newItem);
                    return newItem;
                });
    }

    private void updateCartItemQuantity(CartItem item, int quantityToAdd) {
        item.setQuantity(item.getQuantity() + quantityToAdd);
        item.calculateTotalPrice();
    }

    private CartResponse buildCartResponse(Cart cart, UUID userId) {
        return CartResponse.builder()
                .items(cart.getCartItems())
                .userId(userId)
                .totalPrice(cart.getTotalPrice())
                .message("Product added successfully!")
                .build();
    }

}
