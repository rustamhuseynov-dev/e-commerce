package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Order;
import com.rustam.e_commerce.dao.entity.Payment;
import com.rustam.e_commerce.dao.repository.PaymentRepository;
import com.rustam.e_commerce.dto.response.CreatePaymentResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PaymentService {

    PaymentRepository paymentRepository;

    public Payment createPayment(Order order, String paymentMethod) {
        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(paymentMethod)
                .build();
        paymentRepository.save(payment);
        return payment;
    }
}
