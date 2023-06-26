package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.configuration.JwtRequestFilter;
import com.funcoding.shoptemplate.dao.CartDao;
import com.funcoding.shoptemplate.dao.OrderDetailDao;
import com.funcoding.shoptemplate.dao.ProductDao;
import com.funcoding.shoptemplate.dao.UserDao;
import com.funcoding.shoptemplate.entity.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    private static final String ORDER_PLACED="Placed";
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    private static final String CURRENCY = "INR";

    @Autowired
    private CartDao cartDao;
    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantities();
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();
        for(OrderProductQuantity o: orderProductQuantityList) {
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
            orderDetailDao.save(orderDetail);
        }

        // empty the cart.
        if(!isSingleProductCheckout) {
            List<Cart> carts = cartDao.findByUser(user);
            carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
        }
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
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

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

    public TransactionDetails createTransaction(Double amount) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100) );
            jsonObject.put("currency", CURRENCY);

//            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
//
//            Order order = razorpayClient.orders.create(jsonObject);
//
//            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
//            return transactionDetails;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    private TransactionDetails prepareTransactionDetails(Order order) {
//        String orderId = order.get("id");
//        String currency = order.get("currency");
//        Integer amount = order.get("amount");
//
//        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
//        return transactionDetails;
//    }
}
