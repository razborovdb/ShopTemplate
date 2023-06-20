package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.dao.RoleDao;
import com.funcoding.shoptemplate.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
