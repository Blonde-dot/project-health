package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.entity.Result;
import com.blond.pojo.Role;
import com.blond.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RelationSupport;
import java.util.List;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-09-16 13:12
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<Role> roleList = roleService.findAll();
            return new Result(true,MessageConstant.GET_ROLE_SUCCESS,roleList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ROLE_FAIL);
        }
    }
}
