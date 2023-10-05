package com.project.carsharingapp.service.payment;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import com.project.carsharingapp.model.Payment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PaymentService {
    PaymentResponseDto create(Authentication authentication,
                              CreatePaymentSessionRequestDto requestDto);

    PaymentResponseDto updateStatus(String sessionId, Payment.Status status);

    List<PaymentResponseDto> getAll(Authentication authentication, Pageable pageable);
}
