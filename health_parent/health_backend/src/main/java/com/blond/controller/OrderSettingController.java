package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.entity.Result;
import com.blond.pojo.OrderSetting;
import com.blond.service.OrderSettingService;
import com.blond.utils.POIUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约设置管理
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 23:28
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;


    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file){
        try {
            List<String[]> excel = POIUtils.readExcel(file);
            // 将读取到String类型对象封装为OrderSetting
            List<OrderSetting> data = new ArrayList<>();
            for (String[] strings : excel) {
                String orderData = strings[0];
                String number = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderData), Integer.parseInt(number));
                data.add(orderSetting);
            }
            orderSettingService.add(data);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            // 文件解析失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> resultMap = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('ORDERSETTING')")
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
