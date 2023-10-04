package com.project.carsharingapp.repository.rentals.spec;

import static com.project.carsharingapp.repository.rentals.RentalSpecificationBuilder.IS_ACTIVE_KEY;

import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ActiveStatusSpecificationProvider implements SpecificationProvider<Rental> {
    @Override
    public String getKey() {
        return IS_ACTIVE_KEY;
    }

    public Specification<Rental> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(IS_ACTIVE_KEY)
                .in(Arrays.stream(params).toArray());
    }
}
