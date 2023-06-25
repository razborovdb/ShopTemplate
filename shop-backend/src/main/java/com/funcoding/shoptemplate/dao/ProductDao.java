package com.funcoding.shoptemplate.dao;


import com.funcoding.shoptemplate.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

    public List<Product> findAll(Pageable pageable);
}
