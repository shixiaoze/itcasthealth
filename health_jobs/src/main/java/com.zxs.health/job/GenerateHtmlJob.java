package com.zxs.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxs.health.Utils.QiNiuUtils;
import com.zxs.health.pojo.Setmeal;
import com.zxs.health.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 包名： com.zxs.health.job
 *
 * @author: shixiaoze
 * 日期: 2020/11/27 15:56
 */
@Component
public class GenerateHtmlJob {
    //日志
    private static final Logger log = LoggerFactory.getLogger(GenerateHtmlJob.class);
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Reference
    private SetmealService setmealService;

    /**
     * PostConstruct 初始化的方法
     */
    @PostConstruct
    public void init(){
        //设置模板路径
        freemarkerConfiguration.setClassForTemplateLoading(GenerateHtmlJob.class,"/ftl");
        //设置编码
        freemarkerConfiguration.setDefaultEncoding("utf-8");
    }
    //存放静态文件
    @Value("${out_put_path}")
    private String out_put_path;
    @Scheduled(initialDelay = 3000,fixedDelay = 180000)
    public void generateHtml(){
        log.info("generateHtml任务启动了.......");
        Jedis jedis = jedisPool.getResource();
        String key="setmeal:static:html";
        //返回所有该key的成员
        Set<String> ids = jedis.smembers(key);
        log.debug("要处理的套餐id个数：{}",ids.size());
        ids.forEach(id->{
            //存进redis的id格式：id|操作类型|时间戳
            String [] idInfo=id.split("\\|");
            Integer setmealId = Integer.valueOf(idInfo[0]);//id
            //操作类型
            String operationType=idInfo[1];
            //存放文件的完整路径：格式setmeal_{id}.html
            String filename=out_put_path+ File.separator+"setmeal_"+setmealId+".html";
            //判断操作类型：1代表对表的 增加和修改，0代表对表的 删除
            if ("1".equals(operationType)) {
                generateDetailHtml(setmealId,filename);
            }else if("0".equals(operationType)){
                new File(filename).delete();
                log.debug("删除静态页面{}",setmealId);
            }
            //删除jedis中对应的id
            jedis.srem(key,id);
        });

        if (ids.size()>0) {
            //生成列表页面
            generateSetmealList();
        }
    jedis.close();
    }

    /**
     * 生成列表页面
     */
    private void generateSetmealList(){
        log.debug("生成列表页面成功");
        String templateName="mobile_setmeal.ftl";
        //构建详情数据Map<String,Object>
        List<Setmeal> list = setmealService.getSetmeal();
        //拼接图片路径
        list.forEach(setmeal -> {setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());});
        //将数据转成页面需要的类型
        Map<String, Object> dataMap = new HashMap<String,Object>();
        dataMap.put("setmealList",list);
        //存放的目录文件全路径
        String filename=out_put_path+File.separator+"mobile_setmeal.html";
        //生成页面方法
        generateStaticHtml(filename,templateName,dataMap);
        log.info("生成套餐列表静态页面成功");
    }

    /**
     * 生成详情页面
     * @param setmealId
     * @param filename
     */
    private void generateDetailHtml(Integer setmealId,String filename){
        log.debug("生成详情静态页面{}",setmealId);
        String templateName="mobile_setmeal_detail.ftl";
        //构建详情数据Map<String,Object>
        Setmeal setmeal = setmealService.findDetailById(setmealId);
        //拼接图片路径
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        //将数据转成页面需要的类型
        Map<String, Object> dataMap = new HashMap<String,Object>();
        dataMap.put("setmeal",setmeal);
        //生成页面方法
        generateStaticHtml(filename,templateName,dataMap);
        log.info("生成套餐详情静态页面成功");
    }


    /**
     * 生成静态页面
     * @param filename
     * @param templateName
     * @param dataMap
     */
    private void generateStaticHtml(String filename,String templateName,Map<String,Object>dataMap){
        try {
            //获取模板对象
            Template template = freemarkerConfiguration.getTemplate(templateName);
            //utf-8编码一定要有
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            //给模板填充数据
            template.process(dataMap,writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
           // e.printStackTrace();
            log.error("生成静态页面失败{}",filename,e);
        }
    }
}
