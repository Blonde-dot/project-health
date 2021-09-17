package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.dao.RoleDao;
import com.blond.pojo.Role;
import com.blond.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-09-16 14:25
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
