package com.blond.controller;

import com.blond.constant.MessageConstant;
import com.blond.constant.RedisMessageConstant;
import com.blond.entity.Result;
import com.blond.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 验证码管理
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 0:44
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        Jedis resource = jedisPool.getResource();
        // 生成随机验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        System.out.println("发送的手机验证码为：" + code);
        // 由于云短信服务无法支持，故发送到手机无法实现
        try{
            // 将验证码添加到redis，并设置过期时间，对应的key为用户的手机号码+发送验证码种类
            resource.setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }finally {
            resource.close();
        }
    }


    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        Jedis resource = jedisPool.getResource();
        // 生成随机验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        System.out.println("发送的手机验证码为：" + code);
        // 由于云短信服务无法支持，故发送到手机无法实现
        try{
            // 将验证码添加到redis，并设置过期时间，对应的key为用户的手机号码+发送验证码种类
            resource.setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,5*60,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }finally {
            resource.close();
        }
    }

}
