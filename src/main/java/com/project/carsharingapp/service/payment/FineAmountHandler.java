package com.project.carsharingapp.service.payment;

import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.PaymentAmountHandler;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FineAmountHandler implements PaymentAmountHandler {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.25);

    @Override
    public BigDecimal getPaymentAmount(BigDecimal dailyFee, int numberOfDays) {
        return dailyFee.multiply(FINE_MULTIPLIER).multiply(BigDecimal.valueOf(numberOfDays));
    }

    @Override
    public boolean isApplicable(Payment.Type type) {
        return type.equals(Payment.Type.FINE);
    }
}
