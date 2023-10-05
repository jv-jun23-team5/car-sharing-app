package com.project.carsharingapp.repository;

import com.project.carsharingapp.model.Rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>,
        JpaSpecificationExecutor<Rental> {
    @Query("FROM Rental r LEFT JOIN FETCH r.car "
            + "LEFT JOIN FETCH r.user WHERE r.id = :id")
    Optional<Rental> findById(Long id);

    @Query("FROM Rental r LEFT JOIN FETCH r.user "
            + "LEFT JOIN FETCH r.car "
            + " WHERE r.user.id = :userId AND r.isActive = :isActive")
    List<Rental> findAllByUserIdAndActiveStatus(Long userId, boolean isActive);

    @Query("FROM Rental r LEFT JOIN FETCH r.user "
            + "LEFT JOIN FETCH r.car "
            + " WHERE r.user.id = :userId AND r.isActive = :isActive")
    Optional<Rental> findByUserIdAndActiveStatus(Long userId, boolean isActive);

    @Query("FROM Rental r LEFT JOIN FETCH r.user "
            + "LEFT JOIN FETCH r.car "
            + " WHERE r.user.id = :userId AND r.id = :id")
    Optional<Rental> findByUserIdAndId(Long userId, Long id);
}
