package com.zxs.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxs.health.Utils.QiNiuUtils;
import com.zxs.health.service.SetmealService;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.util.List;

/**
 * 包名： com.zxs.health.job
 *
 * @author: shixiaoze
 * 日期: 2020/11/26 21:43
 */
@Component
public class ClearImgJob {
    //服务订阅者
    @Reference
    private SetmealService setmealService;

    private static final Logger log= LoggerFactory.getLogger(ClearImgJob.class);

    /**
     * 清理垃圾图片
     */
    @Scheduled(initialDelay = 3000,fixedDelay = 30000)
    public void clearImg(){
        log.info("任务开始执行...");
        //获取七牛上的所有图片
        List<String> img7Niu = QiNiuUtils.listFile();
        log.debug("获取7牛上的{}图片",img7Niu==null?0:img7Niu.size());
        //获取套餐表的所有图片
        List<String> imgDB=setmealService.findImgs();
        log.debug("获取套餐表上的{}图片",imgDB==null?0:imgDB.size());
        //获取垃圾图片
        img7Niu.removeAll(imgDB);
        log.debug("要删除{}张垃圾图片",img7Niu.size());
        //将垃圾图片转成数组
        String[] img2Delete = img7Niu.toArray(new String[]{});
        //删除垃圾图片
        log.info("开始删除垃圾图片...");
        QiNiuUtils.removeFiles(img2Delete);
        log.info("删除{}张图片成功",img7Niu.size());

    }
}
