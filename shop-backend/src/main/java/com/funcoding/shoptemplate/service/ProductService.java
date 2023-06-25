package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.dao.ProductDao;

import com.funcoding.shoptemplate.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        Product newProduct = productDao.save(product);
        return newProduct;
    }

    public List<Product> getAllProduct(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber,size);
        List<Product> products = (List<Product>) productDao.findAll(pageable);
        return products;
    }

    public void deleteProduct(Long productId) {
        productDao.deleteById(productId);
    }

    public Product getProductById(Long productId) {
        Optional<Product> product = productDao.findById(productId);
        return product.orElse(null);
    }

    public List<Product> getProductDetails(Long productId, boolean isSingleProductCheckout) {
        if(isSingleProductCheckout) {
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        } else {

        }

        return new ArrayList<>();
    }

}
