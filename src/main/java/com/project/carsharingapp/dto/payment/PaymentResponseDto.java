package com.project.carsharingapp.dto.payment;

import com.project.carsharingapp.model.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private Long id;
    @Schema(example = "PAID")
    private Payment.Status status;
    @Schema(example = "PAYMENT")
    private Payment.Type type;
    @Schema(example = "1")
    private String rentalId;
    @Schema(example = "rental_url")
    private String sessionUrl;
    @Schema(example = "rental_id")
    private String sessionId;
    private BigDecimal amount;
}
