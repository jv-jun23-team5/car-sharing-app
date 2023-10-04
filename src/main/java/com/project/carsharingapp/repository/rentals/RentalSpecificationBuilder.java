package com.project.carsharingapp.repository.rentals;

import com.project.carsharingapp.dto.rental.RentalSearchParametersDto;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.repository.SpecificationBuilder;
import com.project.carsharingapp.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RentalSpecificationBuilder implements SpecificationBuilder<Rental> {
    public static final String USER_ID_KEY = "userId";
    public static final String IS_ACTIVE_KEY = "isActive";

    private final SpecificationProviderManager<Rental> rentalSpecificationProviderManager;

    @Override
    public Specification<Rental> build(RentalSearchParametersDto searchParametersDto) {
        Specification<Rental> spec = Specification.where(null);
        spec = getRentalSpecification(searchParametersDto.userId(), spec, USER_ID_KEY);
        spec = getRentalSpecification(searchParametersDto.isActive(), spec, IS_ACTIVE_KEY);
        return spec;
    }

    private Specification<Rental> getRentalSpecification(String[] searchParametersDto,
                                                     Specification<Rental> spec,
                                                     String key) {
        if (searchParametersDto != null && searchParametersDto.length > 0) {
            spec = spec.and(rentalSpecificationProviderManager.getSpecificationProvider(key)
                    .getSpecification(searchParametersDto));
        }
        return spec;
    }
}
