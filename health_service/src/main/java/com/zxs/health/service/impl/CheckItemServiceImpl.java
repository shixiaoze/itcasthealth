package com.zxs.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxs.health.dao.CheckItemDao;
import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.pojo.CheckItem;
import com.zxs.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

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
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList=checkItemDao.findAll();
        return checkItemList;
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findList(QueryPageBean queryPageBean) {
        Long total=checkItemDao.findTotal(queryPageBean);
        List<CheckItem> rows=checkItemDao.findList(queryPageBean);

        return new PageResult(total,rows);
    }
}
