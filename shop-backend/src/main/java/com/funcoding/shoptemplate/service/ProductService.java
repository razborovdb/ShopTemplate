package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.dao.ProductDao;
import com.funcoding.shoptemplate.dao.RoleDao;
import com.funcoding.shoptemplate.dao.UserDao;
import com.funcoding.shoptemplate.entity.Product;
import com.funcoding.shoptemplate.entity.Role;
import com.funcoding.shoptemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        Product newProduct = productDao.save(product);
        return newProduct;
    }

}
