package com.project.carsharingapp.service.rental;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.dto.rental.RentalSearchParametersDto;
import com.project.carsharingapp.dto.rental.SetActualReturnDateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RentalService {
    RentalDto add(CreateRentalRequestDto requestDto);

    //    List<RentalDto> getByUserIdAndActiveStatus(Pageable pageable, RentalSearchParametersDto params);
    List<RentalDto> getByUserIdAndActiveStatus(Long userId, boolean isActive);

    RentalDto getById(Long id);

    RentalDto setActualReturnDay(Long id);
}
