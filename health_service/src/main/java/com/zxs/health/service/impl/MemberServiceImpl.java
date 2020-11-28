package com.zxs.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxs.health.dao.MemberDao;
import com.zxs.health.pojo.Member;
import com.zxs.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 包名： com.zxs.health.service.impl
 *
 * @author: shixiaoze
 * 日期: 2020/11/28 22:02
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    /**
     * 根据手机号查询数据
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 新增会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
