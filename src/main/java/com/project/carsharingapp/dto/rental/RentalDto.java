package com.project.carsharingapp.dto.rental;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDto {
    private Long id;
    @Schema(example = "2023-10-06 07:00:00")
    private LocalDateTime rentalDate;
    @Schema(example = "2023-10-10 07:00:00")
    private LocalDateTime returnDate;
    @Schema(example = "2023-10-11 07:00:00")
    private LocalDateTime actualReturnDate;
    private Long carId;
    private Long userId;
}
