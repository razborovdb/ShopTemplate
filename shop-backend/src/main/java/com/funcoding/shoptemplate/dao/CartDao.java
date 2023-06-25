package com.funcoding.shoptemplate.dao;


import com.funcoding.shoptemplate.entity.Cart;
import com.funcoding.shoptemplate.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends CrudRepository<Cart, Long > {
    public List<Cart> findByUser(User user);
}
