package com.zxs.health.service;

import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/26 8:29
 */
public interface OrderSettingService {
    /**
     * 批量导入文件
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList)throws MyException;

    /**
     * 根据日期查询所有预约信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> findAll(String month);

    /**
     * 根据日期修改最大预约数
     * @param orderSetting
     */
    void updateByDate(OrderSetting orderSetting) throws MyException;
}
