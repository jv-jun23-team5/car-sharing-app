package com.project.carsharingapp.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentSessionRequestDto {
    @NotNull
    private Long rentalId;
    @NotNull
    private String type;
}
