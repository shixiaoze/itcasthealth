package com.zxs.health.dao;

import com.zxs.health.pojo.User;

/**
 * 包名： com.zxs.health.dao
 *
 * @author: shixiaoze
 * 日期: 2020/11/29 19:30
 */
public interface UserDao {
    /**
     * 根据用户名五表关联查询
     * @param username
     * @return
     */
    User findByUsername(String username);
}
