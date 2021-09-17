package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.dao.MemberDao;
import com.blond.dao.OrderDao;
import com.blond.service.ReportService;
import com.blond.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运营数据统计事务
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-11 0:28
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;
    /**
     * 查询运营数据
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        // 封装当前日期 --> reportDate YYYY--MM-DD
        String today = DateUtils.parseDate2String(DateUtils.getToday());

        // 查询获取本日新增会员数-->todayNewMember
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        // 查询获取会员总数-->totalMember
        Integer totalMember = memberDao.findMemberTotalCount();

        // 获取本周一对应的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        // 查询获取本周新增会员数 --> thisWeekNewMember
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);

        // 获取本月第一天对应的日期
        String firstDayThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        // 查询获取本月新增会员数 —-> thisMonthNewMember
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDayThisMonth);

        //今日预约数 --> todayOrderNumber
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);

        //本周预约数 --> thisWeekOrderNumber
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);

        //本月预约数 --> thisMonthOrderNumber
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDayThisMonth);

        //今日到诊数 --> todayVisitsNumber
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);

        //本周到诊数 --> thisWeekVisitsNumber
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);

        //本月到诊数 --> thisMonthVisitsNumber
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDayThisMonth);

        // 热门套餐查询 --> hotSetmeal[{{name:String,setmeal_count:int,proportion:float}]
        List<Map> hotSetmeal = orderDao.findHotSetmeal();

        // 封装所有运营数据到结果集中
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("reportDate",today);
        resultMap.put("todayNewMember",todayNewMember);
        resultMap.put("totalMember",totalMember);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);
        resultMap.put("todayOrderNumber",todayOrderNumber);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        resultMap.put("hotSetmeal",hotSetmeal);
        return resultMap;
    }

}
