package com.project.carsharingapp.repository;

import com.project.carsharingapp.model.Rental;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("FROM Rental r LEFT JOIN FETCH r.car "
            + "LEFT JOIN FETCH r.user WHERE r.id = :id")
    Optional<Rental> findById(Long id);
}
