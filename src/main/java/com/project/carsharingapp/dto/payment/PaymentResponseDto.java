package com.project.carsharingapp.dto.payment;

import com.project.carsharingapp.model.Payment;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private Long id;
    private Payment.Status status;
    private Payment.Type type;
    private String rentalId;
    private String sessionUrl;
    private String sessionId;
    private BigDecimal amount;
}
