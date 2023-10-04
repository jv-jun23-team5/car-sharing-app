package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.model.Payment;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Payment create(CreatePaymentSessionRequestDto requestDto);

    Payment updateStatus(String sessionId);

    List<Payment> getAll(Pageable pageable);
}
