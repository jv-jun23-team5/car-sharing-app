package com.project.carsharingapp.service;

import com.project.carsharingapp.model.Payment;

import java.math.BigDecimal;

public interface PaymentAmountHandler {
    Long getPaymentAmount(BigDecimal dailyFee, int numberOfDays);

    boolean isApplicable(Payment.Type type);
}
