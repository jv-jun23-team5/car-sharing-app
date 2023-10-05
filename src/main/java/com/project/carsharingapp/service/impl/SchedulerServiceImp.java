package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.repository.PaymentRepository;
import com.project.carsharingapp.service.RentalService;
import com.project.carsharingapp.service.SchedulerService;
import com.project.carsharingapp.service.payment.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImp implements SchedulerService {
    private final RentalService rentalService;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @Override
    @Scheduled(cron = "0/1 0 * * * MON-FRI")
    public void checkOverdueRentals() {
        List<RentalDto> allOverdueRentals = rentalService.getAllOverdueRentals();
        System.out.println(allOverdueRentals);
    }

    @Override
    @Scheduled(cron = "0/1 0 * * * *")
    public void checkExpiredPaymentSessions() {
        List<Payment> allExpiredPayments = paymentService.getAllExpiredPayments();
        allExpiredPayments.forEach(payment -> payment.setStatus(Payment.Status.EXPIRED));
        paymentRepository.saveAll(allExpiredPayments);
    }
}
