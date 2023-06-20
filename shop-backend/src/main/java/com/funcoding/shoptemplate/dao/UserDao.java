package com.funcoding.shoptemplate.dao;

import com.funcoding.shoptemplate.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    //List<User> findUsersByRoleId(String role);
}
