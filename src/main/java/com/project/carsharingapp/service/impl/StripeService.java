package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.model.Rental;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
public class StripeService {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.25);
    @Value("${stripe.secret}")
    private String secretKey;
    @Value("${app.host}")
    private String host;
    @Value("${app.port}")
    private Integer port;


    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Session createSession(Rental rental, Payment.Type type) {
        return null;
    }

    private String createUrl(String type) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(host)
                .port(port)
                .path("/" + type)
                .build()
                .encode()
                .toUriString();
    }

    private BigDecimal calculateTotalAmount(Rental rental, Payment.Type type) {
        return null;
    }
}
