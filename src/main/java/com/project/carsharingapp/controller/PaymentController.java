package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping
    public List<PaymentResponseDto> getAll() {
        return List.of();
    }

    @PostMapping
    public PaymentResponseDto create(
            @RequestBody @Valid CreatePaymentSessionRequestDto requestDto
    ) {
        return new PaymentResponseDto();
    }

    @GetMapping("/success")
    private void redirectToSuccessPage() {

    }

    @GetMapping("/cancel")
    private void redirectToFailedPage() {

    }
}
