package com.zxs.health.dao;

import com.zxs.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 包名： com.zxs.health.dao
 *
 * @author: shixiaoze
 * 日期: 2020/11/26 8:30
 */
public interface OrderSettingDao {


    /**
     * 根据日期查询订单表
     * @param orderDate
     * @return
     */
    OrderSetting findByDate(Date orderDate);

    /**
     * 更新订单表
     * @param orderSetting
     */
    void updateByDate(OrderSetting orderSetting);

    /**
     * 添加订单表
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据日期查询所有预约信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> findAll(String month);

    /**
     * 更新预约表
     * @param orderSetting
     * @return
     */
    int editReservationsByOrderDate(OrderSetting orderSetting);
}
