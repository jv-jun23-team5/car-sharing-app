package com.project.carsharingapp.repository;

import com.project.carsharingapp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
