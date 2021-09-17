package com.blond.dao;

import com.blond.pojo.Role;
import com.blond.pojo.User;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-09 20:03
 */
public interface UserDao {

    public User findByUserName(String username);

    public void updatePassword(User user);

    public String findPassword(String username);

    public Integer findAdminCount(Integer id);

    public Integer findDoctorCount(Integer id);

    public void add(User user);

    public void setRoleByUser(Map<String,Integer> map);

    public Page<User> selectByCondition(String queryString);

    public User findById(Integer id);

    public Integer[] findRoleByUserId(Integer id);

    public void editBasic(User user);

    public void cleanRoleByUser(Integer userId);

    public void deleteById(Integer userId);
}
