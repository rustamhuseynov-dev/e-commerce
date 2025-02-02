package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.OrderItem;
import com.rustam.e_commerce.dao.repository.OrderItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class OrderItemService {

    OrderItemRepository orderItemRepository;

    public void save(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}
