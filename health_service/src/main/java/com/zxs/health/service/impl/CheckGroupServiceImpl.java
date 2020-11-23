package com.zxs.health.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zxs.health.dao.CheckGroupDao;
import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.CheckGroup;
import com.zxs.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 包名： com.zxs.health.service.impl
 *
 * @author: shixiaoze
 * 日期: 2020/11/22 20:16
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 使用分页插件,参数为当前页，每页条数
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //非空判断，是否有搜索条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            //有搜索条件,模糊匹配
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckGroup> checkGroupPage=checkGroupDao.findPage(queryPageBean.getQueryString());

        return new PageResult(checkGroupPage.getTotal(),checkGroupPage.getResult());
    }

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     * 操作两张表要加事物
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.addCheckGroup(checkGroup);
        //获取添加后自增长的id
        Integer id = checkGroup.getId();

        if (checkitemIds !=null) {
            //检查项有勾选
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(id,checkitemId);
            }
        }
    }


    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 根据id查询检查项钩中的id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组的信息
        checkGroupDao.update(checkGroup);
        Integer id = checkGroup.getId();

        //根据id清除关联表的关系
        checkGroupDao.deleteBycCheckGroup(id);

        //重新根据id添加关联表的关系
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(id,checkitemId);
            }
        }
    }

    /**
     * 根据id删除检查组
     * @param id
     * @throws MyException
     */
    @Override
    public void delete(int id) {
        //查询是否有关联的外键
        Long count=checkGroupDao.findCountByCheckGroupId(id);

        //表示有关联
        if (count!=null) {
            if (count>1) {
                throw new MyException("该检查组被套餐使用，不能删除！");
            }
        }
        //先删除检查项与检查组的关系
        checkGroupDao.deleteBycCheckGroup(id);

        //最后删除检查组
        checkGroupDao.delete(id);

    }
}
