package com.blond.service;

import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.Order;
import com.blond.pojo.OrderDetail;

import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 1:47
 */
public interface OrderService {

    public Result setOrder(Map map) throws Exception;

    public Map findById(Integer id) throws Exception;

    public PageResult pageQuery(QueryPageBean queryPageBean) throws Exception;

    public void add(Order order);

    public void edit(Order order) throws Exception;

    public OrderDetail findById4Detail(Integer id);

    public void deleteById(Integer id);

    public Integer getOrderCount();

    public Integer getTodayOrder() throws Exception;

    public Integer getTodayArrive() throws Exception;

    public List<Order> findOrderByMemberId(Integer id);
}
