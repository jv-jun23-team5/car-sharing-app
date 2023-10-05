package com.project.carsharingapp.repository;

import com.project.carsharingapp.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("FROM Payment p LEFT JOIN FETCH p.rental")
    Page<Payment> findAll(Pageable pageable);

    @Query("FROM Payment p LEFT JOIN FETCH p.rental")
    List<Payment> findAll();

    Optional<Payment> findBySessionId(String sessionId);

    Optional<Payment> findByRentalId(Long rentalId);

    boolean existsByRentalIdAndStatus(Long rentalId, Payment.Status status);
}
