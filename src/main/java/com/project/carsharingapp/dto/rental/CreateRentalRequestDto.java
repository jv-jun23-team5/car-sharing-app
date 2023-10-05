package com.project.carsharingapp.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRentalRequestDto {
    @Schema(example = "2023-10-06 07:00:00")
    @NotNull
    private LocalDateTime rentalDate;
    @NotNull
    @Schema(example = "2023-10-10 07:00:00")
    private LocalDateTime returnDate;
    @NotNull
    private Long carId;

}
