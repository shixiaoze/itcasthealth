package com.zxs.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.zxs.health.common.MessageConstant;
import com.zxs.health.common.RedisMessageConstant;
import com.zxs.health.entity.Result;
import com.zxs.health.pojo.Member;
import com.zxs.health.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.dc.pr.PRError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 包名： com.zxs.health.controller
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 21:47
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;
    private static final Logger log= LoggerFactory.getLogger(LoginController.class);

    /**
     * 检查验证码登录
     * @param map
     * @param res
     * @return
     */
    @RequestMapping("/check")
    public Result check(@RequestBody Map<String,String>map ,HttpServletResponse res){
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

        //判断是否为会员
        Member member=memberService.findByTelephone(telephone);
        if (member==null) {
          //不是会员先注册-
          member= new Member();
          member.setRegTime(new Date());
          member.setPhoneNumber(telephone);
          member.setRemark("手机快速注册");
          memberService.add(member);
        }

        //跟踪记录的手机号码，代表着会员
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setMaxAge(30*24*60*60);//存储一个月
        cookie.setPath("/");//访问的路径 根路径下网站的所有路径 都会发送这个cookie
        res.addCookie(cookie);
        return new Result(true,MessageConstant.ORDER_SUCCESS);
    }
}
