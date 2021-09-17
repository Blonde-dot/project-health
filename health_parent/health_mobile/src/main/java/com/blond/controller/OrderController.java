package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.constant.RedisMessageConstant;
import com.blond.entity.Result;
import com.blond.pojo.Member;
import com.blond.pojo.Order;
import com.blond.pojo.Setmeal;
import com.blond.service.MemberService;
import com.blond.service.OrderService;
import com.blond.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线体检预约管理
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 1:35
 */
@RestController
@RequestMapping("/order")
@SuppressWarnings("unchecked")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        Jedis resource = jedisPool.getResource();

        // 校验验证码
        String telephone = (String) map.get("telephone");
        String validateCodeTrue = resource.get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        if(validateCode != null && validateCodeTrue !=null && validateCodeTrue.equals(validateCode)){
            // 验证码输入正确，调用业务返回结果对象
            Result result = null;
            // 手动设置预约类型
            map.put("orderType",Order.ORDERTYPE_WEIXIN);
            try {
                result = orderService.setOrder(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }

            /*if(result.isFlag()){
                // 预约成功，发送成功短信（无云通信服务，暂时无法开发）
            }*/
            resource.close();
            return result;
        }else{ //验证码校验错误（不正确或已过期）
            resource.close();
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map resultMap = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @RequestMapping("/getOrderDetail")
    public Result getOrderDetail(){
        try{
            List<Map> result = new ArrayList<Map>();

            org.springframework.security.core.userdetails.User loginMember =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = loginMember.getUsername();
            Member member = memberService.findByTelephone(username);
            Integer memberId = member.getId();
            List<Order> orderList = orderService.findOrderByMemberId(memberId);
            for (Order order : orderList) {
                Map map = new HashMap();
                map.put("memberName",member.getName());
                map.put("orderDate",order.getOrderDate());
                map.put("orderType",order.getOrderType());
                map.put("orderStatus",order.getOrderStatus());
                String setmealName = setmealService.findNameById(order.getSetmealId());
                map.put("setmealName",setmealName);
                result.add(map);
            }
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
