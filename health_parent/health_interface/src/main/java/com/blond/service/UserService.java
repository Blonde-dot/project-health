package com.blond.service;

import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Role;
import com.blond.pojo.User;

/**
 * 用户服务
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-09 19:51
 */
public interface UserService {

    public User findByUserName(String userName);

    public User findById(Integer id);

    public Integer[] findRoleByUserId(Integer id);

    public void updatePassword(User user);

    public void edit(User user,Integer[] roleIds);

    public void deleteById(Integer id);

    public String getPassword(String username);

    public Integer findAdminCount();

    public Integer findDoctorCount();

    public void add(User user,Integer[] roleIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);


}
