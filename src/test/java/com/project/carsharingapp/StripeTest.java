package com.project.carsharingapp;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.Test;


public class StripeTest {

    @Test
    public void test() throws StripeException {
        Stripe.apiKey = "sk_test_51NwluPLODs6ppc0Lt4vFUOmyqkOeMpnbizJwyBNFWQ0Y2rL79OtqPtydVF11PD2WoXiIkxCc3IrThcvWZkOmmn1e00ST12M2PI";

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/success")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("USD")
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .putAllExtraParam(null)
                                                                .build()
                                                )
                                                .setUnitAmount(1L)
                                                .build()
                                )
                                .setQuantity(1L) // todo quantity
                                .build()
                )
                .build();

        Session session = Session.create(params);

//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(1099L)
//                        .setCurrency("usd")
//                        .addPaymentMethodType("card")
//                        .build();
//        PaymentIntent test = PaymentIntent.create(params);
//        System.out.println(test);
    }
}
