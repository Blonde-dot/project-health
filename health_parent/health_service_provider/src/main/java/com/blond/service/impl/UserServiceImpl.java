package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.RpcAcsRequest;
import com.blond.dao.PermissionDao;
import com.blond.dao.RoleDao;
import com.blond.dao.UserDao;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Permission;
import com.blond.pojo.Role;
import com.blond.pojo.User;
import com.blond.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-09 20:01
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 根据用户名查询用户
     * 并绑定与用户关联的角色（单个或多个），与角色关联的权限（单个或多个）
     * @param userName
     * @return com.blond.pojo.User
     */
    @Override
    public User findByUserName(String userName) {
        // 查询用户基本信息（不包括用户关联角色）
        User user = userDao.findByUserName(userName);
        if(user == null){
            return null;
        }

        // 查询关联角色集合并设置到用户中
        Integer id = user.getId();
        Set<Role> roles = findByUserId(id);

        // 查询用户对应的角色所关联的所有权限
        for (Role role : roles) {
            Set<Permission> permissions = findByRoleId(role.getId());
            role.setPermissions(permissions);
        }

        user.setRoles(roles);
        return user;
    }

    /**
     * 更新用户密码
     * @param user
     * @return void
     */
    @Override
    public void updatePassword(User user) {
        userDao.updatePassword(user);
    }

    /**
     * 根据用户名查找加密密码
     * @param username
     * @return java.lang.String
     */
    @Override
    public String getPassword(String username) {

        return userDao.findPassword(username);
    }

    /**
     * 查询管理员总数
     * 管理员的身份标识符默认为1
     * @param
     * @return java.lang.Integer
     */
    @Override
    public Integer findAdminCount() {
        return userDao.findAdminCount(1);
    }

    /**
     * 查询健康管理员总数
     * 健康管理员的身份标识符默认为2
     * @param
     * @return java.lang.Integer
     */
    @Override
    public Integer findDoctorCount() {
        return userDao.findDoctorCount(2);
    }


    /**
     * 根据用户id查询返回用户关联的角色
     * @param userId
     * @return java.util.Set<com.blond.pojo.Role>
     */
    public Set<Role> findByUserId(Integer userId){
        return roleDao.findByUserId(userId);
    }

    /**
     * 根据角色id查询返回角色关联的权限
     * @param roleId
     * @return java.util.Set<com.blond.pojo.Permission>
     */
    public Set<Permission> findByRoleId(Integer roleId){
        return permissionDao.findByRoleId(roleId);
    }

    /**
     * 添加用户并设置用户角色
     * @param user
     * @param roleIds
     * @return void
     */
    @Override
    public void add(User user,Integer[] roleIds) {
        // 新用户默认密码为123456
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        user.setPassword(encode);
        // 添加用户
        userDao.add(user);
        Integer userId = user.getId();
        // 设置用户关联角色
        for (Integer roleId : roleIds) {
            Map<String ,Integer> map = new HashMap<>();
            map.put("userId",userId);
            map.put("roleId",roleId);
            userDao.setRoleByUser(map);
        }
    }

    /**
     * 查询单个用户信息，用于回显
     * @param id
     * @return com.blond.pojo.User
     */
    @Override
    public User findById(Integer id) {
        // 查询基本信息
        User user = userDao.findById(id);
        return user;
    }

    /**
     * 查询用户关联角色，用于回显
     * @param id
     * @return java.lang.Integer[]
     */
    @Override
    public Integer[] findRoleByUserId(Integer id) {
        Integer[] roleByUserId = userDao.findRoleByUserId(id);
        return roleByUserId;
    }

    /**
     * 编辑用户基本信息和关联角色
     * @param user
     * @param roleIds
     * @return void
     */
    @Override
    public void edit(User user, Integer[] roleIds) {
        userDao.editBasic(user);

        // 设置用户关联角色
        Integer userId = user.getId();
        userDao.cleanRoleByUser(userId);

        for (Integer roleId : roleIds) {
            Map map = new HashMap();
            map.put("userId",userId);
            map.put("roleId",roleId);
            userDao.setRoleByUser(map);
        }

    }

    /**
     * 根据id删除用户信息
     * @param id
     * @return void
     */
    @Override
    public void deleteById(Integer id) {
        // 清除用户关联角色
        userDao.cleanRoleByUser(id);
        userDao.deleteById(id);
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

        Page<User> page = userDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }
}
