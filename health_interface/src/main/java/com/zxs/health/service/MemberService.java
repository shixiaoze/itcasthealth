package com.zxs.health.service;

import com.zxs.health.pojo.Member;

/**
 * 包名： com.zxs.health.service
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 21:54
 */
public interface MemberService {
    /**
     * 根据手机号查询数据
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 新增会员
     * @param member
     */
    void add(Member member);
}
