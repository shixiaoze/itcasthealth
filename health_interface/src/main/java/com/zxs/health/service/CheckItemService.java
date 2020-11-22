package com.zxs.health.service;

import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.CheckItem;

import java.util.List;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/21 16:41
 */
public interface CheckItemService {
    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     *  分页查询
     * @param queryPageBean
     * @return
     */
    PageResult findList(QueryPageBean queryPageBean);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById (int id) throws MyException;

    /**
     * 根据id修改
     * @param checkItem
     */
    void updateById(CheckItem checkItem);

    /**
     * 根据id查询回显数据
     * @param id
     * @return
     */
    CheckItem findById(int id);
}
