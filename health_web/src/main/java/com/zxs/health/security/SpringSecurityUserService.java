package com.zxs.health.security;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zxs.health.pojo.Permission;
import com.zxs.health.pojo.Role;
import com.zxs.health.pojo.User;
import com.zxs.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 包名： com.zxs.health.security
 *
 * @author: shixiaoze
 * 日期: 2020/11/29 17:57
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    //订阅服务
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询用户信息
        User user=userService.findByUsername(username);
        if (user!=null) {
            List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
            //把用户下的角色及权限转成权限集合
            Set<Role> roles = user.getRoles();
            roles.forEach(role -> {
                authorityList.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                permissions.forEach(permission -> {
                    authorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
                });
            });

            //返回UserDetails(用户名，对应的密码，权限集合)
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorityList);
            return userDetails;
        }
        return null;
    }
}
