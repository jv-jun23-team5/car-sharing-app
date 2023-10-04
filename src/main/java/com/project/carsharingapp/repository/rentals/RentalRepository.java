package com.project.carsharingapp.repository.rentals;

import com.project.carsharingapp.model.Rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("FROM Rental r LEFT JOIN FETCH r.car "
            + "LEFT JOIN FETCH r.user WHERE r.id = :id")
    Optional<Rental> findById(Long id);

    @Query("FROM Rental r WHERE r.user.id = :userId AND r.isActive = :isActive")
    List<Rental> findRentalsByUserIdAndActiveStatus(Long userId, boolean isActive);
}
