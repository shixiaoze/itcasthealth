package com.zxs.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.zxs.health.common.MessageConstant;
import com.zxs.health.common.RedisMessageConstant;
import com.zxs.health.entity.Result;
import com.zxs.health.pojo.Order;
import com.zxs.health.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Map;

/**
 * 包名： com.zxs.health.controller
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 17:12
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    //订阅服务
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    private static final Logger log=LoggerFactory.getLogger(OrderController.class);

    /**
     * 校验验证码
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,String>map){
        //获取验证码
        String code = map.get("validateCode");
        log.info("用户输入的验证码: {}",code);
        //获取手机号
        String telephone = map.get("telephone");
        //拼接成jedis格式的key
        Jedis jedis = jedisPool.getResource();
        String key= RedisMessageConstant.SENDTYPE_ORDER+":"+telephone;
        //根据key获取redis存储的验证码
        String codeInRedis = jedis.get(key);
        log.debug("redis中的验证码：{}",codeInRedis);
        //判断是否有验证码
        if (StringUtil.isEmpty(codeInRedis)) {
            //如果没有，提示请获取验证码
            return new Result(false,"请重新获取验证码！");
        }
        //校验验证码
        if (!codeInRedis.equals(code)) {
            //验证码不通过，则返回错误信息
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        log.info("验证码校验通过...");
        //验证码通过，删除redis的key，关闭
        jedis.del(key);
        jedis.close();
        //设置预约类型：微信登录
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        //调用服务提交预约数据返回订单的id
        Integer id=orderService.submit(map);
        return new Result(true,MessageConstant.ORDER_SUCCESS,id);
    }

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(int id){
        Map<String,String> resultMap=orderService.findById(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,resultMap);
    }

}
