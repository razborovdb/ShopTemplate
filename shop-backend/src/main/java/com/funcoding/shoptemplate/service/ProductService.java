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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        Product newProduct = productDao.save(product);
        return newProduct;
    }

    public List<Product> getAllProduct() {
        List<Product> products = (List<Product>) productDao.findAll();
        return products;
    }

    public void deleteProduct(Long productId) {
        productDao.deleteById(productId);
    }

    public Product getProductById(Long productId) {
        Optional<Product> product = productDao.findById(productId);
        return product.orElse(null);
    }

}
