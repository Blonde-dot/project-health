package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.Member;
import com.blond.pojo.Order;
import com.blond.pojo.OrderDetail;
import com.blond.pojo.OrderSetting;
import com.blond.service.MemberService;
import com.blond.service.OrderService;
import com.blond.service.SetmealService;
import com.blond.utils.DateUtils;
import com.mysql.jdbc.CacheAdapter;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-14 0:26
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private MemberService memberService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) throws Exception {
        return orderService.pageQuery(queryPageBean);
    }

    @PreAuthorize("hasAuthority('ORDER_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody OrderDetail orderDetail){
        try{
            // 根据套餐名查询套餐id
            String setmealName = orderDetail.getSetmealName();
            Integer setmeal_id = setmealService.findByName(setmealName);
            // 根据用户名查询用户
            String phoneNumber = orderDetail.getPhoneNumber();
            Member member = memberService.findByTelephone(phoneNumber);
            Integer memberId = member.getId();

            // 封装订单信息
            Order order = new Order();
            order.setMemberId(memberId);
            order.setSetmealId(setmeal_id);
            order.setOrderDate(DateUtils.parseString2Date(orderDetail.getOrderDate()));
            order.setOrderStatus(orderDetail.getOrderStatus());
            order.setOrderType(orderDetail.getOrderType());
            orderService.add(order);
            return new Result(true, MessageConstant.ADD_ORDER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ORDER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            OrderDetail byId4Detail = orderService.findById4Detail(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,byId4Detail);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('ORDER_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody OrderDetail orderDetail){
        try{
            // 根据套餐名查询套餐id
            String setmealName = orderDetail.getSetmealName();
            Integer setmeal_id = setmealService.findByName(setmealName);
            // 根据用户名查询用户
            String phoneNumber = orderDetail.getPhoneNumber();
            Member member = memberService.findByTelephone(phoneNumber);
            Integer memberId = member.getId();

            // 封装订单信息
            Order order = new Order();
            order.setId(orderDetail.getOrderId());
            order.setMemberId(memberId);
            order.setSetmealId(setmeal_id);
            order.setOrderDate(DateUtils.parseString2Date(orderDetail.getOrderDate()));
            order.setOrderStatus(orderDetail.getOrderStatus());
            order.setOrderType(orderDetail.getOrderType());
            orderService.edit(order);
            return new Result(true,MessageConstant.EDIT_ORDER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ORDER_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            orderService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_ORDER_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(true,MessageConstant.DELETE_ORDER_FAIL);
        }
    }
}
