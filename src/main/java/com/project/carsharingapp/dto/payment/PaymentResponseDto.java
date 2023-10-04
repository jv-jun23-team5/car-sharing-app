package com.project.carsharingapp.dto.payment;

import com.project.carsharingapp.model.Payment;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentResponseDto {
    private Long id;
    private Payment.Status status;
    private Payment.Type type;
    private String rental_id;
    private String sessionUrl;
    private String sessionId;
    private BigDecimal amount;
}
