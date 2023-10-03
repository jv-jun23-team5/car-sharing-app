package com.project.carsharingapp.dto.payment;

import com.project.carsharingapp.model.Payment;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreatePaymentSessionRequestDto {
    @NotNull
    private Long rentalId;
    @NotNull
    private String type;
}
