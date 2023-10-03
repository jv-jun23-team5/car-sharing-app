package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.repository.PaymentRepository;
import com.project.carsharingapp.repository.RentalRepository;
import com.project.carsharingapp.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final RentalRepository rentalRepository;
    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;

    @Override
    public Payment create(CreatePaymentSessionRequestDto requestDto) {
        Rental rental = rentalRepository.findById(requestDto.getRentalId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find a Rental by id: "
                                                    + requestDto.getRentalId())
        );
        Payment.Type type = Payment.Type.valueOf(requestDto.getType());
        try {
            Session session = stripeService.createSession(rental, type);
            Payment payment = generatePayment(session, rental, type);
            return paymentRepository.save(payment);
        } catch (StripeException e) {
            throw new RuntimeException("Can't create a stripe checkout session!");
        }
    }

    @Override
    public Payment updateStatus(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId).orElseThrow(
                () -> new EntityNotFoundException("Can't find a Payment by the session")
        );
        payment.setStatus(Payment.Status.PAID);
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAll(Pageable pageable) {
        return paymentRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    private Payment generatePayment(Session session, Rental rental, Payment.Type type) {
        Payment payment = new Payment();
        payment.setStatus(Payment.Status.PENDING);
        payment.setType(type);
        payment.setRental(rental);
        payment.setSessionUrl(session.getUrl());
        payment.setSessionId(session.getId());
        payment.setAmount(BigDecimal.valueOf(session.getAmountTotal()));
        return payment;
    }
}
