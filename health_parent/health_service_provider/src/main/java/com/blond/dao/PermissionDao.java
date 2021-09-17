package com.blond.dao;

import com.blond.pojo.Permission;
import com.github.pagehelper.Page;

import java.util.Map;
import java.util.Set;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-09 20:10
 */
public interface PermissionDao {

    public void add(Permission permission);

    public void setRoleWithPermission(Map map);

    public void editBasic(Permission permission);

    public Set<Permission> findByRoleId(Integer roleId);

    public Page<Permission> selectByCondition(String queryString);

    public Permission findById(Integer id);

    public void cleanPermissionWithRole(Integer id);

    public void deleteById(Integer id);

    public Integer[] findRoleByPermissionId(Integer id);
}
