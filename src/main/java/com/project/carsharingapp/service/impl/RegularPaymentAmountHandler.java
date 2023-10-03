package com.project.carsharingapp.service.impl;

import java.math.BigDecimal;
import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.PaymentAmountHandler;
import org.springframework.stereotype.Component;

@Component
public class RegularPaymentAmountHandler implements PaymentAmountHandler {
    @Override
    public Long getPaymentAmount(BigDecimal dailyFee, int numberOfDays) {
        return dailyFee.longValue() * numberOfDays;
    }

    @Override
    public boolean isApplicable(Payment.Type type) {
        return type.equals(Payment.Type.PAYMENT);
    }
}
