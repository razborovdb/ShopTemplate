package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.configuration.JwtRequestFilter;
import com.funcoding.shoptemplate.dao.CartDao;
import com.funcoding.shoptemplate.dao.OrderDetailDao;
import com.funcoding.shoptemplate.dao.ProductDao;
import com.funcoding.shoptemplate.dao.UserDao;
import com.funcoding.shoptemplate.entity.*;
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
                    user
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
}
