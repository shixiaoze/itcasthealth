package com.zxs.health.dao;

import com.github.pagehelper.Page;
import com.zxs.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 包名： com.zxs.health.dao
 *
 * @author: shixiaoze
 * 日期: 2020/11/23 16:53
 */
public interface SetmealDao {
    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 新建
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加添加套餐与检查组的关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId")Integer checkgroupId);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 根据套餐id查询检查组id
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 更新套餐表
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 根据套餐id删除旧关系
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id去查询有没有被订单表关联
     * @param id
     * @return
     */
    int findOrderCountBySetmealId(int id);

    /**
     * 根据id删除套餐
     * @param id
     */
    void delete(int id);

    /**
     * 查询套餐表的所有图片
     * @return
     */
    List<String> findImgs();
}
