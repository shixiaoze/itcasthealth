package com.zxs.health.dao;

import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.pojo.CheckItem;

import java.util.List;

/**
 * 包名： com.zxs.health.dao
 *
 * @author: shixiaoze
 * 日期: 2020/11/21 16:16
 */
public interface CheckItemDao {
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
     * 分页查询总条数
     * @param queryPageBean
     * @return
     */
    Long findTotal(QueryPageBean queryPageBean);

    /**
     * 分页查询每页结果
     * @param queryPageBean
     * @return
     */
    List<CheckItem> findList(QueryPageBean queryPageBean);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 根据id修改
     * @param checkItem
     */
    void updateById(CheckItem checkItem);

    /**
     * 根据id查询回显的数据
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 查询有没外键关联
     * @param id
     * @return
     */
    int findCountById(int id);
}
