package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.dao.OrderSettingDao;
import com.blond.pojo.Order;
import com.blond.pojo.OrderSetting;
import com.blond.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约设置服务
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 23:38
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约设置数据
     * @param list
     * @return void
     */
    @Override
    public void add(List<OrderSetting> list) {
        if(list != null && list.size()>0){
            for (OrderSetting orderSetting : list) {
                // 通过传入日期判断当日是否已经设置了预约人数
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count>0){ // 如果已设置，则调用更新方法
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{ // 如果未设置，则调用添加方法
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 通过年月份获取当月的预约设置数据
     * @param date yyyy--MM
     * @return java.util.List<java.util.Map>
     */
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        // 将年月date拼接成完整的日期
        String begin = date + "-1";// yyyy--MM--d
        String end = date + "-31"; // yyyy--MM--dd
        Map<String,String> dateMap = new HashMap<>();
        dateMap.put("begin",begin);
        dateMap.put("end",end);

        // 调用dao，根据日期范围查询月份预约人数
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(dateMap);
        // 封装成Map集合
        List<Map> result = new ArrayList<>();
        if (orderSettings != null && orderSettings.size() > 0) {
            for (OrderSetting orderSetting : orderSettings) {
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("date",orderSetting.getOrderDate().getDate());// 获取日期，封装成String类型
                resultMap.put("number",orderSetting.getNumber());// 获取预约人数
                resultMap.put("reservations",orderSetting.getReservations());// 获取已预约人数
                result.add(resultMap);
            }
        }
        return result;
    }

    /**
     * 根据日期设置对应的预约人数
     * @param orderSetting
     * @return void
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        // 根据传入的日期判断是否设置了预约人数
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count>0){ // 当前日期已经设置了预约人数，进行更新
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{ // 当前日期第一次设置预约人数，进行插入
            orderSettingDao.add(orderSetting);
        }
    }
}
