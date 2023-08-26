package com.hue.cn.cloudapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hue.cn.cloudapp.entity.SysUser;
import com.hue.cn.cloudapp.mapper.SysUserMapper;
import com.hue.cn.cloudapp.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @Author:xgd
 * @Date:2023/8/26 8:37
 * @Description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
