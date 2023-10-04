package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.service.PaymentAmountHandler;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentAmountHandlerStrategy {
    @Autowired
    private List<PaymentAmountHandler> paymentAmountHandlers;

    public PaymentAmountHandler getHandler(Payment.Type type) {
        return paymentAmountHandlers.stream()
                .filter(paymentAmountHandler -> paymentAmountHandler.isApplicable(type))
                .findAny()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find a PaymentAmountHandler "
                                + "for such type as: " + type)
                );
    }
}
