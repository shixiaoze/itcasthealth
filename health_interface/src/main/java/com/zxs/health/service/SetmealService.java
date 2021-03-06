package com.zxs.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.Setmeal;

import java.util.List;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/23 16:52
 */
public interface SetmealService {
    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 新建
     *
     * @param setmeal
     * @param checkgroupIds
     */
    Integer add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 根据套餐id查询检查组id
     *
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);


    /**
     * 更新套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 删除
     *
     * @param id
     */
    void delete(int id) throws MyException;

    /**
     * 查询套餐表的所有图片
     * @return
     */
    List<String> findImgs();

    /**
     * 获取套餐信息列表
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 根据id查询套餐下的所有检查组检查项
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
