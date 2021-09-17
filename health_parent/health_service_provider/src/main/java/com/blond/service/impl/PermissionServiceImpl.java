package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.dao.PermissionDao;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Permission;
import com.blond.service.PermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.bouncycastle.openssl.PEMReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.security.Permissions;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-09-16 17:34
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 新增权限项
     * @param permission
     * @param roleIds
     * @return void
     */
    @Override
    public void add(Permission permission, Integer[] roleIds) {
        permissionDao.add(permission);

        Integer permissionId = permission.getId();
        for (Integer roleId : roleIds) {
            Map map = new HashMap();
            map.put("permissionId",permissionId);
            map.put("roleId",roleId);
            permissionDao.setRoleWithPermission(map);
        }

    }

    /**
     * 编辑权限项
     * @param permission
     * @param roleIds
     * @return void
     */
    @Override
    public void edit(Permission permission, Integer[] roleIds) {
        // 编辑基本信息
        permissionDao.editBasic(permission);

        Integer permissionId = permission.getId();
        // 清除旧的权限与角色的关联
        permissionDao.cleanPermissionWithRole(permissionId);
        // 重新设置权限与角色的关联
        for (Integer roleId : roleIds) {
            Map map = new HashMap();
            map.put("permissionId",permissionId);
            map.put("roleId",roleId);
            permissionDao.setRoleWithPermission(map);
        }
    }

    /**
     * 查询单个权限项信息，用于回显
     * @param id
     * @return com.blond.pojo.Permission
     */
    @Override
    public Permission findById(Integer id) {

        return permissionDao.findById(id);
    }

    /**
     * 清除权限与角色的关系，并删除权限项
     * @param id
     * @return void
     */
    @Override
    public void deleteById(Integer id) {

        // 清除权限与角色的绑定
        permissionDao.cleanPermissionWithRole(id);
        // 删除权限
        permissionDao.deleteById(id);
    }

    /**
     * 根据权限查找其关联的角色
     * @param id
     * @return java.lang.Integer[]
     */
    @Override
    public Integer[] findRoleByPermissionId(Integer id) {

        return permissionDao.findRoleByPermissionId(id);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return com.blond.entity.PageResult
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);

        Page<Permission> permissionsPage = permissionDao.selectByCondition(queryString);

        return new PageResult(permissionsPage.getTotal(),permissionsPage.getResult());
    }
}
