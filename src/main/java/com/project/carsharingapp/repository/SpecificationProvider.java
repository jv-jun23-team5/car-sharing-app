package com.project.carsharingapp.repository;

import com.project.carsharingapp.model.Rental;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    Specification<Rental> getSpecification(String[] params);
}
