package com.hue.cn.cloudapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hue.cn.cloudapp.config.CustomUser;
import com.hue.cn.cloudapp.entity.SysUser;
import com.hue.cn.cloudapp.service.SysUserService;
import com.hue.cn.cloudapp.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:xgd
 * @Date:2023/8/24 18:40
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser user = sysUserService.getOne(wrapper);
        user.getUsername();
        user.getPassword();
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //设置用户权限，在之后可进行权限校验
        List<SimpleGrantedAuthority> authorityList=new ArrayList<SimpleGrantedAuthority>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return new CustomUser(user,authorityList);
    }
}
