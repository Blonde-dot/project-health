package com.blond.dao;

import com.blond.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-09 20:05
 */
public interface RoleDao {

    public Set<Role> findByUserId(Integer userId);

    public List<Role> findAll();
}
