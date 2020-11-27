package com.zxs.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxs.health.dao.OrderSettingDao;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.OrderSetting;
import com.zxs.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 包名： com.zxs.health.service.impl
 *
 * @author: shixiaoze
 * 日期: 2020/11/26 8:30
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList!=null) {
            //①遍历List<OrderSetting>
            for (OrderSetting orderSetting : orderSettingList) {
                //②通过日历查询预约设置表
                OrderSetting orderDB=orderSettingDao.findByDate(orderSetting.getOrderDate());
                //③如果存在预约设置：
                if (orderDB!=null) {
                    //判断更新后的最大数是否会等于已预约人数；

                    if (orderSetting.getNumber()>orderDB.getReservations()) {
                        //大于，则可以更新最大预约数；
                        orderSettingDao.updateByDate(orderSetting);
                    }else{
                        //小于，报错已预约数超过最大预约数，接口上声明异常
                        throw new MyException("已预约人数超过了最大预约数，不能更新！");

                    }
                }else {
                    //④不存在，则添加预约设置
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 根据日期查询所有预约信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> findAll(String month) {
        month=month+"%";
        return orderSettingDao.findAll(month);
    }

    /**
     * 根据日期修改最大预约数
     * @param orderSetting
     * @throws MyException
     */
    @Override
    public void updateByDate(OrderSetting orderSetting)  {
        //通过日历查询预约设置表
        OrderSetting orderDB=orderSettingDao.findByDate(orderSetting.getOrderDate());
        //③如果存在预约设置：
        if (orderDB!=null) {
            //判断更新后的最大数是否会等于已预约人数；

            if (orderSetting.getNumber()>orderDB.getReservations()) {
                //大于，则可以更新最大预约数；
                orderSettingDao.updateByDate(orderSetting);
            }else{
                //小于，报错已预约数超过最大预约数，接口上声明异常
                throw new MyException("已预约人数超过了最大预约数，不能更新！");
            }
        }else {
            //④不存在，则添加预约设置
            orderSettingDao.add(orderSetting);
        }

    }
}
