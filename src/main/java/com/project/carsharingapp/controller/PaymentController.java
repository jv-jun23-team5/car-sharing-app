package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment management", description = "Endpoint for managing payments")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private static final String SUCCESS_ENDPOINT_MESSAGE = "THE PAYMENT IS SUCCESSFUL!";
    private static final String CANCEL_ENDPOINT_MESSAGE = "THE SESSION WILL BE EXPIRED IN 24 HOURS";

    private final PaymentService paymentService;

    @GetMapping
    @Operation(summary = "Get all user's payments")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public List<PaymentResponseDto> getAll(Authentication authentication, Pageable pageable) {
        return paymentService.getAll(authentication, pageable);
    }

    @PostMapping
    @Operation(summary = "Create a new payment session")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public PaymentResponseDto create(
            Authentication authentication,
            @RequestBody @Valid CreatePaymentSessionRequestDto requestDto
    ) {
        return paymentService.create(authentication, requestDto);
    }

    @GetMapping("/success")
    @Operation(summary = "Redirect endpoint in case of successful payment")
    public String redirectToSuccessPage(@RequestParam String sessionId) {

        paymentService.updateStatus(sessionId, Payment.Status.PAID);
        return SUCCESS_ENDPOINT_MESSAGE;
    }

    @GetMapping("/cancel")
    @Operation(summary = "Redirect endpoint in case of paused payment")
    public String redirectToFailedPage(@RequestParam String sessionId) {
        paymentService.updateStatus(sessionId, Payment.Status.PAUSED);
        return CANCEL_ENDPOINT_MESSAGE;
    }
}
