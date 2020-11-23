package com.zxs.health.dao;

import com.github.pagehelper.Page;
import com.zxs.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 包名： com.zxs.health.dao
 *
 * @author: shixiaoze
 * 日期: 2020/11/22 20:17
 */
public interface CheckGroupDao {
    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findPage(String queryString);

    /**
     * 新增检查组
     * @param checkGroup
     */
    void addCheckGroup(CheckGroup checkGroup);

    /**
     * 新增检查组也检查项的关联表
     * @param id
     * @param checkitemId
     * 参数是两个一样的基本类型数据，要取别名区分
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer id, @Param("checkItemId")Integer checkitemId);

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 根据id查询检查项钩中的id集合
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 更新检查组的信息
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 根据id清除关联表的关系
     * @param id
     */
    void deleteBycCheckGroup(Integer id);

    /**
     * 查询是否有关联的外键
     * @param id
     * @return
     */
    Long findCountByCheckGroupId(int id);

    /**
     * 根据id删除
     * @param id
     */
    void delete(int id);
}
