package com.funcoding.shoptemplate.controller;

import com.funcoding.shoptemplate.entity.StripeModel;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.SourceTransaction;
import com.stripe.model.checkout.Session;
import com.funcoding.shoptemplate.entity.OrderDetail;
import com.funcoding.shoptemplate.entity.OrderInput;
import com.funcoding.shoptemplate.entity.TransactionDetails;
import com.funcoding.shoptemplate.service.OrderDetailService;
import com.funcoding.shoptemplate.service.ProductService;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @Value("${ENDPOINT_SECRET}")
    String endpointSecret;
    @PreAuthorize("hasRole('User')")
    @PostMapping({"/order/{isSingleProductCheckout}"})
    public void placeOrder(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
                           @RequestBody OrderInput orderInput) {
        orderDetailService.placeOrder(orderInput, isSingleProductCheckout);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getOrderDetails"})
    public List<OrderDetail> getOrderDetails() {
        return orderDetailService.getOrderDetails();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getAllOrderDetails/{status}"})
    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status") String status) {
        return orderDetailService.getAllOrderDetails(status);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping({"/markOrderAsDelivered/{orderId}"})
    public void markOrderAsDelivered(@PathVariable(name = "orderId") Long orderId) {
        orderDetailService.markOrderAsDelivered(orderId);
    }

    @PreAuthorize("hasRole('User')")
    @PostMapping({"/createTransaction/{amount}/{isSingleProductCheckout}"})
    public StripeModel createTransaction(@PathVariable(name = "amount") Double amount,
                                         @PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
                                         @RequestBody OrderInput orderInput) {

        return orderDetailService.createTransaction(orderInput, isSingleProductCheckout, amount);
    }

    @PostMapping({"/webhook"})
    public String stripeWebhook(@RequestBody String request, @RequestHeader(name="stripe-signature") String header) {

        Event event = null;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret
            );

        } catch (JsonSyntaxException e) {
            // Invalid payload

            return "";
        } catch (SignatureVerificationException e) {
            // Invalid signature

            return "";
        } catch (Exception e) {

            return "";
        }
//
//        // Deserialize the nested object inside the event
//        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
//        StripeObject stripeObject = null;
//        if (dataObjectDeserializer.getObject().isPresent()) {
//            stripeObject = dataObjectDeserializer.getObject().get();
//        } else {
//            // Deserialization failed, probably due to an API version mismatch.
//            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
//            // instructions on how to handle this case, or return an error here.
//        }
//        // Handle the event
//        switch (event.getType()) {
//            case "payment_intent.succeeded": {
//                // Then define and call a function to handle the event payment_intent.succeeded
//                break;
//            }
//            // ... handle other event types
//            default:
//                System.out.println("Unhandled event type: " + event.getType());
//        }
//
//        response.status(200);
        return "";

    }

}
