package com.project.carsharingapp.dto.rental;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRentalRequestDto {
    @NotNull
    private LocalDateTime rentalDate;
    @NotNull
    private LocalDateTime returnDate;
    @NotNull
    private LocalDateTime actualReturnDate;
    @NotNull
    private Long carId;
    @NotNull
    private Long userId;
}
