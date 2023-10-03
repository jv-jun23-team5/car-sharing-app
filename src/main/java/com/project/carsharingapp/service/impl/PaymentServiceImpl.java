package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.repository.RentalRepository;
import com.project.carsharingapp.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final RentalRepository rentalRepository;
    private final StripeService stripeService;

    @Override
    public Payment create(CreatePaymentSessionRequestDto requestDto) {
        Rental rental = rentalRepository.getReferenceById(requestDto.getRentalId());
        try {
            Session session = stripeService.createSession(rental, Payment.Type.valueOf(requestDto.getType()));
            return generatePayment(session, rental);
        } catch (StripeException e) {
            throw new RuntimeException("Can't create a stripe checkout session!");
        }
    }

    private Payment generatePayment(Session session, Rental rental) {
        Payment payment = new Payment();
        payment.setStatus(Payment.Status.PENDING);
        payment.setRental(rental);
        payment.setSessionUrl(session.getUrl());
        payment.setSessionId(session.getId());
        return payment;
    }
}
