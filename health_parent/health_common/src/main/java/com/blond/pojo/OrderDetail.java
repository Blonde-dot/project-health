package com.blond.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-14 0:51
 */
public class OrderDetail implements Serializable {
    private Integer orderId;
    private String orderDate;
    private String memberName;
    private String phoneNumber;
    private String orderType;
    private String orderStatus;
    private String setmealName;

    public OrderDetail() {
    }

    public OrderDetail(Integer orderId, String orderDate, String memberName, String phoneNumber, String orderType, String orderStatus, String setmealName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.setmealName = setmealName;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", orderDate='" + orderDate + '\'' +
                ", memberName='" + memberName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", setmealName='" + setmealName + '\'' +
                '}';
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String  getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSetmealName() {
        return setmealName;
    }

    public void setSetmealName(String setmealName) {
        this.setmealName = setmealName;
    }
}
