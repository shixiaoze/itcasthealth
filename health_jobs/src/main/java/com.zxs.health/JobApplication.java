package com.zxs.health;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 包名： com.zxs.job
 *
 * @author: shixiaoze
 * 日期: 2020/11/24 21:43
 */
public class JobApplication {
    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("classpath:applicationContext-job.xml");
        // 阻塞
        System.in.read();
    }
}
