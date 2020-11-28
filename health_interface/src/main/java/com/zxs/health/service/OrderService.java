package com.zxs.health.service;

import com.zxs.health.exception.MyException;

import java.util.Map;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 17:13
 */
public interface OrderService {
    /**
     * 提交预约数据
     * @param map
     * @return
     */
    Integer submit(Map<String, String> map) throws MyException;

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    Map<String, String> findById(int id);
}
