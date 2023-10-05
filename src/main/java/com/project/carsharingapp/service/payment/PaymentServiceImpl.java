package com.project.carsharingapp.service.payment;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.exception.NotValidPaymentProcessException;
import com.project.carsharingapp.mapper.PaymentMapper;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.model.Role;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.PaymentRepository;
import com.project.carsharingapp.service.NotificationService;
import com.project.carsharingapp.service.RentalService;
import com.project.carsharingapp.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final Long CONVERTING_TO_USD_VALUE = 100L;

    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;
    private final UserService userService;
    private final RentalService rentalService;
    private final NotificationService notificationService;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDto create(Authentication authentication,
                                     CreatePaymentSessionRequestDto requestDto) {
        Rental rental = getUserRentalById(authentication, requestDto.getRentalId());
        Payment.Type type = Payment.Type.valueOf(requestDto.getType());

        checkIfPaymentSessionValid(rental);

        if (paymentRepository.existsByRentalIdAndStatus(rental.getId(), Payment.Status.PAUSED)) {
            return paymentMapper.toDto(paymentRepository.findByRentalId(rental.getId()).get());
        }

        try {
            Session session = stripeService.createSession(rental, type);
            Payment payment = generatePayment(session, rental, type);
            return paymentMapper.toDto(paymentRepository.save(payment));
        } catch (StripeException e) {
            throw new RuntimeException("Can't create a stripe checkout session!");
        }
    }

    @Override
    public PaymentResponseDto updateStatus(String sessionId, Payment.Status status) {
        Payment payment = paymentRepository.findBySessionId(sessionId).orElseThrow(
                () -> new EntityNotFoundException("Can't find a Payment by the session")
        );
        payment.setStatus(status);
        User user = userService.getUserByRentalId(payment.getRental().getId());
        notificationService.sendMessage(user.getTelegramChatId(), "The payment is " + status);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponseDto> getAll(Authentication authentication, Pageable pageable) {
        User user = userService.getByAuthentication(authentication);

        if (isUserCustomer(user)) {
            return paymentRepository.findAll(pageable).stream()
                    .filter(payment -> payment.getRental().getUser().getId().equals(user.getId()))
                    .map(paymentMapper::toDto)
                    .collect(Collectors.toList());
        }
        return paymentRepository.findAll(pageable).stream().map(paymentMapper::toDto).toList();
    }

    @Override
    public List<Payment> getAllExpiredPayments() {
        return paymentRepository.findAll().stream()
                .filter(payment -> !payment.getStatus().equals(Payment.Status.PAID)
                                    && payment.getExpiredTime().isBefore(Instant.now()))
                .toList();
    }

    private Payment generatePayment(Session session, Rental rental, Payment.Type type) {
        Payment payment = new Payment();
        payment.setStatus(Payment.Status.PENDING);
        payment.setType(type);
        payment.setRental(rental);
        payment.setSessionUrl(session.getUrl());
        payment.setSessionId(session.getId());
        payment.setAmount(BigDecimal.valueOf(session.getAmountTotal() / CONVERTING_TO_USD_VALUE));
        payment.setExpiredTime(Instant.ofEpochSecond(session.getExpiresAt()));
        return payment;
    }

    private Rental getUserRentalById(Authentication authentication, Long id) {
        User user = userService.getByAuthentication(authentication);
        return rentalService.getByUserAndId(user, id);
    }

    private void checkIfPaymentSessionValid(Rental rental) {
        if (paymentRepository.existsByRentalIdAndStatus(rental.getId(), Payment.Status.PAID)) {
            throw new NotValidPaymentProcessException("The rental is paid!");
        }
        if (paymentRepository.existsByRentalIdAndStatus(rental.getId(), Payment.Status.EXPIRED)) {
            throw new NotValidPaymentProcessException("The payment session is expired!");
        }
    }

    private boolean isUserCustomer(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .anyMatch(roleName -> roleName.equals(Role.RoleName.ROLE_CUSTOMER));
    }
}
