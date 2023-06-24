package com.funcoding.shoptemplate.controller;

import com.funcoding.shoptemplate.entity.OrderInput;
import com.funcoding.shoptemplate.service.OrderDetailService;
import com.funcoding.shoptemplate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
    @PreAuthorize("hasRole('User')")
    @PostMapping({"/order"})
    public void placeOrder(@RequestBody OrderInput orderInput) {
        orderDetailService.placeOrder(orderInput);
    }
}
