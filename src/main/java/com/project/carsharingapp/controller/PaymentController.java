package com.project.carsharingapp.controller;

import com.project.carsharingapp.dto.payment.CreatePaymentSessionRequestDto;
import com.project.carsharingapp.dto.payment.PaymentResponseDto;
import java.util.List;
import javax.validation.Valid;

import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Payment create(
            @RequestBody @Valid CreatePaymentSessionRequestDto requestDto
    ) {
        return paymentService.create(requestDto);
    }

    @GetMapping("/success")
    private String redirectToSuccessPage() {
        return "success";
    }

    @GetMapping("/cancel")
    private String redirectToFailedPage() {
        return "cancel";
    }
}
