package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.*;
import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.repository.CartItemRepository;
import com.rustam.e_commerce.dao.repository.CartRepository;
import com.rustam.e_commerce.dao.repository.OrderRepository;
import com.rustam.e_commerce.dto.OrderItemDTO;
import com.rustam.e_commerce.dto.request.OrderCreateRequest;
import com.rustam.e_commerce.dto.response.CreatePaymentResponse;
import com.rustam.e_commerce.dto.response.OrderCreateResponse;
import com.rustam.e_commerce.dto.response.OrderReadResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    UtilService utilService;
    PaymentService paymentService;
    ModelMapper modelMapper;
    OrderItemService orderItemService;
    EmailSendService emailSendService;

    @Transactional
    public OrderCreateResponse create(OrderCreateRequest orderCreateRequest) {
        BaseUser user = utilService.findById(UUID.fromString(orderCreateRequest.getUserId()));
        Cart cart = utilService.findByCartIdAndUserId(orderCreateRequest.getCartId(), orderCreateRequest.getUserId());
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .orderStatus(OrderStatus.SUCCESSFUL)
                .totalAmount(cart.getTotalPrice())
                .userId(orderCreateRequest.getUserId())
                .email(user.getEmail())
                .build();

        Payment payment = paymentService.createPayment(order, orderCreateRequest.getPaymentMethod());
        order.setPayment(payment);
        orderRepository.save(order);

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> createOrderItem(order, cartItem))
                .toList();

        orderItemService.save(orderItems);

        cart.getCartItems().forEach(item -> {
            Product product = utilService.findByProductId(item.getProduct().getProductId());
            utilService.updateProductQuantity(item.getQuantity(),product);
        });
        utilService.cartClean(orderCreateRequest.getCartId());
        emailSendService.sendOrderConfirmationEmail(order.getEmail());
        OrderCreateResponse orderCreateResponse = modelMapper.map(order, OrderCreateResponse.class);
        orderCreateResponse.setOrderItems(new ArrayList<>());
        orderItems.forEach(item -> orderCreateResponse.getOrderItems().add(modelMapper.map(item, OrderItem.class)));
        orderCreateResponse.setOrderDate(order.getOrderDate());
        orderCreateResponse.setTotalAmount(order.getTotalAmount());
        orderCreateResponse.setPayment(payment);
        orderCreateResponse.setOrderStatus(OrderStatus.SUCCESSFUL);
        orderCreateResponse.setEmail(user.getEmail());
        return orderCreateResponse;
    }

    private OrderItem createOrderItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct().getProductId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setOrderedProductPrice(cartItem.getPrice());
        orderItem.setOrder(order);
        return orderItem;
    }

    public List<OrderReadResponse> read() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> OrderReadResponse.builder()
                        .orderId(order.getOrderId())
                        .orderDate(order.getOrderDate())
                        .orderItems(order.getOrderItems().stream()
                                .map(item -> modelMapper.map(item, OrderItem.class))
                                .collect(Collectors.toList()))
                        .email(order.getEmail())
                        .userId(order.getUserId())
                        .orderStatus(order.getOrderStatus())
                        .totalAmount(order.getTotalAmount())
                        .payment(order.getPayment())
                        .text(order.getEmail() + ": This is a customer order.")
                        .build())
                .collect(Collectors.toList());
    }

}
