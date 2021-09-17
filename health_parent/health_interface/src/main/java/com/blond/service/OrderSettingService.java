package com.blond.service;

import com.blond.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 23:31
 */
public interface OrderSettingService {

    public void add(List<OrderSetting> list);

    public List<Map> getOrderSettingByMonth(String date);

    public void editNumberByDate(OrderSetting orderSetting);
}
