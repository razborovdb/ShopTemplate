package com.funcoding.shoptemplate.controller;

import com.funcoding.shoptemplate.entity.Product;
import com.funcoding.shoptemplate.entity.User;
import com.funcoding.shoptemplate.service.ProductService;
import com.funcoding.shoptemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping({"/product/add"})

    public Product addNewProduct(@RequestBody Product product) {

        return productService.addNewProduct(product);
    }

}
