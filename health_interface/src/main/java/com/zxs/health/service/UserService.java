package com.zxs.health.service;

import com.zxs.health.pojo.User;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/29 19:27
 */
public interface UserService {
    /**
     * 根据用户名五表关联查询
     * @param username
     * @return
     */
    User findByUsername(String username);
}
