package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.model.Payment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PaymentService {
    Payment create(Authentication authentication, CreatePaymentSessionRequestDto requestDto);

    Payment updateStatus(String sessionId, Payment.Status status);

    List<Payment> getAll(Authentication authentication, Pageable pageable);
}
