package com.project.carsharingapp.dto.rental;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDto {
    private Long id;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private LocalDateTime actualReturnDate;
    private Long carId;
    private Long userId;
}
