package com.funcoding.shoptemplate.dao;

import com.funcoding.shoptemplate.entity.OrderDetail;
import com.funcoding.shoptemplate.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Long> {
    public List<OrderDetail> findByUser(User user);

    public List<OrderDetail> findByOrderStatus(String status);
}
