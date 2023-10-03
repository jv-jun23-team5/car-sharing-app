package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
