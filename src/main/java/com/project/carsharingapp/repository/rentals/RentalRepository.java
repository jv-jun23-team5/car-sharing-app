package com.project.carsharingapp.repository.rentals;

import com.project.carsharingapp.model.Rental;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>,
        JpaSpecificationExecutor<Rental> {

    @Query("FROM Rental r WHERE r.user.id = :userId OR r.isActive = :isActive")
    List<Rental> findRentalsByUserIdAndActiveStatus(Long userId, boolean isActive);

//    @Query("FROM Rental r WHERE r.user.id = :userId AND r.isActive = :isActive")
//    Optional<Rental>findRentalByUserIdAndActiveStatus(Long userId, boolean isActive);


}
