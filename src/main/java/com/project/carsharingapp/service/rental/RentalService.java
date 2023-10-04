package com.project.carsharingapp.service.rental;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import java.util.List;

public interface RentalService {
    RentalDto add(CreateRentalRequestDto requestDto);

    List<RentalDto> getByUserIdAndActiveStatus(Long userId, boolean isActive);

    RentalDto getById(Long id);

    RentalDto setActualReturnDay(Long id);
}
