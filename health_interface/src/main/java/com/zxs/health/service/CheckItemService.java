package com.zxs.health.service;

import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.pojo.CheckItem;

import java.util.List;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/21 16:41
 */
public interface CheckItemService {
    //查询所有
    List<CheckItem> findAll();

    //添加
    void add(CheckItem checkItem);

    PageResult findList(QueryPageBean queryPageBean);
}
