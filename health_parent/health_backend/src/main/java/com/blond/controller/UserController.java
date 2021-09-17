package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.User;
import com.blond.service.UserService;
import org.aspectj.bridge.Message;
import org.bouncycastle.jce.provider.JDKKeyFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户管理
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-10 1:39
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("getUsername")
    public Result getUsername(){
        // 当SpringSecurity完成用户认证后，会将用户信息存储到框架提供的上下文对象中（基于session实现）
        // .getContext().getAuthentication().getPrincipal()返回框架提供的User类型，而不是自定义的User
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user !=null){
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        }
        return new Result(false,MessageConstant.GET_USERNAME_FAIL);
    }

    @RequestMapping("/changePassword")
    public Result changePassword(@RequestBody Map map ) {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            String originPw = (String) map.get("originPw");
            String password = (String) map.get("password");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            // 根据用户名查找加密的原密码
            String oldPassword = userService.getPassword(user.getUsername());
            System.out.println(oldPassword);
            // 通过BCrypt提供的验证方法进行密码校验
            if(!bCryptPasswordEncoder.matches(originPw,oldPassword)){
                return new Result(false,MessageConstant.PASSWORD_NOT_SAME);
            }

            // 封装用户名密码到用户对象中
            User user1 = new User();
            user1.setUsername(user.getUsername());
            String encode = bCryptPasswordEncoder.encode(password);
            user1.setPassword(encode);
            userService.updatePassword(user1);
            return new Result(true,MessageConstant.EDIT_PASSWORD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_PASSWORD_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('USER_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody User user,Integer[] roleIds){
        try{
            userService.add(user,roleIds);
            return new Result(true,MessageConstant.ADD_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_USER_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('USER_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody User user,Integer[] roleIds){
        try{
            userService.edit(user,roleIds);
            return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_USER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            User user = userService.findById(id);
            return new Result(true,MessageConstant.GET_USER_SUCCESS,user);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USER_FAIL);
        }
    }

    @RequestMapping("/findRoleByUser")
    public Result findRoleByUser(Integer id){
        try{
            Integer[] roleIds = userService.findRoleByUserId(id);
            return new Result(true,MessageConstant.GET_USER_ROLE_SUCCESS,roleIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USER_ROLE_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        try{
            userService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_USER_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = userService.pageQuery(queryPageBean);
        return pageResult;
    }
}

