package com.blond.dao;

import com.blond.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 23:41
 */
public interface OrderSettingDao {

    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date OrderDate);
    public List<OrderSetting> getOrderSettingByMonth(Map map);
    public OrderSetting findByOrderDate(Date date);
    public void editReservationsByOrderDate(OrderSetting orderSetting);

}
