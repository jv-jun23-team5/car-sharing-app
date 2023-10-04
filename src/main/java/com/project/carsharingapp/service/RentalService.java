package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface RentalService {
    RentalDto add(CreateRentalRequestDto requestDto, Authentication authentication);

    List<RentalDto> getByUserIdAndActiveStatus(Long userId, boolean isActive);

    RentalDto getById(Long id);

    RentalDto setActualReturnDay(Authentication authentication);
}
