package com.zxs.health.dao;

import com.zxs.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * 包名： com.zxs.health.dao
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 19:20
 */
public interface OrderDao {
    /**
     * 订单表查询集合
     * @param order
     * @return
     */
    List<Order> findByOrder(Order order);

    /**
     * 添加订单信息
     * @param order
     */
    void add(Order order);

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    Map<String, String> findById(int id);
}
