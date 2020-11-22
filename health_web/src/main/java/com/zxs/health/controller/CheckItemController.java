package com.zxs.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxs.health.common.MessageConstant;
import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.entity.Result;
import com.zxs.health.pojo.CheckItem;
import com.zxs.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.FlatteningPathIterator;
import java.util.List;

/**
 * 包名： com.zxs.health.controller
 *
 * @author: shixiaoze
 * 日期: 2020/11/21 16:58
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){

            List<CheckItem> checkItemList=checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);

    }

    /**
     * 添加方法
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){

            checkItemService.add(checkItem);
            return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);

    }

    /**
     * 分页查询所有
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findList")
    public Result findList(@RequestBody QueryPageBean queryPageBean){

            PageResult pageResult=checkItemService.findList(queryPageBean);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteById(int id){
            checkItemService.deleteById(id);
            return new Result(true ,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    /**
     * 根据id查询要回显的数据
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem=checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }


    /**
     * 修改
     * @param checkItem
     * @return
     */
    @RequestMapping("/update")
    public Result updateById(@RequestBody CheckItem checkItem){
            checkItemService.updateById(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }
}
