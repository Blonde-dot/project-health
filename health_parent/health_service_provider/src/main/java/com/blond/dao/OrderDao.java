package com.blond.dao;

import com.blond.pojo.Order;
import com.blond.pojo.OrderDetail;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 1:59
 */
public interface OrderDao {
    public void add(Order order);
    public void edit(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public OrderDetail findById(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<Map> findHotSetmeal();
    public Page<OrderDetail> selectByCondition(String queryString);
    public void deleteById(Integer id);
    public Integer getCount();
    public Integer findArrivedCountBydate(String date);
    public List<Order> findOrderByMemberId(Integer memberId);

}
