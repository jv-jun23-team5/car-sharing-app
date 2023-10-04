package com.project.carsharingapp.dto.rental;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRentalRequestDto {
    private LocalDateTime rentalDate;
    @NotNull
    private LocalDateTime returnDate;
    @NotNull
    private Long carId;

}
