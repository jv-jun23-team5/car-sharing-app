package com.project.carsharingapp.service;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.model.Rental;
import java.util.List;

import com.project.carsharingapp.model.User;
import org.springframework.security.core.Authentication;

public interface RentalService {
    RentalDto add(CreateRentalRequestDto requestDto, Authentication authentication);

    List<RentalDto> getAllByUserIdAndActiveStatus(Long userId, boolean isActive);

    Rental getByUserIdAndActiveStatus(Long userId, boolean isActive);

    Rental getByUserAndId(User user, Long id);

    RentalDto getById(Long id);

    RentalDto setActualReturnDay(Authentication authentication);
}
