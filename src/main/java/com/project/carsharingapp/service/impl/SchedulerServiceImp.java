package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.PaymentRepository;
import com.project.carsharingapp.repository.UserRepository;
import com.project.carsharingapp.service.NotificationService;
import com.project.carsharingapp.service.RentalService;
import com.project.carsharingapp.service.SchedulerService;
import com.project.carsharingapp.service.UserService;
import com.project.carsharingapp.service.payment.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImp implements SchedulerService {
    private static final String OVERDUE_RENTAL_MESSAGE = "Your rental is expected to be "
                                                            + "overdue! Rental id: ";
    private final RentalService rentalService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final NotificationService notificationService;

    @Override
    @Scheduled(cron = "0/1 0 * * * MON-FRI")
    public void checkOverdueRentals() {
        rentalService.getAllOverdueRentals()
                .forEach(
                        (rentalDto) -> {
                            User user = userRepository.findUserById(rentalDto.getUserId()).get();
                            notificationService.sendMessage(user.getTelegramChatId(),
                                                    OVERDUE_RENTAL_MESSAGE + rentalDto.getId());
                        }
                );
    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void checkExpiredPaymentSessions() {
        List<Payment> allExpiredPayments = paymentService.getAllExpiredPayments();
        allExpiredPayments.forEach(payment -> payment.setStatus(Payment.Status.EXPIRED));
        paymentRepository.saveAll(allExpiredPayments);
    }
}
