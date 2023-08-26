package com.hue.cn.cloudapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hue.cn.cloudapp.entity.LoginVo;
import com.hue.cn.cloudapp.entity.SysUser;
import com.hue.cn.cloudapp.service.SysUserService;
import com.hue.cn.cloudapp.utils.JwtHelper;
import com.hue.cn.cloudapp.utils.Result;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:xgd
 * @Date:2023/8/25 20:16
 * @Description:
 */
@RestController
@RequestMapping("/system/user")
public class LonginController {
    @Resource
    private SysUserService sysUserService;
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        //获取用户名
        String username=loginVo.getUsername();
        String password=loginVo.getPassword();

        LambdaQueryWrapper<SysUser> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser user = sysUserService.getOne(wrapper);
        if (user == null) {
            return Result.fail("用户信息为空");
        }
        if(!password.equals(user.getPassword())){
            return Result.fail("密码不正确");
        }
        String token = JwtHelper.createToken(user.getId(),user.getUsername());
        //7 返回
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

}