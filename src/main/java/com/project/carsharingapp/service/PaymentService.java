package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.model.Payment;

public interface PaymentService {
    Payment create(CreatePaymentSessionRequestDto requestDto);

    Payment updateStatus(String sessionId);

}
