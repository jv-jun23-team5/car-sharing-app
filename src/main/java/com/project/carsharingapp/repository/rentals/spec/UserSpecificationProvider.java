package com.project.carsharingapp.repository.rentals.spec;

import static com.project.carsharingapp.repository.rentals.RentalSpecificationBuilder.USER_ID_KEY;

import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationProvider implements SpecificationProvider<Rental> {
    @Override
    public String getKey() {
        return USER_ID_KEY;
    }

    public Specification<Rental> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(USER_ID_KEY)
                .in(Arrays.stream(params).toArray());
    }
}
