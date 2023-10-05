package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.PaymentAmountHandler;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class RegularPaymentAmountHandler implements PaymentAmountHandler {
    @Override
    public BigDecimal getPaymentAmount(BigDecimal dailyFee, int numberOfDays) {
        return dailyFee.multiply(BigDecimal.valueOf(numberOfDays));
    }

    @Override
    public boolean isApplicable(Payment.Type type) {
        return type.equals(Payment.Type.PAYMENT);
    }
}
