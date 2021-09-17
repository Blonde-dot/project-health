package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.Permission;
import com.blond.pojo.User;
import com.blond.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-09-16 3:27
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @PreAuthorize("hasAuthority('PERMISSION_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission, Integer[] roleIds){
        try{
            permissionService.add(permission,roleIds);
            return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_PERMISSION_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('PERMISSION_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission,Integer[] roleIds){
        try{
            permissionService.edit(permission,roleIds);
            return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Permission permission = permissionService.findById(id);
            return new Result(true,MessageConstant.GET_PERMISSION_SUCCESS,permission);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_PERMISSION_FAIL);
        }
    }

    @RequestMapping("/findRoleByPermission")
    public Result findRoleByPermission(Integer id){
        try{
            Integer[] roleIds = permissionService.findRoleByPermissionId(id);
            return new Result(true,MessageConstant.GET_PERMISSION_ROLE_SUCCESS,roleIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_PERMISSION_ROLE_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        try{
            permissionService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.pageQuery(queryPageBean);
        return pageResult;
    }


}
