package com.blond.service;

import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Permission;
import com.blond.pojo.Role;

import java.util.List;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-09-16 3:28
 */
public interface PermissionService {

    public void add(Permission permission,Integer[] roleIds);

    public void edit(Permission permission,Integer[] roleIds);

    public Permission findById(Integer id);

    public void deleteById(Integer id);

    public Integer[] findRoleByPermissionId(Integer id);


    public PageResult pageQuery(QueryPageBean queryPageBean);


}
