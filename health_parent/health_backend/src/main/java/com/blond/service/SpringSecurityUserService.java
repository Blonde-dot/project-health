package com.blond.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.pojo.Permission;
import com.blond.pojo.Role;
import com.blond.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 认证和权限控制
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-09 19:48
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return org.springframework.security.core.userdetails.UserDetails 返回用户信息，由SpringSecurity框架完成验证
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if(user == null){ // 用户名不存在
            return null;
        }
        // 为当前用户授权
        List<GrantedAuthority> list = new ArrayList<>();
        // 1.获取用户所担任的角色
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            // 2.获取用户对应角色所拥有的权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }


        org.springframework.security.core.userdetails.User securityUser
                = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return securityUser;
    }
}
