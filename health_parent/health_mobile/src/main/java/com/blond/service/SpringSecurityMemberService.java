package com.blond.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.pojo.Member;
import com.blond.pojo.Permission;
import com.blond.pojo.Role;
import com.blond.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
public class SpringSecurityMemberService implements UserDetailsService {

    /*空指针异常：没有配置dubbo扫描*/
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 登陆验证
     * 根据用户名获取用户信息
     * @param username
     * @return org.springframework.security.core.userdetails.UserDetails 返回用户信息，由SpringSecurity框架完成验证
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Jedis resource = jedisPool.getResource();
        Member member = memberService.findByTelephone(username);
        if(member == null){ // 用户名不存在
            return null;
        }
        // 为当前用户授权
        List<GrantedAuthority> list = new ArrayList<>();
        // 1.获取用户所担任的角色
        list.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        org.springframework.security.core.userdetails.User member4Detail
                = new org.springframework.security.core.userdetails.User(username,member.getPassword(),list);
        // 登录成功，日访问量+1
        try{
            resource.incr("DailyVisit");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            resource.close();
        }
        return member4Detail;
    }
}
