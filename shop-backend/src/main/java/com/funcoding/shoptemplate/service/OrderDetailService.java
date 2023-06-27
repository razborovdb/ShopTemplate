package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.configuration.JwtRequestFilter;
import com.funcoding.shoptemplate.dao.CartDao;
import com.funcoding.shoptemplate.dao.OrderDetailDao;
import com.funcoding.shoptemplate.dao.ProductDao;
import com.funcoding.shoptemplate.dao.UserDao;
import com.funcoding.shoptemplate.entity.*;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.LineItem;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.ReflectionAccessFilter;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailService {
    private static final String ORDER_PLACED = "Placed";
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    @Value("${STRIPE_SECRET_KEY}")
    String secretKey;



    private static final String CURRENCY = "USD";

    @Autowired
    private CartDao cartDao;

    public List<OrderDetail> placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantities();
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();
        List<OrderDetail> orderIds = new ArrayList<>();
        for (OrderProductQuantity o : orderProductQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();
            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    o.getQuantity() * product.getProductDiscountedPrice(),
                    product,
                    user,
                    orderInput.getTransactionId()
            );
           orderIds.add(orderDetailDao.save(orderDetail));
        }

        // empty the cart.
        if (!isSingleProductCheckout) {
            List<Cart> carts = cartDao.findByUser(user);
            carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
        }

        return orderIds;
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if (status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    order -> orderDetails.add(order)
            );
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    order -> orderDetails.add(order)
            );
        }


        return orderDetails;
    }

    public void markOrderAsDelivered(Long orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

        if (orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

    public StripeModel createTransaction(OrderInput orderInput, boolean isSingleProductCheckout, Double amount) {
        Stripe.apiKey = secretKey;

        List<OrderDetail> orderDetails = placeOrder(orderInput, isSingleProductCheckout);
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        List<OrderProductQuantity> orders = orderInput.getOrderProductQuantities();
        Map<String, String> map =  new HashMap<>();

        for (int i = 0; i < orderDetails.size(); i++) {
            Double cost = orderDetails.get(i).getOrderAmount() * 100;
            map.put("key"+Integer.toString(i), Long.toString(orderDetails.get(i).getOrderId()));
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    //.putExtraParam("orderId", orderDetails.get(i).getOrderId())
                    .setQuantity(orders.get(i).getQuantity())
                    // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("USD")
                                    .setUnitAmount(cost.longValue())
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName(orderDetails.get(i).getProduct().getProductName())

                                            .build()
                                    )
                                    .build()
                     )
                    .build();
            lineItems.add(lineItem);
        }
        try {

            String YOUR_DOMAIN = "http://localhost:4200";
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setCurrency("USD")
                            .putAllMetadata(map)
                            .setSuccessUrl(YOUR_DOMAIN + "/orderConfirm")
                            .setCancelUrl(YOUR_DOMAIN + "/cart")
                            .addAllLineItem(lineItems)
                            .build();
            Session session = Session.create(params);


            return new StripeModel(session.getUrl());

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return null;
    }


}
