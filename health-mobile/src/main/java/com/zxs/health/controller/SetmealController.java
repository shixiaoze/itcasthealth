package com.zxs.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxs.health.Utils.QiNiuUtils;
import com.zxs.health.common.MessageConstant;
import com.zxs.health.entity.Result;
import com.zxs.health.pojo.Setmeal;
import com.zxs.health.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 包名： com.zxs.health.controller
 *
 * @author: shixiaoze
 * 日期: 2020/11/27 12:39
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    //订阅服务
    @Reference
    private SetmealService setmealService;

    /**
     * 获取套餐信息列表
     * @return
     */
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        List<Setmeal> setmealList=setmealService.getSetmeal();
        //拼接图片完整路径
        if (setmealList!=null) {
            for (Setmeal setmeal : setmealList) {
                setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
            }
        }
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmealList);
    }

    /**
     * 根据id查询套餐下的所有检查组检查项
     * @return
     */
    @RequestMapping("/findDetailById")
    public Result findDetailById(int id){
        Setmeal setmeal=setmealService.findDetailById(id);
        //拼接图片完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmeal);
    }

}
