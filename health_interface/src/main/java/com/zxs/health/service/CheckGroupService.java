package com.zxs.health.service;

import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.CheckGroup;

import java.util.List;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/22 20:16
 */
public interface CheckGroupService {
    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);


    /**
     * 根据id查询检查组
     *
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 根据id查询检查项钩中的id集合
     *
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 编辑检查组
     *
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 根据id删除检查组
     *
     * @param id
     * @throws MyException
     */
    void delete(int id) throws MyException;

    /**
     * 查询所有
     *
     * @return
     */
    List<CheckGroup> findAll();
}
