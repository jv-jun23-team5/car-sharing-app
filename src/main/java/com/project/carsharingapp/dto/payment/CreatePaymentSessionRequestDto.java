package com.project.carsharingapp.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentSessionRequestDto {
    @NotNull
    private String type;
}
