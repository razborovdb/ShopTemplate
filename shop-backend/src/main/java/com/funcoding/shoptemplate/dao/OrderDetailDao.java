package com.funcoding.shoptemplate.dao;

import com.funcoding.shoptemplate.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Long> {

}
