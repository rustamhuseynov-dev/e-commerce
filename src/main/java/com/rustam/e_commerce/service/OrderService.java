package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.*;
import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import com.rustam.e_commerce.dao.repository.OrderRepository;
import com.rustam.e_commerce.dto.request.OrderCreateRequest;
import com.rustam.e_commerce.dto.response.OrderCreateResponse;
import com.rustam.e_commerce.mapper.OrderMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    UtilService utilService;
    OrderMapper orderMapper;

    public OrderCreateResponse create(OrderCreateRequest orderCreateRequest) {
        Cart cart = utilService.findByCartId(orderCreateRequest.getCartId());

        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .orderStatus(OrderStatus.SUCCESSFUL)
                .totalAmount(cart.getTotalPrice())
                .email(orderCreateRequest.getEmail())
                .build();

        orderRepository.save(order);

        List<CartItem> cartItems = cart.getCartItems();

        List<OrderItem> orderItems = new ArrayList<>();

//        for (CartItem cartItem : cartItems) {
//            OrderItem orderItem = new OrderItem();
//
//            orderItem.setProduct(cartItem.getProduct());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setDiscount(cartItem.getDiscount());
//            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
//            orderItem.setOrder(savedOrder);
//
//            orderItems.add(orderItem);
//        }
//
//        orderItems = orderItemRepo.saveAll(orderItems);
//
//        cart.getCartItems().forEach(item -> {
//            int quantity = item.getQuantity();
//
//            Product product = item.getProduct();
//
//            cartService.deleteProductFromCart(cartId, item.getProduct().getProductId());
//
//            product.setQuantity(product.getQuantity() - quantity);
//        });
//
//        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
//
//        orderItems.forEach(item -> orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));
//
        return orderMapper.toDto(order);
    }
}
