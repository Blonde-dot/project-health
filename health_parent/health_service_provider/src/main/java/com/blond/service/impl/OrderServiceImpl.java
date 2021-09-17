package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.constant.MessageConstant;
import com.blond.dao.MemberDao;
import com.blond.dao.OrderDao;
import com.blond.dao.OrderSettingDao;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.*;
import com.blond.service.OrderService;
import com.blond.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在线体检预约服务
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 1:48
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    /**
     * 设置预约
     *
     * @param map
     * @return com.blond.entity.Result
     */
    @Override
    public Result setOrder(Map map) throws Exception {
        // 判断用户选择的预约日期是否开放预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetByOrderDate = orderSettingDao.findByOrderDate(date);
        if(orderSetByOrderDate == null){ // 用户申请的预约日期没有设置预约（没有开放预约）
          return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        // 判断用户选择的预约日期是否预约人数达到上限
        int number = orderSetByOrderDate.getNumber(); // 当日可预约人数
        int reservations = orderSetByOrderDate.getReservations(); // 已经预约的人数
        if(reservations >= number){ // 已经约满
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        // 检查同一用户是否在同一天预约了同一套餐
        String telephone = (String) map.get("telephone");
        // 根据手机号查询用户
        Member member = memberDao.findByTelephone(telephone);
        if(member != null){
            // 用户已注册为会员，判断是否重复预约
            Integer memberId = member.getId(); // 会员id
            Date OrderDate = DateUtils.parseString2Date(orderDate); // 预约日期
            Integer setmealId = Integer.parseInt((String) map.get("setmealId")); // 预约套餐id
            Order order = new Order(memberId,OrderDate,setmealId);
            List<Order> orders = orderDao.findByCondition(order);
            if(orders !=null && orders.size()>0){
                // 预约订单已存在，说明用户在重复预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
            // 检查当前用户是否注册会员，如果是会员，直接设置预约
        }else{ // 当前用户未注册为会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(DateUtils.parseDate2String(new Date()));
            memberDao.add(member);
        }

        // 预约完成，更新当日预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(DateUtils.parseString2Date(orderDate));
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        // 大坑：Integer(map.get("setmealId"))报错：
        // 由json转换过来的map，setmealId对应的是String类型，但编译过程中是Object类型
        // 因此直接Integer(map.get("setmealId"))系统不会提示编译错误
        // 但运行时String类型无法通过这种方式直接转换为Integer，导致运行失败
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        // 更新预约人数
        orderSetByOrderDate.setReservations(orderSetByOrderDate.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetByOrderDate);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,order.getId());
    }

    /**
     * 根据id查询预约相关信息项 ：姓名、预约日期、预约类型、套餐类型
     * @param id
     * @return java.util.Map
     */
    @Override
    public Map findById(Integer id) throws Exception {
        Map resultMap = orderDao.findById4Detail(id);
        if(resultMap!=null){
            // 将获取的日期类型变量转化为字符串型变量
            Date orderDate = (Date) resultMap.get("orderDate");
            DateUtils.parseDate2String(orderDate);
        }
        return resultMap;
    }

    /**
     * 分页查询预约
     * @param
     * @return com.blond.entity.PageResult
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) throws Exception {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<OrderDetail> orderDetails = orderDao.selectByCondition(queryString);
        long total = orderDetails.getTotal();
        List<OrderDetail> result = orderDetails.getResult();
       /* for (OrderDetail orderDetail : result) {
            Date orderDate = orderDetail.getOrderDate();
            String parseDate2String = DateUtils.parseDate2String(orderDetail.getOrderDate());
            orderDetail.setOrderDate(parseDate2String);
        }*/
        return new PageResult(total,result);
    }

    /**
     * 插入新订单
     * @param order
     * @return void
     */
    @Override
    public void add(Order order) {
        orderDao.add(order);
    }

    /**
     * 更新预约信息
     * @param order
     * @return void
     */
    @Override
    public void edit(Order order) throws Exception {
        orderDao.edit(order);
    }

    /**
     * 查询订单详情信息，用于回显
     * @param id
     * @return com.blond.pojo.OrderDetail
     */
    @Override
    public OrderDetail findById4Detail(Integer id) {
        return orderDao.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        orderDao.deleteById(id);
    }

    /**
     * 查询订单总数
     * @param
     * @return java.lang.Integer
     */
    @Override
    public Integer getOrderCount() {
        return orderDao.getCount();
    }

    /**
     * 获取当日新增订单
     * @param
     * @return java.lang.Integer
     */
    @Override
    public Integer getTodayOrder() throws Exception {
        String date = DateUtils.parseDate2String(new Date());
        return orderDao.findOrderCountByDate(date);
    }

    /**
     * 获取当日到诊人数
     * @param
     * @return java.lang.Integer
     */
    @Override
    public Integer getTodayArrive() throws Exception {
        String date = DateUtils.parseDate2String(new Date());
        return orderDao.findArrivedCountBydate(date);
    }

    @Override
    public List<Order> findOrderByMemberId(Integer id) {
        List<Order> orders = orderDao.findOrderByMemberId(id);
        for (Order order : orders) {
            System.out.println(order);
        }
        return orders;
    }
}