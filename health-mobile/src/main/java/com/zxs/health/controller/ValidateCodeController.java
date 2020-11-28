package com.zxs.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.StringUtil;
import com.zxs.health.Utils.SMSUtils;
import com.zxs.health.Utils.ValidateCodeUtils;
import com.zxs.health.common.MessageConstant;
import com.zxs.health.common.RedisMessageConstant;
import com.zxs.health.entity.Result;
import com.zxs.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 包名： com.zxs.health.controller
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 16:32
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约页面生成验证码
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        Jedis jedis = jedisPool.getResource();
        //拼接jedis的key,key要带上业务标示
        String key= RedisMessageConstant.SENDTYPE_ORDER+":"+telephone;
        //根据这个电话标示的key去jedis获取值
        String codeInRedis = jedis.get(key);
        log.debug("Redis中的验证码: {},{}",codeInRedis,telephone);
        //判断是否存在
        if (!StringUtil.isEmpty(codeInRedis)) {
            //表示已经生成了验证码，报提示
            return new Result(false, "验证码已发送，请注意查收！");
        }else{
            //表示没有验证码,则生成验证码
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            //发送短信，通过阿里云发送短信验证码
           /* try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            } catch (ClientException e) {
                e.printStackTrace();
                log.error("发送验证码失败",e);
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }*/
            log.debug("验证码发送成功: {},{}", code, telephone);
            //- 验证码存入redis，同时要设置有效期，5-15 10分钟
            jedis.setex(key, 10*60, code);
            jedis.close();
        }

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


    /**
     * 手机快速登录生成验证码
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        Jedis jedis = jedisPool.getResource();
        //拼接jedis的key,key要带上业务标示
        String key= RedisMessageConstant.SENDTYPE_ORDER+":"+telephone;
        //根据这个电话标示的key去jedis获取值
        String codeInRedis = jedis.get(key);
        log.debug("Redis中的验证码: {},{}",codeInRedis,telephone);
        //判断是否存在
        if (!StringUtil.isEmpty(codeInRedis)) {
            //表示已经生成了验证码，报提示
            return new Result(false, "验证码已发送，请注意查收！");
        }else{
            //表示没有验证码,则生成验证码
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            //发送短信，通过阿里云发送短信验证码
           /* try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            } catch (ClientException e) {
                e.printStackTrace();
                log.error("发送验证码失败",e);
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }*/
            log.debug("验证码发送成功: {},{}", code, telephone);
            //- 验证码存入redis，同时要设置有效期，5-15 10分钟
            jedis.setex(key, 10*60, code);
            jedis.close();
        }

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
