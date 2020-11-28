package com.zxs.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxs.health.Utils.DateUtils;
import com.zxs.health.dao.MemberDao;
import com.zxs.health.dao.OrderDao;
import com.zxs.health.dao.OrderSettingDao;
import com.zxs.health.entity.Result;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.Member;
import com.zxs.health.pojo.Order;
import com.zxs.health.pojo.OrderSetting;
import com.zxs.health.service.OrderService;
import com.zxs.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 包名： com.zxs.health.service.impl
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 17:49
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    /**
     * 提交预约数据
     * @param map
     * @return
     */
    @Override
    @Transactional
    public Integer submit(Map<String, String> map) {
        //根据日期去预约表查询数据
        String date = map.get("orderDate");
        Date orderDate=null;
        //字符串转成日期
        try {
            orderDate= DateUtils.parseString2Date(date);
        } catch (Exception e) {
            throw new MyException("日期格式不正确");
        }
        OrderSetting orderSetting = orderSettingDao.findByDate(orderDate);
        //判断是否为空
        if (orderSetting==null) {
            //为空，抛异常，接口声明
            throw new MyException("今日休息，请您预约其它日期！");
        }
        //不为空，判断可预约人数是否大于预约人数
        if(!(orderSetting.getNumber()>orderSetting.getReservations())){
            //如果不大于，则抛出异常
            throw new MyException("今日预约人数已满，请您预约其它日期！");
        }
        //构建重复预约的查询条件
        String telephone = map.get("telephone");
        Order order = new Order();
        order.setSetmealId(Integer.valueOf(map.get("setmealId")));
        order.setOrderDate(orderDate);

        //大于，根据手机号去会员表查数据
        Member member=memberDao.findByTelephone(telephone);
        //判断是不是会员
        if (member==null) {
            //如果不是先进行注册
            member=new Member();
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdCard(map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setRemark(map.get("orderType"));

            memberDao.add(member);
        }
        //获取会员id
        Integer memberId = member.getId();
        order.setMemberId(memberId);
        //判断是否重复预约，去订单表查询集合
        List<Order> orderList=orderDao.findByOrder(order);
        if (orderList!=null&&orderList.size()>0) {
            //表示已经预约
            throw new MyException("您已经预约过了，请勿重复预约！");
        }
        //否则，更新预约表
        int count= orderSettingDao.editReservationsByOrderDate(orderSetting);
        //判断是否更新成功
        if (count ==0) {
            throw new MyException("今日预约人数已满，请您预约其它日期！");
        }
        // - 如果更新成功，mybatis则会返回1
        //- 添加订单信息，返回主键
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(map.get("orderType"));
        orderDao.add(order);
        return order.getId();
    }

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Map<String, String> findById(int id) {
        return orderDao.findById(id);
    }

}
