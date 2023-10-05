package com.project.carsharingapp.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentSessionRequestDto {
    @NotNull
    private Long rentalId;
    @Schema(example = "PAYMENT | FINE")
    @NotNull
    private String type;
}
