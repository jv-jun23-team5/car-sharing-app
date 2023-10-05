package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.model.User;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface RentalService {
    RentalDto add(CreateRentalRequestDto requestDto, Authentication authentication);

    List<RentalDto> getAllByUserIdAndActiveStatus(Long userId, boolean isActive);

    Rental getByUserIdAndActiveStatus(Long userId, boolean isActive);

    List<Rental> getAllByUserId(Long userId);

    Rental getByUserAndId(User user, Long id);

    RentalDto getById(Long id);

    RentalDto setActualReturnDay(Authentication authentication);

    List<RentalDto> getAllOverdueRentals();
}
