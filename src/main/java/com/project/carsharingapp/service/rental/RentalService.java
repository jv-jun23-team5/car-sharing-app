package com.project.carsharingapp.service.rental;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.dto.rental.RentalSearchParametersDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface RentalService {
    RentalDto add(CreateRentalRequestDto requestDto, Authentication authentication);

        List<RentalDto> getByUserIdAndActiveStatus(Pageable pageable, RentalSearchParametersDto params);
//    List<RentalDto> getByUserIdAndActiveStatus(Long userId, boolean isActive);

    RentalDto getById(Long id);

    RentalDto setActualReturnDay(Long id);
}
