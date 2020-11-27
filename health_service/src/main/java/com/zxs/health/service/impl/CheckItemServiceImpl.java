package com.zxs.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxs.health.dao.CheckItemDao;
import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.exception.MyException;
import com.zxs.health.pojo.CheckItem;
import com.zxs.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 包名： com.zxs.health.service.impl
 *
 * @author: shixiaoze
 * 日期: 2020/11/21 16:39
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList = checkItemDao.findAll();
        return checkItemList;
    }

    /**
     * 添加
     *
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findList(QueryPageBean queryPageBean) {
        Long total = checkItemDao.findTotal(queryPageBean);
        List<CheckItem> rows = checkItemDao.findList(queryPageBean);

        return new PageResult(total, rows);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void deleteById(int id) {
        //查询有关联个数
        int count = checkItemDao.findCountById(id);
        //判断是否有关联
        if (count > 0) {
            //有关联抛异常
            throw new MyException("有关联的检查组，不能删除！");
        }
        checkItemDao.deleteById(id);
    }

    /**
     * 根据id修改
     *
     * @param checkItem
     */
    @Override
    public void updateById(CheckItem checkItem) {
        checkItemDao.updateById(checkItem);
    }

    /**
     * 根据id查询要回显的数据
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

}
