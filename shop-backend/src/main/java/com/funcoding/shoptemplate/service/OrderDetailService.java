package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.configuration.JwtRequestFilter;
import com.funcoding.shoptemplate.dao.OrderDetailDao;
import com.funcoding.shoptemplate.dao.ProductDao;
import com.funcoding.shoptemplate.dao.UserDao;
import com.funcoding.shoptemplate.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void placeOrder(OrderInput orderInput) {
        List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantities();
        for(OrderProductQuantity o: orderProductQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();
            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();
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
    }
}
