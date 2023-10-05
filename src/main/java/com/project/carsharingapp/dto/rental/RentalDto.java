package com.project.carsharingapp.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
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
