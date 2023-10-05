package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.model.Payment;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.service.PaymentAmountHandler;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class StripeService {
    private static final String DEFAULT_URL_PART = "/api/payments";
    private static final String SUCCESS_ENDPOINT = "success";
    private static final String CANCEL_ENDPOINT = "cancel";
    private static final Long STANDARD_QUANTITY_OF_RENTAL_CART = 1L;
    private static final String DEFAULT_CURRENCY = "USD";
    private static final BigDecimal CONVERTING_TO_DOLLARS_VALUE = BigDecimal.valueOf(100);

    private final PaymentAmountHandlerStrategy handler;
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

    public Session createSession(Rental rental, Payment.Type type) throws StripeException {
        String productName = rental.getCar().getModel();
        BigDecimal price = calculateTotalAmount(rental, type);
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(createUrl(SUCCESS_ENDPOINT))
                .setCancelUrl(createUrl(CANCEL_ENDPOINT))
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(DEFAULT_CURRENCY)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData
                                                                .ProductData.builder()
                                                                .setName(productName)
                                                                .build()
                                                )
                                                .setUnitAmountDecimal(
                                                        price.multiply(CONVERTING_TO_DOLLARS_VALUE)
                                                )
                                                .build()
                                )
                                .setQuantity(STANDARD_QUANTITY_OF_RENTAL_CART)
                                .build()
                )
                .build();
        return Session.create(params);
    }

    private String createUrl(String type) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(host)
                .port(port)
                .path(DEFAULT_URL_PART + "/" + type)
                .query("sessionId={CHECKOUT_SESSION_ID}")
                .build()
                .toUriString();
    }

    private BigDecimal calculateTotalAmount(Rental rental, Payment.Type type) {
        PaymentAmountHandler amountHandler = handler.getHandler(type);
        int rentalDays = getNumberOfRentalDays(rental);
        BigDecimal dailyFee = rental.getCar().getDailyFee();
        return amountHandler.getPaymentAmount(dailyFee, rentalDays);
    }

    private int getNumberOfRentalDays(Rental rental) {
        LocalDate actualReturnDate = rental.getActualReturnDate().toLocalDate();
        LocalDate rentalDate = rental.getRentalDate().toLocalDate();
        return Period.between(rentalDate, actualReturnDate).getDays();
    }
}
