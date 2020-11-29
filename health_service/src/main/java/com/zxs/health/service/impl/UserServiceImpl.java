package com.zxs.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxs.health.dao.UserDao;
import com.zxs.health.pojo.User;
import com.zxs.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 包名： com.zxs.health.service.impl
 *
 * @author: shixiaoze
 * 日期: 2020/11/29 19:27
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    /**
     * 根据用户名五表关联查询
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
