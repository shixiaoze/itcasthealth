package com.zxs.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.zxs.health.dao.SetmealDao;
import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.Setmeal;
import com.zxs.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 包名： com.zxs.health.service.impl
 *
 * @author: shixiaoze
 * 日期: 2020/11/23 16:52
 */

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用插件分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //如果有搜索条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            //字符串模糊拼接
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<Setmeal> setmealPage = setmealDao.findPage(queryPageBean.getQueryString());
        //将查到的total和每页集合封装
        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }


    /**
     * 新建
     *
     * @param setmeal
     * @param checkgroupIds 操作多张表一定要加事物控制
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先对套餐表进行添加
        setmealDao.add(setmeal);
        //获取自增长的id
        Integer setmealId = setmeal.getId();
        //遍历检查组的id
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                //添加套餐与检查组的关系
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 根据套餐id查询检查组id
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
     * 更新套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //先更新套餐表的信息
        setmealDao.update(setmeal);
        //根据套餐id删除旧关系
        setmealDao.deleteById(setmeal.getId());
        //添加新关系
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        //根据id去查询有没有被订单表关联
        int count = setmealDao.findOrderCountBySetmealId(id);

        if (count > 0) {
            //有关联，不能删除
            throw new MyException("已有客户对该套餐下单，不能删除！");
        }
        //再根据id去套餐表与检查组删除关系
        setmealDao.deleteById(id);
        //可以删除
        setmealDao.delete(id);
    }

    /**
     * 查询套餐表的所有图片
     * @return
     */
    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }
}
