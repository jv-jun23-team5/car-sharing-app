package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.PaymentService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public List<PaymentResponseDto> getAll() {
        return List.of();
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestBody @Valid CreatePaymentSessionRequestDto requestDto
    ) {
        Payment payment = paymentService.create(requestDto);
        URI url = URI.create(payment.getSessionUrl());
        return ResponseEntity.status(HttpStatus.FOUND).location(url).build();
    }

    @GetMapping("/success")
    private String redirectToSuccessPage(@RequestParam String sessionId) {
        paymentService.updateStatus(sessionId);
        return "success";
    }

    @GetMapping("/cancel")
    private String redirectToFailedPage() {
        return "cancel";
    }
}
