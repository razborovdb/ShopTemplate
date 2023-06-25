package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.configuration.JwtRequestFilter;
import com.funcoding.shoptemplate.dao.CartDao;
import com.funcoding.shoptemplate.dao.ProductDao;

import com.funcoding.shoptemplate.dao.UserDao;
import com.funcoding.shoptemplate.entity.Cart;
import com.funcoding.shoptemplate.entity.Product;

import com.funcoding.shoptemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product) {
        Product newProduct = productDao.save(product);
        return newProduct;
    }

    public List<Product> getAllProduct(int pageNumber, int size, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,size);
        if (searchKey.isEmpty()) {
            return (List<Product>) productDao.findAll(pageable);
        } else {
            return (List<Product>) productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
        }
    }

    public void deleteProduct(Long productId) {
        productDao.deleteById(productId);
    }

    public Product getProductById(Long productId) {
        Optional<Product> product = productDao.findById(productId);
        return product.orElse(null);
    }

    public List<Product> getProductDetails(Long productId, boolean isSingleProductCheckout) {
        if(isSingleProductCheckout && productId != 0) {
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        } else {
            String username = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(username).get();
            List<Cart> carts = cartDao.findByUser(user);

            return carts.stream().map(item -> item.getProduct()).collect(Collectors.toList());
        }
    }

}
