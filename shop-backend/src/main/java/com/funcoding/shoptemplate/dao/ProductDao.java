package com.funcoding.shoptemplate.dao;


import com.funcoding.shoptemplate.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

}
