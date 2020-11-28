package com.zxs.health.dao;

import com.zxs.health.pojo.Member;

/**
 * 包名： com.zxs.health.dao
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 19:34
 */
public interface MemberDao {
    /**
     * 根据手机号去会员表查数据
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 注册会员，添加数据
     * @param member
     */
    void add(Member member);
}
