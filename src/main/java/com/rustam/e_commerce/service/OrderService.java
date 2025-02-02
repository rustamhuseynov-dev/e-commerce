package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.*;
import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import com.rustam.e_commerce.dao.repository.CartRepository;
import com.rustam.e_commerce.dao.repository.OrderRepository;
import com.rustam.e_commerce.dto.OrderItemDTO;
import com.rustam.e_commerce.dto.request.OrderCreateRequest;
import com.rustam.e_commerce.dto.response.CreatePaymentResponse;
import com.rustam.e_commerce.dto.response.OrderCreateResponse;
import com.rustam.e_commerce.mapper.OrderMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    UtilService utilService;
    OrderMapper orderMapper;
    PaymentService paymentService;
    CartService cartService;
    ModelMapper modelMapper;
    OrderItemService orderItemService;
    CartRepository cartRepository;

    @Transactional
    public OrderCreateResponse create(OrderCreateRequest orderCreateRequest) {
        Cart cart = utilService.findByCartIdAndUserId(orderCreateRequest.getCartId(), orderCreateRequest.getUserId());

        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .orderStatus(OrderStatus.SUCCESSFUL)
                .totalAmount(cart.getTotalPrice())
                .userId(orderCreateRequest.getUserId())
                .build();

        Payment payment = paymentService.createPayment(order, orderCreateRequest.getPaymentMethod());
        order.setPayment(payment);
        orderRepository.save(order);

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> createOrderItem(order, cartItem))
                .toList();

        orderItemService.save(orderItems);

        cart.getCartItems().forEach(item -> {
            Product product = utilService.findByProductId(item.getProduct());
            utilService.updateProductQuantity(product, item.getQuantity());
        });
        cartService.delete(orderCreateRequest.getCartId());

        OrderCreateResponse orderCreateResponse = modelMapper.map(order, OrderCreateResponse.class);
        orderCreateResponse.setOrderItems(new ArrayList<>());
        orderItems.forEach(item -> orderCreateResponse.getOrderItems().add(modelMapper.map(item, OrderItem.class)));
        orderCreateRequest.setCartId(orderCreateRequest.getCartId());
        orderCreateResponse.setTotalAmount(cart.getTotalPrice());
        return orderCreateResponse;
    }

    private OrderItem createOrderItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setOrderedProductPrice(cartItem.getPrice());
        orderItem.setOrder(order);
        return orderItem;
    }
}
