package com.funcoding.shoptemplate.dao;


import com.funcoding.shoptemplate.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
    //List<Role> findRolesByUserId(String userId);
}
