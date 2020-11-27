package com.zxs.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxs.health.Utils.POIUtils;
import com.zxs.health.common.MessageConstant;
import com.zxs.health.entity.Result;
import com.zxs.health.pojo.OrderSetting;
import com.zxs.health.service.OrderSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 包名： com.zxs.health.controller
 *
 * @author: shixiaoze
 * 日期: 2020/11/26 8:27
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    //订阅服务
    @Reference
    private OrderSettingService orderSettingService;

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    /**
     * 批量导入文件
     * @param excelFile
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) throws Exception {
        //①接收上传的文件，用MultipartFile excelFile 接收参数
        //②调用POIUtils解析excel,List<String[]>
        List<String[]> excel = POIUtils.readExcel(excelFile);
        //③再List<String[]>转成List<OrderSetting>
        SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
        List<OrderSetting> orderSettingList=excel.stream().map(orderSettingArr->{
            //创建一个空的对象
            OrderSetting orderSetting = new OrderSetting();
            //获取表中的日期字符串（表格中的第一项）
            try {
                orderSetting.setOrderDate(sdf.parse(orderSettingArr[0]));
            } catch (ParseException e) { }

            //获取表中的最大预约数（表格中的第二项）
            orderSetting.setNumber(Integer.parseInt(orderSettingArr[1]));
            return orderSetting;
        }).collect(Collectors.toList());
        //④调用服务导入
        orderSettingService.add(orderSettingList);
        //⑤返回操作的结果返回给页面
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 根据日期查询所有预约信息
     * @param month
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(String month){
        List<Map<String ,Integer>> resultList=orderSettingService.findAll(month);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,resultList);
    }

    /**
     * 根据日期修改最大预约数
     * @param orderSetting
     * @return
     */
    @RequestMapping("/updateByDate")
    public Result updateByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.updateByDate(orderSetting);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS);
    }
}
