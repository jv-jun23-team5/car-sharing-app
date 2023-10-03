package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment controller", description = "Endpoint for managing payments")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    @Operation(summary = "Get all user's payments")
    public List<Payment> getAll(Pageable pageable) {
        return paymentService.getAll(pageable);
    }

    @PostMapping
    @Operation(summary = "Create a new payment session")
    public ResponseEntity<Object> create(
            @RequestBody @Valid CreatePaymentSessionRequestDto requestDto
    ) {
        Payment payment = paymentService.create(requestDto);
        URI url = URI.create(payment.getSessionUrl());
        return ResponseEntity.status(HttpStatus.FOUND).location(url).build();
    }

    @GetMapping("/success")
    @Operation(summary = "Redirect endpoint in case of successful payment")
    private String redirectToSuccessPage(@RequestParam String sessionId) {
        paymentService.updateStatus(sessionId);
        return "success";
    }

    @GetMapping("/cancel")
    @Operation(summary = "Redirect endpoint in case of paused payment")
    private String redirectToFailedPage() {
        return "cancel";
    }
}
